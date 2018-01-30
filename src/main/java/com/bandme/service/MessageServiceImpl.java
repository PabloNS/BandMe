package com.bandme.service;

import com.bandme.model.Message;
import com.bandme.model.Post;
import com.bandme.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{

    public static final int PAGE_SIZE = 10;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public long countAll() {
        return messageRepository.count();
    }

    @Override
    public Long countNewMessagesOfUserByUserId(Long userId) {
        return messageRepository.countNewMessagesOfUserByUserId(userId);
    }

    @Override
    public List<Message> findAllLimited(int page) {
        //Pageable limit = new PageRequest(page,PAGE_SIZE);
        PageRequest request =
                new PageRequest(page, PAGE_SIZE, Sort.Direction.DESC, "date");
        Page<Message> pageMessages = messageRepository.findAll(request);
        return pageMessages.getContent();
    }
}
