package com.bandme.service;

import com.bandme.model.Message;

import java.util.List;

public interface MessageService {
    public void saveMessage(Message post);
    public List<Message> findAllLimited(int page);
    public long countAll();
    public Long countNewMessagesOfUserByUserId(Long userId);
    public void deleteMessage(Long idMessage);
    public Message readMessage(Long idMessage);
}
