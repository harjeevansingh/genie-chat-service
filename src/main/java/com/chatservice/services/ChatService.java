package com.chatservice.services;

import com.chatservice.constants.enums.exceptionCodes.RootExceptionCodes;
import com.chatservice.dto.MessageDTO;
import com.chatservice.entities.Conversation;
import com.chatservice.dao.master.ConversationDAO;
import com.chatservice.exceptions.RootException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Author: harjeevansingh
 */

@Service
@Slf4j
public class ChatService {

    @Autowired
    private ConversationDAO conversationDAO;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String RECENT_MESSAGES_KEY_PREFIX = "recent_messages:";
    private static final long RECENT_MESSAGES_TTL = 24 * 60 * 60; // 24 hours
    private static final String CHAT_TOPIC = "chat_messages";

    public Conversation createConversation(Long userId, String title) {
        Conversation conversation = new Conversation();
        conversation.setUserId(userId);
        conversation.setTitle(title);
        return conversationDAO.save(conversation);
    }

    public List<Conversation> getUserConversations(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        return conversationDAO.findByUserId(userId, pageable).getContent();
    }

    public void storeMessage(MessageDTO message) {
        try {
            // Store in Redis
            String key = RECENT_MESSAGES_KEY_PREFIX + message.getConversationId();
            try {
                String messageJson = objectMapper.writeValueAsString(message);
                redisTemplate.opsForList().leftPush(key, messageJson);
                redisTemplate.opsForList().trim(key, 0, 99); // Keep only last 100 messages
                redisTemplate.expire(key, RECENT_MESSAGES_TTL, TimeUnit.SECONDS);
            } catch (Exception e) {
                log.error("Error storing message in Redis. Message - {}", message, e);
                throw new RootException(RootExceptionCodes.INTERNAL_SERVER_ERROR);
            }

            // Send to Kafka
            try {
                String messageJson = objectMapper.writeValueAsString(message);
                kafkaTemplate.send(CHAT_TOPIC, messageJson);
            } catch (Exception e) {
                // Log the error and possibly throw a custom exception
                log.error("Error sending message to Kafka. Message - {}", message, e);
                throw new RootException(RootExceptionCodes.INTERNAL_SERVER_ERROR);
            }

            // Update conversation's updated_at timestamp
            Conversation conversation = conversationDAO.findById(message.getConversationId())
                    .orElseThrow(() -> new RootException(RootExceptionCodes.CONVERSATION_NOT_FOUND));
            conversation.setUpdatedAt(message.getTimestamp());
            conversationDAO.save(conversation);
        } catch (RootException e) {
            log.error("Error storing message. Message - {}", message, e);
            throw e;
        } catch (Exception e) {
            log.error("Error storing message. Message - {}", message, e);
            throw new RootException(RootExceptionCodes.INTERNAL_SERVER_ERROR);
        }
    }

    public List<MessageDTO> getRecentMessages(Long conversationId) {
        String key = RECENT_MESSAGES_KEY_PREFIX + conversationId;
        List<String> messagesJson = redisTemplate.opsForList().range(key, 0, -1);
        return messagesJson.stream()
                .map(json -> {
                    try {
                        return objectMapper.readValue(json, MessageDTO.class);
                    } catch (Exception e) {
                        // Log the error and return null or throw a custom exception
                        log.error("Error deserializing message from Redis. Message JSON - {} " +
                                "for conversationId: {}", conversationId, json, e);
                        throw new RootException(RootExceptionCodes.INTERNAL_SERVER_ERROR);
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }
}