package com.bandme.repository;

import com.bandme.model.Message;
import com.bandme.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.xml.namespace.QName;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT COUNT(m) FROM Message m WHERE m.readMessage = 0 AND m.toUser.id = :toUserId")
    Long countNewMessagesOfUserByUserId(@Param("toUserId") Long toUserId);
}
