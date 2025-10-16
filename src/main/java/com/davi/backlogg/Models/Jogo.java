package com.davi.backlogg.Models;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Jogo {
    private Long id;

    @JsonProperty("titulo_jogo")  // no JSON será "titulo_jogo"
    private String titulo;

    @JsonProperty("genero_jogo")
    private String genero;

    @JsonProperty("plataforma_jogo")
    private String plataforma;

    @JsonProperty("data")  // no JSON será "data"
    private LocalDate dataLancamento;

    @JsonProperty("imagem")  // no JSON será "imagem"
    private String imagemUrl;
}
