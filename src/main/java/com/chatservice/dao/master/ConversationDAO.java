/**
 * Author: harjeevansingh
 * Date:05/07/24
 * Time:11:29 pm
 */


package com.chatservice.dao.master;

import com.chatservice.entities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationDAO extends JpaRepository<Conversation, Long> {
}

