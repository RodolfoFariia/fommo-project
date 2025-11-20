package com.fommo_project.dto;

import java.time.LocalDate;

public record RegisterDTO(String nome, LocalDate data_nascimento, String email, String senha) {
}
