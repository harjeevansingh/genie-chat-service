package com.chatservice.dto;

import lombok.Data;

/**
 * Author: harjeevansingh
 */

@Data
public class ConversationRequestDTO {
    private Long userId;
    private String title;
}