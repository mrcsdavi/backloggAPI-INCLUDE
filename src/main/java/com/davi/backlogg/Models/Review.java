package com.davi.backlogg.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {
    private Long id;

    @JsonProperty("Nota")      // opcional: se quiser nome com underscore
    private int nota;

    @JsonProperty("Comentario")
    private String comentario;

    @JsonProperty("Usuario")
    private String usuario;

    @JsonProperty("ID_Jogo")
    private Long jogoReferencia; // campo obrigatório quando usar POST /reviews
}
