package com.softline.chamados.configuration;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {

        this.messagingTemplate = messagingTemplate;
    }

    public void sendNewChamadoNotification(String ticket) {

        messagingTemplate.convertAndSend("/topic/novochamado", "Novo chamado criado! Ticket: " + ticket);
    }


}