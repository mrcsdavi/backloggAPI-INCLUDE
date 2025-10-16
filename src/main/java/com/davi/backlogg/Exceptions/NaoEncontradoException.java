package com.davi.backlogg.Exceptions;

public class NaoEncontradoException extends RuntimeException {
    public NaoEncontradoException (Long id) {
        super("Jogo com ID " + id + " não foi encontrado.");
    }
}