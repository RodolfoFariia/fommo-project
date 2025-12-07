package com.fommo_project.service;

import com.fommo_project.client.SpotifyApiClient;
import com.fommo_project.client.SpotifyAuthClient;
import com.fommo_project.dto.*;
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

    // Método para receber as requisições de itens pelo id do spotify
    public ItemSpotifyResponseDto getItemById(String type, String id){
        Album album = null;
        Artist artist = null;
        Track track = null;



        if(type.equals("Album")){
            album = getAlbumById(id);
        }
        else if (type.equals("Track")){
            track = getTrackById(id);
        }
        else {
            artist = getArtistById(id);
        }

        ItemSpotifyResponseDto itemResponse = new ItemSpotifyResponseDto(
                type,
                album,
                artist,
                track
        );

        return itemResponse;
    }

    // Método privado da classe para retornar um album pelo id
    private Album getAlbumById(String id){
        String token = "Bearer " + getAccessToken();

        return apiClient.getAlbum(token, id);
    }

    // Método privado para  buscar uma música pelo id
    private Track getTrackById(String id){
        String token = "Bearer " + getAccessToken();

        return apiClient.getTrack(token,id);
    }

    // Método privado para  buscar um artista pelo id
    private Artist getArtistById(String id){
        String token = "Bearer " + getAccessToken();

        return apiClient.getArtist(token,id);
    }
}
