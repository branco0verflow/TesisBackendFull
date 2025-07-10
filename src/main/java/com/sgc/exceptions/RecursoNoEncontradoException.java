package com.sgc.exceptions;

public class RecursoNoEncontradoException extends RuntimeException { //Creado para manejar errores de Seguimientos de usarios. Entre otras
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
