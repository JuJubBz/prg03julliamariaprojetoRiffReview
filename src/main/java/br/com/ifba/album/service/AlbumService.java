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
        
        // Validação 2: Checagem do ID do tipo primitivo 'long' herdado da PersistenceEntity
        if (album.getId() != 0) {
            throw new RuntimeException("Álbum já existente no Banco de dados!");
        }
        
        // Validação de Negócio: O nome do álbum é obrigatório conforme o UML
        if (album.getNome() == null || album.getNome().trim().isEmpty()) {
            throw new RuntimeException("O nome do álbum é obrigatório!");
        }
        
        // Validação de Negócio: Ano de lançamento coerente
        if (album.getAnoLancamento() <= 0) {
            throw new RuntimeException("O ano de lançamento informado é inválido!");
        }

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
    public Album findById(Long id) throws RuntimeException {
        if (id == null) {
            throw new RuntimeException("ID fornecido é inválido!");
        }
        return albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Álbum não encontrado!"));
    }

    @Override
    public List<Album> findByNome(String nome) throws RuntimeException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new RuntimeException("O termo de busca por nome não pode estar vazio!");
        }
        return albumRepository.findByNomeIgnoreCase(nome);
    }

    @Override
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

    /*@Override
    public void calcularMediaNotas(Long id) throws RuntimeException {
        Album album = this.findById(id);
        
        // Estrutura pronta para quando a classe AvaliacaoAlbum for implementada
        System.out.println("Calculando a média das avaliações do álbum: " + album.getNome());
    }

    @Override
    public void exibirTracklist(Long id) throws RuntimeException {
        Album album = this.findById(id);
        
        System.out.println("=== Tracklist do álbum: " + album.getNome() + " ===");
        if (album.getMusicas() == null || album.getMusicas().isEmpty()) {
            System.out.println("Nenhuma música cadastrada neste álbum.");
        } else {
            for (Musica musica : album.getMusicas()) {
                System.out.println("- " + musica.getTitulo() + " (" + musica.getDuracao() + ")");
            }
        }
    }  */
    
}
