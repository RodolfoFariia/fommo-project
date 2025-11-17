package com.fommo_project.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioUpdateDTO {
    private String nome;
    private LocalDate data_nascimento;
    private String email;
    private String senha;
}
