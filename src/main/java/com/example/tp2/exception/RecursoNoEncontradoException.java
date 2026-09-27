package com.example.tp2.exception;

public class RecursoNoEncontradoException extends  RuntimeException{
    public RecursoNoEncontradoException(String mensaje){
        super(mensaje);
    }
}
