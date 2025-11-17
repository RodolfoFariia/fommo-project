package com.fommo_project.dto;

import com.fommo_project.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class AvalicaoCreateDTO {

    private String id_item_externo;
    private String tipo_item; // "MUSICA", "ALBUM", "ARTISTA"
    private Integer nota;
    private String titulo;
    private String textoAvaliacao;
}
