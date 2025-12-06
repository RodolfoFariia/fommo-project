package com.fommo_project.controller;

import com.fommo_project.dto.SenhaUpdateDTO;
import com.fommo_project.dto.UsuarioResponseDTO;
import com.fommo_project.dto.UsuarioUpdateDTO;
import com.fommo_project.model.Usuario;
import com.fommo_project.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    // endpoint para retornar todos os usuários cadastrados
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    // endpoint para atualizar os dados
    @PutMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> update(@RequestBody UsuarioUpdateDTO dto){
        // pegando o token do usuário
        Usuario user = getUsuarioLogado();

        UsuarioResponseDTO response = service.update(user, dto);
        return ResponseEntity.ok(response);
    }

    // endpoint para excluir o proprio perfil
    @DeleteMapping("/me")
    public ResponseEntity<Void> remove(){
        Usuario user = getUsuarioLogado();

        service.remove(user);
        return ResponseEntity.noContent().build();
    }

    // método para retornar o usuário logado
    private Usuario getUsuarioLogado(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Usuario) auth.getPrincipal();
    }

    // método de get sobre o meu perfil
    @GetMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> getMe(){
        Usuario user = getUsuarioLogado();
        return ResponseEntity.ok(service.getMe(user));
    }


    // endpoint para ver o perfil de um usuário pelo id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    // endpoint para alterar a senha do usuário
    @PatchMapping("/me/password")
    public ResponseEntity<Void> updateSenha(@RequestBody SenhaUpdateDTO dto){
        service.updateSenha(dto);
        System.out.println("Testando update Senha: ");
        System.out.println("Senha Antiga:"+dto.getSenha_antiga());
        System.out.println("Senha Nova:"+dto.getSenha_nova());
        return ResponseEntity.ok().build();
    }


}
