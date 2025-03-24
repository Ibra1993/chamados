package com.softline.chamados.exception;


public class ChamadoNotFoundException extends Exception{

    public ChamadoNotFoundException(String message) {
        super(String.valueOf(message));

    }

}