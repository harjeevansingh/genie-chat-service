package com.chatservice.dto;

import lombok.Data;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Author: harjeevansingh
 */

@Data
public class MessageDTO {
    private Long id;
    private Long conversationId;
    private SenderType senderType;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime timestamp = LocalDateTime.now();

    public enum SenderType {
        USER, AI
    }
}
