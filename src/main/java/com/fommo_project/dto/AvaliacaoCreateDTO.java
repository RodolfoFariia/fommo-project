package com.fommo_project.dto;

import lombok.Data;

@Data
public class AvaliacaoCreateDTO {

    private String id_item_externo;
    private String tipo_item; // "MUSICA", "ALBUM", "ARTISTA"
    private Integer nota;
    private String titulo;
    private String textoAvaliacao;
}
