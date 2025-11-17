package com.fommo_project.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioCreateDTO {

    private String nome;
    private LocalDate data_nascimento;
    private String email;
    private String senha;
}
