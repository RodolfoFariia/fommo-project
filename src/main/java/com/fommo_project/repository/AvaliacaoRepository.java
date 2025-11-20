package com.fommo_project.repository;

import com.fommo_project.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    @Query("SELECT a FROM Avaliacao a WHERE a.usuario.id_usuario = :id")
    List<Avaliacao> findByUser(@Param("id") Long id);
}
