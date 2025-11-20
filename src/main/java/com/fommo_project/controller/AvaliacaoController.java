package com.fommo_project.controller;

import com.fommo_project.dto.AvaliacaoCreateDTO;
import com.fommo_project.dto.AvaliacaoResponseDTO;
import com.fommo_project.dto.AvaliacaoUpdateDTO;
import com.fommo_project.model.Avaliacao;
import com.fommo_project.service.AvaliacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    private AvaliacaoService service;

    private AvaliacaoController(AvaliacaoService service){
        this.service = service;
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }


    // endpoint para retornar todos as avaliacoes
    @GetMapping
    public ResponseEntity<List<Avaliacao>> findAll(){
        List<Avaliacao> lista = service.findAll();

        return ResponseEntity.ok(lista);
    }

    // endpoint para cadastrar uma avaliacao
    @PostMapping
    public ResponseEntity<Avaliacao> save(@RequestBody AvaliacaoCreateDTO dto){
        Avaliacao avaliacao = service.save(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacao);
    }

    // endpoint para remoção de uma avaliação
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id){
        service.remove(id);
        return ResponseEntity.noContent().build();
    }

    // endpoint para atualizar uma avaliação já cadastrada pelo id
    @PutMapping("/{id}")
    public ResponseEntity<Avaliacao> update (@PathVariable Long id, @RequestBody AvaliacaoUpdateDTO dto){
        Avaliacao av = service.update(id, dto);

        return ResponseEntity.ok(av);
    }

    // endpoint para consultar uma avaliação pelo id
    @GetMapping("/{id}")
    public ResponseEntity<Avaliacao> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }



    // endpoint para retornar avaliacoes feitas pelo id do user
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> findByUser(@PathVariable Long id){
        return ResponseEntity.ok(service.findByUser(id));
    }

}
