package com.fommo_project.service;

import com.fommo_project.dto.SenhaUpdateDTO;
import com.fommo_project.dto.UsuarioResponseDTO;
import com.fommo_project.dto.UsuarioUpdateDTO;
import com.fommo_project.model.Usuario;
import com.fommo_project.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repository;

    // Injetamos o encoder para criptografar senha no update
    @Autowired
    private PasswordEncoder passwordEncoder;

    // método para retornar todos os usuários
    public List<UsuarioResponseDTO> findAll(){
        List<Usuario> usuarios = repository.findAll();

        return usuarios.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    private UsuarioResponseDTO mapToResponseDTO(Usuario user){
        UsuarioResponseDTO dto = new UsuarioResponseDTO();

        dto.setId_usuario(user.getId_usuario());
        dto.setNome(user.getNome());
        dto.setEmail(user.getEmail());
        dto.setData_nascimento(user.getData_nascimento());

        return dto;
    }

    // método para retornar os dados do meu perfil
    public UsuarioResponseDTO getMe(Usuario user){
        return mapToResponseDTO(user);
    }

    // método para atualizar um usuário (é permitido alterar a senha)
    public UsuarioResponseDTO update(Usuario usuarioLogado, UsuarioUpdateDTO dto){
        // campos simples
        if(dto.getNome()!= null) usuarioLogado.setNome(dto.getNome());
        if(dto.getData_nascimento()!= null) usuarioLogado.setData_nascimento(dto.getData_nascimento());
        if(dto.getEmail()!= null) usuarioLogado.setEmail(dto.getEmail());

        // lógica da senha
        if(dto.getSenha() != null && !dto.getSenha().isEmpty()){
            String senhaHash = passwordEncoder.encode(dto.getSenha());
            usuarioLogado.setSenha(senhaHash);
        }

        repository.save(usuarioLogado);
        return mapToResponseDTO(usuarioLogado);
    }

    // método para remover um usuário
    public void remove(Usuario usuarioLogado){
        repository.delete(usuarioLogado);
    }


    // método para encontrar um usuário pelo id
    public UsuarioResponseDTO findById(Long id){
        Usuario user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return mapToResponseDTO(user);
    }


    // método para atualizar senha do usuário
    public void updateSenha(SenhaUpdateDTO dto){
        Usuario user = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // passwordEnconder recebe uma senha raw e compara com o hash do banco
        // para isso vai fazer o processo de hash na senha raw com o msm algoritmo da senha do banco
        // retorna true se forem iguais e false caso contrário
        if(!passwordEncoder.matches(dto.getSenha_antiga() , user.getPassword())){
            throw new RuntimeException("Senha atual incorreta!");
        }

        String senhaHash = passwordEncoder.encode(dto.getSenha_nova());
        user.setSenha(senhaHash);

        repository.save(user);
    }

}
