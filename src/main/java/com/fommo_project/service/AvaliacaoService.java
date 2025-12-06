package com.fommo_project.service;

import com.fommo_project.dto.AvaliacaoCreateDTO;
import com.fommo_project.dto.AvaliacaoResponseDTO;
import com.fommo_project.dto.AvaliacaoUpdateDTO;
import com.fommo_project.dto.UsuarioResponseDTO;
import com.fommo_project.model.Avaliacao;
import com.fommo_project.model.Usuario;
import com.fommo_project.repository.AvaliacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    private AvaliacaoRepository repository;
    public AvaliacaoService(AvaliacaoRepository repository){
        this.repository = repository;
    }

    // Método para salvar uma avaliação
    public AvaliacaoResponseDTO save(AvaliacaoCreateDTO dto, Usuario usuario){
        Avaliacao av = new Avaliacao();

        av.setId_item_externo(dto.getId_item_externo());
        av.setTipo_item(dto.getTipo_item());
        av.setNota(dto.getNota());
        av.setTitulo(dto.getTitulo());
        av.setTextoAvaliacao(dto.getTextoAvaliacao());

        av.setUsuario(usuario);

        repository.save(av);

        return mapToResponseDTO(av);
    }


    // Método para remoção de uma avaliação
    public void remove(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Avaliação não encontrada para remoção.");
        }

        repository.deleteById(id);
    }

    // Método para retornar todas as avaliações cadastradas no banco
    public List<Avaliacao> findAll(){
        return repository.findAll();
    }

    // Método para atualizar uma avaliação já cadastrada no banco pelo id
    public Avaliacao update(Long id, AvaliacaoUpdateDTO dto){
        Avaliacao av = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada para atualização."));

        av.setNota(dto.getNota());
        av.setTitulo(dto.getTitulo());
        av.setTextoAvaliacao(dto.getTextoAvaliacao());

        repository.save(av);

        return av;

    }


    // Método para retornar uma avaliação pelo id
    public Avaliacao findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliacao de id: "+id+" não encontrado."));
    }


    public List<AvaliacaoResponseDTO> findByUser(Long id_usuario){
        List <Avaliacao> avaliacoes = repository.findByUser(id_usuario);

        return avaliacoes.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    // método que retorna as avaliações do usuário que está logado
    public List<AvaliacaoResponseDTO> findByUserLogado(Usuario user){
        List<Avaliacao> avaliacoes = repository.findByUser(user.getId_usuario());

        return avaliacoes.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }


    private AvaliacaoResponseDTO mapToResponseDTO(Avaliacao av){
        AvaliacaoResponseDTO dto = new AvaliacaoResponseDTO();

        dto.setId_avaliacao(av.getId_avaliacao());
        dto.setTitulo(av.getTitulo());
        dto.setTexto_avaliacao(av.getTextoAvaliacao());
        dto.setNota(av.getNota());
        dto.setTipo_item(av.getTipo_item());
        dto.setId_item_externo(av.getId_item_externo());

        return dto;
    }


    // método para retornar avaliações pelo id do spotify
    public List<AvaliacaoResponseDTO> findByIdSpotify(String spotifyId){
        return repository.findByIdItemExterno(spotifyId)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }




}
