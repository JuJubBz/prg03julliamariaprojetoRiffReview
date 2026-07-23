/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.album.service;

import br.com.ifba.album.entity.Album;
import br.com.ifba.album.repository.AlbumRepository;
import br.com.ifba.musica.entity.Musica;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Julia Freitas
 */

@Slf4j
@Service
@org.springframework.transaction.annotation.Transactional
public class AlbumService implements AlbumIService{
    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public List<Album> findAll() throws RuntimeException {
        log.info("Iniciando busca por todos os álbuns.");
        List<Album> albuns = albumRepository.findAll();
        log.info("Busca finalizada. Total de álbuns encontrados: {}", albuns.size());
        return albuns;
    }

    @Override
    public Album save(Album album) throws RuntimeException {
        log.info("Iniciando cadastro de álbum.");

        if (album == null) {
            log.error("Objeto Album recebido é nulo.");
            throw new RuntimeException("Dados do álbum não preenchidos!");
        }
        
        if (album.getNome() == null || album.getNome().trim().isEmpty()) {
            log.warn("Tentativa de cadastrar álbum sem nome.");
            throw new RuntimeException("O nome do álbum é obrigatório!");
        }
        
        if (album.getAnoLancamento() <= 0) {
            log.warn("Tentativa de cadastrar álbum com ano de lançamento inválido: {}", album.getAnoLancamento());
            throw new RuntimeException("O ano de lançamento informado é inválido!");
        }

        Album salvo = albumRepository.save(album);
        log.info("Álbum '{}' cadastrado com sucesso. ID={}", salvo.getNome(), salvo.getId());
        return salvo;
    }

    @Override
    public Album update(Album album) throws RuntimeException {
        log.info("Iniciando atualização de álbum.");

        if (album == null) {
            log.error("Objeto Album recebido para atualização é nulo.");
            throw new RuntimeException("Dados do álbum não preenchidos!");
        }
        
        if (album.getId() == 0 || !albumRepository.existsById(album.getId())) {
            log.error("Álbum não encontrado para atualização. ID={}", album.getId());
            throw new RuntimeException("Álbum não encontrado para atualização!");
        }
        
        if (album.getNome() == null || album.getNome().trim().isEmpty()) {
            log.warn("Tentativa de atualizar álbum deixando o nome vazio.");
            throw new RuntimeException("O nome do álbum não pode ficar vazio!");
        }

        Album atualizado = albumRepository.save(album);
        log.info("Álbum ID={} atualizado com sucesso.", atualizado.getId());
        return atualizado;
    }

    @Override
    public void delete(Long id) throws RuntimeException {
        log.info("Iniciando exclusão do álbum com ID={}", id);

        if (id == null) {
            log.error("ID fornecido para exclusão é nulo.");
            throw new RuntimeException("ID inválido para exclusão!");
        }
        
        if (!albumRepository.existsById(id)) {
            log.error("Álbum não encontrado no banco de dados para exclusão. ID={}", id);
            throw new RuntimeException("Álbum não encontrado no banco de dados!");
        }

        albumRepository.deleteById(id);
        log.info("Álbum ID={} excluído com sucesso.", id);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public Album findById(Long id) throws RuntimeException {
        log.info("Buscando álbum por ID={}", id);

        if (id == null) {
            log.error("ID fornecido para busca é nulo.");
            throw new RuntimeException("ID fornecido é inválido!");
        }
        
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Álbum não encontrado para o ID={}", id);
                    return new RuntimeException("Álbum não encontrado!");
                });
                
        log.info("Álbum encontrado com sucesso: {}", album.getNome());
        return album;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public List<Album> findByNome(String nome) throws RuntimeException {
        log.info("Buscando álbuns por nome contendo: '{}'", nome);

        if (nome == null || nome.trim().isEmpty()) {
            log.warn("Termo de busca por nome vazio.");
            throw new RuntimeException("O termo de busca por nome não pode estar vazio!");
        }
        
        List<Album> albuns = albumRepository.findByNomeIgnoreCase(nome);
        log.info("Busca por nome finalizada. Resultados encontrados: {}", albuns.size());
        return albuns;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public List<Album> findByAnoLancamento(int anoLancamento) throws RuntimeException {
        log.info("Buscando álbuns pelo ano de lançamento: {}", anoLancamento);

        if (anoLancamento <= 0) {
            log.warn("Ano de lançamento inválido fornecido para busca: {}", anoLancamento);
            throw new RuntimeException("O ano de lançamento fornecido é inválido!");
        }
        
        List<Album> albuns = albumRepository.findByAnoLancamento(anoLancamento);
        log.info("Busca por ano finalizada. Resultados encontrados: {}", albuns.size());
        return albuns;
    }

    // --- MÉTODOS DE DIRETRIZ E REGRAS DE NEGÓCIO DO UML ---

    @Override
    public void adicionarMusica(Long albumId, Musica musica) throws RuntimeException {
        log.info("Adicionando música ao álbum ID={}", albumId);

        Album album = this.findById(albumId);
        
        if (musica == null) {
            log.error("Tentativa de adicionar uma música nula ao álbum ID={}", albumId);
            throw new RuntimeException("A música a ser adicionada não pode ser nula!");
        }
        
        musica.setAlbum(album);
        album.getMusicas().add(musica);
        
        albumRepository.save(album);
        log.info("Música '{}' adicionada com sucesso ao álbum: {}", musica.getTitulo(), album.getNome());
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public double calcularMediaNotas(Long id) throws RuntimeException {
        log.info("Calculando média de notas para o álbum ID={}", id);

        Album album = this.findById(id);
        
        if (album.getListaAvaliacoes() == null || album.getListaAvaliacoes().isEmpty()) {
            log.info("Álbum ID={} não possui avaliações. Média retornada: 0.0", id);
            return 0.0;
        }
        
        double soma = 0;
        for (br.com.ifba.avaliacao.entity.AvaliacaoAlbum avaliacao : album.getListaAvaliacoes()) {
            soma += avaliacao.getNota();
        }
        
        double media = soma / album.getListaAvaliacoes().size();
        log.info("Média calculada para o álbum ID={}: {}", id, media);
        return media;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<Musica> exibirTracklist(Long id) throws RuntimeException {
        log.info("Exibindo tracklist do álbum ID={}", id);

        Album album = this.findById(id);
        
        if (album.getMusicas() == null) {
            log.info("Tracklist vazia para o álbum ID={}", id);
            return java.util.Collections.emptyList();
        }
        
        log.info("Tracklist recuperada com sucesso para o álbum ID={}. Total de músicas: {}", id, album.getMusicas().size());
        return album.getMusicas();
    } 
    
}
