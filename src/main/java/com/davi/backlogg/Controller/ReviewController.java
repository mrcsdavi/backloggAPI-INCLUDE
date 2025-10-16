package com.davi.backlogg.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davi.backlogg.Exceptions.NaoEncontradoException;
import com.davi.backlogg.Exceptions.ReviewNaoEncontradaException;
import com.davi.backlogg.Models.Jogo;
import com.davi.backlogg.Models.Review;

@RestController
@RequestMapping({"/reviews", "/reviews/"})
public class ReviewController {

    private final List<Review> reviews = new ArrayList<>();
    private Long proximoId = 1L;
    private final JogoController jogoController;
    
    // Mapa para controlar os IDs sequenciais por jogo
    private final java.util.Map<Long, AtomicLong> proximoIdPorJogo = new java.util.HashMap<>();

    public ReviewController(JogoController jogoController) {
        this.jogoController = jogoController;
    }

    // Criar uma review informando o ID do jogo manualmente pelo Postman
    @PostMapping
    public Review criar(@RequestBody Review review) {
        // Verifica se o jogo informado existe
        Jogo jogo = jogoController.buscar(review.getJogoReferencia());
        if (jogo == null) {
            throw new NaoEncontradoException(review.getJogoReferencia());
        }

        review.setId(proximoId++);
        reviews.add(review);
        return review;
    }

    // Criar review para um jogo específico
    @PostMapping("/jogo/{jogoId}")
    public Review criarReviewParaJogo(@PathVariable Long jogoId, @RequestBody Review review) {
        // Verifica se o jogo existe
        Jogo jogo = jogoController.buscar(jogoId);
        if (jogo == null) {
            throw new NaoEncontradoException(jogoId);
        }

        // Define o ID da review de forma sequencial para este jogo (1, 2, 3...)
        Long reviewId = gerarProximoIdParaJogo(jogoId);
        review.setId(reviewId);
        review.setJogoReferencia(jogoId);
        
        reviews.add(review);
        return review;
    }

    // Vincular uma review já existente a um jogo específico
    @PostMapping("/jogo/{jogoId}/review/{reviewId}")
    public String vincularReview(@PathVariable Long jogoId, @PathVariable Long reviewId) {
        // Verifica se o jogo existe
        Jogo jogo = jogoController.buscar(jogoId);
        if (jogo == null) {
            throw new NaoEncontradoException(jogoId);
        }

        // Verifica se a review existe - LANÇA EXCEÇÃO SE NÃO ENCONTRAR
        Review review = buscar(reviewId);

        // Faz o vínculo
        review.setJogoReferencia(jogoId);
        return "Review " + reviewId + " vinculada ao jogo " + jogoId + " com sucesso!";
    }

    // Listar todas as avaliações
    @GetMapping
    public List<Review> listarTodas() {
        return reviews;
    }

    // Listar avaliações por jogo
    @GetMapping("/jogo/{jogoId}")
    public List<Review> listarPorJogo(@PathVariable Long jogoId) {
        // Verifica se o jogo existe
        jogoController.buscar(jogoId);
        
        return reviews.stream()
                .filter(r -> jogoId.equals(r.getJogoReferencia()))
                .toList();
    }

    // Buscar avaliação específica
    @GetMapping("/{id}")
    public Review buscar(@PathVariable Long id) {
        return reviews.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ReviewNaoEncontradaException(id));
    }

    // Buscar review específica de um jogo específico
    @GetMapping("/jogo/{jogoId}/review/{reviewId}")
    public Review buscarReviewDoJogo(@PathVariable Long jogoId, @PathVariable Long reviewId) {
        // Verifica se o jogo existe
        jogoController.buscar(jogoId);
        
        // Busca a review específica - LANÇA EXCEÇÃO SE NÃO ENCONTRAR
        Review review = reviews.stream()
                .filter(r -> r.getId().equals(reviewId) && jogoId.equals(r.getJogoReferencia()))
                .findFirst()
                .orElseThrow(() -> new ReviewNaoEncontradaException(jogoId, reviewId));
        
        return review;
    }

    // Deletar review específica de um jogo
    @DeleteMapping("/jogo/{jogoId}/review/{reviewId}")
    public String removerReviewDoJogo(@PathVariable Long jogoId, @PathVariable Long reviewId) {
        // Verifica se o jogo existe
        jogoController.buscar(jogoId);
        
        // Busca e remove a review - LANÇA EXCEÇÃO SE NÃO ENCONTRAR
        Review review = buscarReviewDoJogo(jogoId, reviewId);
        reviews.remove(review);
        
        return "Review " + reviewId + " do jogo " + jogoId + " removida com sucesso!";
    }

    // Deletar avaliação (geral)
    @DeleteMapping("/{id}")
    public String remover(@PathVariable Long id) {
        // Busca e remove a review - LANÇA EXCEÇÃO SE NÃO ENCONTRAR
        Review review = buscar(id);
        reviews.remove(review);
        return "Review com ID " + id + " removida com sucesso!";
    }

    private Long gerarProximoIdParaJogo(Long jogoId) {
        proximoIdPorJogo.putIfAbsent(jogoId, new AtomicLong(1L));
        return proximoIdPorJogo.get(jogoId).getAndIncrement();
    }
}