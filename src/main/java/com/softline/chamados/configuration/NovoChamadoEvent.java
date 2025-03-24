package com.softline.chamados.configuration;
import org.springframework.context.ApplicationEvent;

public class NovoChamadoEvent extends ApplicationEvent {

    private final String ticket;

    public NovoChamadoEvent(Object source, String ticket) {
        super(source);
        this.ticket = ticket;
    }

    public String getTicket() {

        return ticket;
    }

}