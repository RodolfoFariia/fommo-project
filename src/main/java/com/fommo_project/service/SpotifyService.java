package com.fommo_project.service;

import com.fommo_project.client.SpotifyApiClient;
import com.fommo_project.client.SpotifyAuthClient;
import com.fommo_project.dto.SpotifySearchResponseDTO;
import com.fommo_project.dto.SpotifyTokenResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SpotifyService {

    private final SpotifyAuthClient authClient;
    private final SpotifyApiClient apiClient;

    // Injeção das credenciais do application.properties
    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    public SpotifyService(SpotifyAuthClient authClient, SpotifyApiClient apiClient) {
        this.authClient = authClient;
        this.apiClient = apiClient;
    }

    // 1. Método Principal: Busca por albuns, artistas ou faixas
    public SpotifySearchResponseDTO search(String query, String type) {
        // Passo A: Pega um token válido
        String token = "Bearer " + getAccessToken();

        // Passo B: Faz a busca usando o token
        return apiClient.search(token, query, type);
    }

    // 2. Método Auxiliar: Autentica no Spotify e pega o Token
    private String getAccessToken() {
        // O Spotify exige os dados no formato x-www-form-urlencoded no corpo
        String body = "grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret;

        // Chama o Feign Client de Auth
        SpotifyTokenResponseDTO response = authClient.getAccessToken(body);

        return response.accessToken();
    }
}
