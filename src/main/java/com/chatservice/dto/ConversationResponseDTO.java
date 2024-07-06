package com.chatservice.dto;

import com.chatservice.entities.Conversation;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Author: harjeevansingh
 */

@Data
public class ConversationResponseDTO {
    private Long id;
    private Long userId;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ConversationResponseDTO fromConversation(Conversation conversation) {
        ConversationResponseDTO dto = new ConversationResponseDTO();
        dto.setId(conversation.getId());
        dto.setUserId(conversation.getUserId());
        dto.setTitle(conversation.getTitle());
        dto.setCreatedAt(conversation.getCreatedAt());
        dto.setUpdatedAt(conversation.getUpdatedAt());
        return dto;
    }
}