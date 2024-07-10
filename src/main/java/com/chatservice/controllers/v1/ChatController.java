package com.chatservice.controllers.v1;

import com.chatservice.dto.ConversationRequestDTO;
import com.chatservice.dto.ConversationResponseDTO;
import com.chatservice.dto.MessageDTO;
import com.chatservice.entities.Conversation;
import com.chatservice.services.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: harjeevansingh
 */


@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    @Autowired
    private ChatService chatService;

    @PostMapping("/conversations")
    public ResponseEntity<ConversationResponseDTO> createConversation(@RequestBody ConversationRequestDTO conversationRequestDTO) {
        Conversation conversation = chatService.createConversation(conversationRequestDTO.getUserId(), conversationRequestDTO.getTitle());
        return ResponseEntity.ok(ConversationResponseDTO.fromConversation(conversation));
    }

    @GetMapping("/conversations/{userId}")
    public ResponseEntity<List<ConversationResponseDTO>> getUserConversations(@PathVariable Long userId
            , @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<Conversation> conversations = chatService.getUserConversations(userId, page, size);
        List<ConversationResponseDTO> conversationResponseDTOS = conversations.stream()
                .map(ConversationResponseDTO::fromConversation)
                .toList();
        return ResponseEntity.ok(conversationResponseDTOS);
    }

    @GetMapping("/conversations/{conversationId}/messages")
    public ResponseEntity<List<MessageDTO>> getRecentMessages(@PathVariable Long conversationId) {
        List<MessageDTO> messages = chatService.getRecentMessages(conversationId);
        return ResponseEntity.ok(messages);
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload MessageDTO messageDTO) {
        log.info("Received message: {}", messageDTO);
        chatService.handleUserMessages(messageDTO);
    }
}