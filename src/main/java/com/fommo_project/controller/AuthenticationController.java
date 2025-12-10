package com.fommo_project.controller;

import com.fommo_project.dto.AuthenticationDTO;
import com.fommo_project.dto.LoginResponseDTO;
import com.fommo_project.dto.RegisterDTO;
import com.fommo_project.infra.security.TokenService;
import com.fommo_project.model.Usuario;
import com.fommo_project.repository.UsuarioRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    // responsável por fazer a mágica do login
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data){
        // Encapsula as credencias (email e senha) em um objeto que o Spring Security entende
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(data.email(), data.senha());

        // Nesse passo o authentication Manager faz o seguinte:
        // Chama UserDetailsService
        // Busca o usuário no banco pelo email
        // Compara a senha enviada com o hash do banco / usando o bcrypt
        // se tudo der certo retorna o objeto auth, caso contrário lança erro 403
        var auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // com o auth vamos recuperar o usuário para passar pro TokenService
        var usuario = (Usuario) auth.getPrincipal();

        // Gerar o token
        var token = tokenService.generateToken(usuario);

        // retornar o dto de resposta que contém o token
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register (@RequestBody RegisterDTO dto){
        // verificando se já existe usuário cadastrado com o mesmo email
        if(this.repository.findByEmail(dto.email()) != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // criptografando a senha
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.senha());

        // Criando usuário
        Usuario newUser = new Usuario();
        newUser.setEmail(dto.email());
        newUser.setNome(dto.nome());
        newUser.setSenha(encryptedPassword); // guardando o hash da senha no banco
        newUser.setData_nascimento(dto.data_nascimento());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
