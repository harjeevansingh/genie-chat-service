package com.chatservice.consumer;

import com.chatservice.dto.MessageDTO;
import com.chatservice.services.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static com.chatservice.constants.CommonConstants.HISTORY_MESSAGES_TOPIC;

/**
 * Author: harjeevansingh
 */

@Component
public class HandleAiResponseConsumer {

    private static final Logger log = LoggerFactory.getLogger(HandleAiResponseConsumer.class);
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "ai-responses", groupId = "chat-service-group")
    public void handleAiResponse(String messageJson) {
        try {
            log.info("Received AI response: {}", messageJson);

            MessageDTO message = objectMapper.readValue(messageJson, MessageDTO.class);

            // Send the AI response to the specific conversation topic
            messagingTemplate.convertAndSend("/topic/messages/" + message.getConversationId(), message);

            // Store in history
            kafkaTemplate.send(HISTORY_MESSAGES_TOPIC, messageJson);

            // Store the AI message in the database
            chatService.storeMessageInRedis(message);
        } catch (Exception e) {
            log.error("Error handling AI response", e);
        }
    }

}
