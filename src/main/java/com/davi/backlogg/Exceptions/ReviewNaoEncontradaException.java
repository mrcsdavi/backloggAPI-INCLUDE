package com.davi.backlogg.Exceptions;

public class ReviewNaoEncontradaException extends RuntimeException {
    public ReviewNaoEncontradaException(Long reviewId) {
        super("Review com ID " + reviewId + " não foi encontrada.");
    }
    public ReviewNaoEncontradaException(Long jogoId, Long reviewId) {
        super("Review com ID " + reviewId + " não foi encontrada para o jogo " + jogoId + ".");
    }
}