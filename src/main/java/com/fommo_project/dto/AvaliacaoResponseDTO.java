package com.fommo_project.dto;

import lombok.Data;

@Data
public class AvaliacaoResponseDTO {
    private Long id_avaliacao;
    private String titulo;
    private String texto_avaliacao;
    private Integer nota;
    private String tipo_item;
    private String id_item_externo;
}
