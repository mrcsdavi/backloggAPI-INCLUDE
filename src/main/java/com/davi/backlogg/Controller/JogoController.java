package com.davi.backlogg.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davi.backlogg.Exceptions.NaoEncontradoException;
import com.davi.backlogg.Models.Jogo;

@RestController
@RequestMapping({"/jogos", "/jogos/"})
public class JogoController {

    private final List<Jogo> jogos = new ArrayList<>();
    private Long proximoId = 1L;

    // Listar todos os jogos
    @GetMapping
    public List<Jogo> listarTodos() {
        return jogos;
    }

    // Criar novo jogo
    @PostMapping
    public Jogo criar(@RequestBody Jogo jogo) {
        jogo.setId(proximoId++);
        jogos.add(jogo);
        return jogo;
    }

    // Buscar jogo por ID
    @GetMapping("/{id}")
    public Jogo buscar(@PathVariable Long id) {
        return jogos.stream()
            .filter(j -> j.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new NaoEncontradoException(id));
    }

    // Atualizar jogo
    @PutMapping("/{id}")
    public Jogo atualizar(@PathVariable Long id, @RequestBody Jogo jogoAtualizado) {
        Jogo jogo = buscar(id);
        jogo.setTitulo(jogoAtualizado.getTitulo());
        jogo.setGenero(jogoAtualizado.getGenero());
        jogo.setPlataforma(jogoAtualizado.getPlataforma());
        jogo.setDataLancamento(jogoAtualizado.getDataLancamento());
        jogo.setImagemUrl(jogoAtualizado.getImagemUrl());
        return jogo;
    }

    // Remover jogo
    @DeleteMapping("/{id}")
    public String remover(@PathVariable Long id) {
        Jogo jogo = buscar(id);
        jogos.remove(jogo);
        return "Jogo removido com sucesso!";
    }
}
