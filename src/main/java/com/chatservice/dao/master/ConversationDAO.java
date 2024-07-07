package com.chatservice.dao.master;

import com.chatservice.entities.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: harjeevansingh
 */

@Repository
public interface ConversationDAO extends JpaRepository<Conversation, Long> {
    Page<Conversation> findByUserId(Long userId, Pageable pageable);
}

