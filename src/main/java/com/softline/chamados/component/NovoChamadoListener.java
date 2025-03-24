package com.softline.chamados.component;

import com.softline.chamados.configuration.NotificationService;
import com.softline.chamados.configuration.NovoChamadoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NovoChamadoListener {

    private final NotificationService notificationService;

    public NovoChamadoListener(NotificationService notificationService) {

        this.notificationService = notificationService;
    }
    @EventListener
    public void onNovoChamado(NovoChamadoEvent event) {

        System.out.println("🚀 Novo chamado criado! Ticket: " + event.getTicket());

        // Envia a notificação via WebSocket
        notificationService.sendNewChamadoNotification(event.getTicket());

    }
}
