package com.chatservice.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Author: harjeevansingh
 */

@Data
public class MessageDTO {
    private Long id;
    private Long conversationId;
    private SenderType senderType;
    private String content;
    private LocalDateTime timestamp = LocalDateTime.now();

    public enum SenderType {
        USER, AI
    }
}
