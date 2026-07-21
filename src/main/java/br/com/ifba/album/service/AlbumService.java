/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.album.service;

import br.com.ifba.album.entity.Album;
import br.com.ifba.album.repository.AlbumRepository;
import br.com.ifba.musica.entity.Musica;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Julia Freitas
 */

@Service
@org.springframework.transaction.annotation.Transactional(readOnly = true)
public class AlbumService implements AlbumIService{
    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public List<Album> findAll() throws RuntimeException {
        return albumRepository.findAll();
    }

    @Override
    public Album save(Album album) throws RuntimeException {
        // Validação 1: O objeto não pode ser nulo
        if (album == null) {
            throw new RuntimeException("Dados do álbum não preenchidos!");
        }
        
        // Validação de Negócio: O nome do álbum é obrigatório conforme o UML
        if (album.getNome() == null || album.getNome().trim().isEmpty()) {
            throw new RuntimeException("O nome do álbum é obrigatório!");
        }
        
        // Validação de Negócio: Ano de lançamento coerente
        if (album.getAnoLancamento() <= 0) {
            throw new RuntimeException("O ano de lançamento informado é inválido!");
        }

        // O Spring Data JPA cria (INSERT) se não houver ID e atualiza (UPDATE) se o ID já existir
        return albumRepository.save(album);
    }

    @Override
    public Album update(Album album) throws RuntimeException {
        if (album == null) {
            throw new RuntimeException("Dados do álbum não preenchidos!");
        }
        
        // Para atualizar, o registro NECESSITA existir previamente no banco
        if (album.getId() == 0 || !albumRepository.existsById(album.getId())) {
            throw new RuntimeException("Álbum não encontrado para atualização!");
        }
        
        if (album.getNome() == null || album.getNome().trim().isEmpty()) {
            throw new RuntimeException("O nome do álbum não pode ficar vazio!");
        }

        return albumRepository.save(album);
    }

    @Override
    public void delete(Long id) throws RuntimeException {
        if (id == null) {
            throw new RuntimeException("ID inválido para exclusão!");
        }
        
        if (!albumRepository.existsById(id)) {
            throw new RuntimeException("Álbum não encontrado no banco de dados!");
        }

        albumRepository.deleteById(id);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public Album findById(Long id) throws RuntimeException {
        if (id == null) {
            throw new RuntimeException("ID fornecido é inválido!");
        }
        return albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Álbum não encontrado!"));
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public List<Album> findByNome(String nome) throws RuntimeException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new RuntimeException("O termo de busca por nome não pode estar vazio!");
        }
        return albumRepository.findByNomeIgnoreCase(nome);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public List<Album> findByAnoLancamento(int anoLancamento) throws RuntimeException {
        if (anoLancamento <= 0) {
            throw new RuntimeException("O ano de lançamento fornecido é inválido!");
        }
        return albumRepository.findByAnoLancamento(anoLancamento);
    }

    // --- MÉTODOS DE DIRETRIZ E REGRAS DE NEGÓCIO DO UML ---

    @Override
    public void adicionarMusica(Long albumId, Musica musica) throws RuntimeException {
        Album album = this.findById(albumId);
        
        if (musica == null) {
            throw new RuntimeException("A música a ser adicionada não pode ser nula!");
        }
        
        // Associa a música ao álbum de forma bidirecional
        musica.setAlbum(album);
        album.getMusicas().add(musica);
        
        albumRepository.save(album);
        System.out.println("Música '" + musica.getTitulo() + "' adicionada com sucesso ao álbum: " + album.getNome());
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public double calcularMediaNotas(Long id) throws RuntimeException {
        Album album = this.findById(id);
        
        // Se o álbum não tiver nenhuma avaliação cadastrada ainda, retorna média 0.0
        if (album.getListaAvaliacoes() == null || album.getListaAvaliacoes().isEmpty()) {
            return 0.0;
        }
        
        // Soma todas as notas da lista de AvaliacaoAlbum
        double soma = 0;
        for (br.com.ifba.avaliacao.entity.AvaliacaoAlbum avaliacao : album.getListaAvaliacoes()) {
            soma += avaliacao.getNota(); // Supondo que o atributo na classe Avaliacao seja 'nota'
        }
        
        // Retorna o cálculo da média
        return soma / album.getListaAvaliacoes().size();
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Musica> exibirTracklist(Long id) throws RuntimeException {
        Album album = this.findById(id);
        
        // Inicializa a lista caso esteja nula para evitar NullPointerException na View
        if (album.getMusicas() == null) {
            return java.util.Collections.emptyList();
        }
        
        return album.getMusicas();
    } 
    
}
