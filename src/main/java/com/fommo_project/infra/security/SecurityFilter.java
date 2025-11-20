package com.fommo_project.infra.security;

import com.fommo_project.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        // tenta recuperar o token da requisição
        var token = this.recoverToken(request);

        if(token != null){
            // validando o token
            var login = tokenService.validateToken(token);

            // token válido retorna o email do user
            if(!login.isEmpty()){
                // buscar o usuário no banco
                UserDetails user = usuarioRepository.findByEmail(login);

                // criando um objeto de autenticação do spring
                var authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());


                // salva o usuário
                // é aqui que o spring entende que estamos logados
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }
        // chama o próximo filtro
        filterChain.doFilter(request, response);
    }

    // método para recuperar o token do cabeçalho Authorization
    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;

        // removendo o prefixo Bearer que vem no cabeçalho
        return authHeader.replace("Bearer ","");
    }
}
