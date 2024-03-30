package edu.java.service;

import org.springframework.stereotype.Service;

@Service
public interface TgChatService {
    void register(long chatId);

    void unregister(long chatId);

    void setStatus(long chatId, long status);

    long getStatus(long chatId);
}