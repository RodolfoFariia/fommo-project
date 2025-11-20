package com.fommo_project.infra.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fommo_project.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Busca a senha que foi definida no aplication properties
    @Value("${api.security.token.secret}")
    private String secret;

    // Método para gerar o token
    public String generateToken(Usuario usuario){
        try{
            // Definindo o algoritmo de criptografia
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // criando o token
            String token = JWT.create()
                    .withIssuer("fommo-api") // emissor do token
                    .withSubject(usuario.getEmail()) // o dono do token (email é o utilizado no login)
                    .withExpiresAt(genExpirationDate()) // validade do token
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException exception){
            throw  new RuntimeException("Erro ao gerar token.",exception);
        }
    }

    // Método para definir a validade do token, vamos definir como 1 hora
    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }


    // Método para validar o token
    // Token válido   -> retorna email
    // Token inválido -> retorna vazio
    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("fommo-api") // verificando se o token tem o emissor correto
                    .build()
                    .verify(token) // realiza descriptografia e verifica validade
                    .getSubject(); // se passou pelos anteriores pega o email e retorna
        } catch (JWTVerificationException exception){
            return "";
        }
    }


}
