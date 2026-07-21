/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.musica.service;

import br.com.ifba.musica.entity.Musica;
import br.com.ifba.musica.repository.MusicaRepository;
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
public class MusicaService implements MusicaIService{
    
    @Autowired
    private MusicaRepository musicaRepository;

    @Override
    public List<Musica> findAll() throws RuntimeException {
        log.info("Iniciando busca por todas as músicas.");
        List<Musica> musicas = musicaRepository.findAll();
        log.info("Busca finalizada. Total de músicas encontradas: {}", musicas.size());
        return musicas;
    }

    @Override
    public Musica save(Musica musica) throws RuntimeException {
        log.info("Iniciando cadastro de música.");

        // Validação 1: Objeto não pode ser nulo
        if (musica == null) {
            log.error("Objeto Musica recebido é nulo.");
            throw new RuntimeException("Dados da música não preenchidos!");
        }
        
        // Validação 2: Se já tem ID, é uma edição e não deve ser salvo via save()
        if (musica.getId() != 0) {
            log.warn("Tentativa de cadastrar música que já possui ID={}", musica.getId());
            throw new RuntimeException("Música já existente no Banco de dados! Utilize o método de atualização.");
        }
        
        // Validação 3: Validação de campos obrigatórios
        if (musica.getTitulo() == null || musica.getTitulo().trim().isEmpty()) {
            log.warn("Tentativa de cadastrar música sem título.");
            throw new RuntimeException("O título da música é obrigatório!");
        }
        
        if (musica.getGeneroPrincipal() == null || musica.getGeneroPrincipal().trim().isEmpty()) {
            log.warn("Tentativa de cadastrar música sem gênero principal.");
            throw new RuntimeException("O gênero principal da música é obrigatório!");
        }

        Musica salva = musicaRepository.save(musica);
        log.info("Música '{}' cadastrada com sucesso. ID={}", salva.getTitulo(), salva.getId());
        return salva;
    }

    @Override
    public Musica update(Musica musica) throws RuntimeException {
        log.info("Iniciando atualização de música.");

        if (musica == null) {
            log.error("Objeto Musica recebido para atualização é nulo.");
            throw new RuntimeException("Dados da música não preenchidos!");
        }
        
        // Validação para garantir que o registro já existe antes de tentar atualizar
        if (musica.getId() == 0 || !musicaRepository.existsById(musica.getId())) {
            log.error("Música não encontrada para atualização. ID={}", musica.getId());
            throw new RuntimeException("Música não encontrada para atualização!");
        }
        
        if (musica.getTitulo() == null || musica.getTitulo().trim().isEmpty()) {
            log.warn("Tentativa de atualizar música deixando o título vazio.");
            throw new RuntimeException("O título da música não pode ficar vazio!");
        }

        Musica atualizada = musicaRepository.save(musica);
        log.info("Música ID={} atualizada com sucesso.", atualizada.getId());
        return atualizada;
    }

    @Override
    public void delete(Long id) throws RuntimeException {
        log.info("Iniciando exclusão da música com ID={}", id);

        if (id == null) {
            log.error("ID fornecido para exclusão é nulo.");
            throw new RuntimeException("ID inválido para exclusão!");
        }
        
        if (!musicaRepository.existsById(id)) {
            log.error("Música não encontrada no banco de dados para exclusão. ID={}", id);
            throw new RuntimeException("Música não encontrada no banco de dados!");
        }

        musicaRepository.deleteById(id);
        log.info("Música ID={} excluída com sucesso.", id);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public Musica findById(Long id) throws RuntimeException {
        log.info("Buscando música por ID={}", id);

        if (id == null) {
            log.error("ID fornecido para busca é nulo.");
            throw new RuntimeException("ID fornecido é inválido!");
        }
        
        Musica musica = musicaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Música não encontrada para o ID={}", id);
                    return new RuntimeException("Música não encontrada!");
                });
                
        log.info("Música encontrada com sucesso: {}", musica.getTitulo());
        return musica;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public List<Musica> findByTitulo(String titulo) throws RuntimeException {
        log.info("Buscando músicas por título contendo: '{}'", titulo);

        if (titulo == null || titulo.trim().isEmpty()) {
            log.warn("Termo de busca por título vazio.");
            throw new RuntimeException("O termo de busca por título não pode estar vazio!");
        }
        
        List<Musica> musicas = musicaRepository.findByTituloIgnoreCase(titulo);
        log.info("Busca por título finalizada. Resultados encontrados: {}", musicas.size());
        return musicas;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public List<Musica> findByGeneroPrincipal(String generoPrincipal) throws RuntimeException {
        log.info("Buscando músicas pelo gênero principal: '{}'", generoPrincipal);

        if (generoPrincipal == null || generoPrincipal.trim().isEmpty()) {
            log.warn("Termo de busca por gênero vazio.");
            throw new RuntimeException("O termo de busca por gênero não pode estar vazio!");
        }
        
        List<Musica> musicas = musicaRepository.findByGeneroPrincipalContaining(generoPrincipal);
        log.info("Busca por gênero finalizada. Resultados encontrados: {}", musicas.size());
        return musicas;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public double calcularMediaNotas(Long id) throws RuntimeException {
        log.info("Calculando média de notas para a música ID={}", id);

        Musica musica = this.findById(id);
        
        if (musica.getListaAvaliacoes() == null || musica.getListaAvaliacoes().isEmpty()) {
            log.info("Música ID={} não possui avaliações. Média retornada: 0.0", id);
            return 0.0;
        }
        
        double soma = 0;
        for (br.com.ifba.avaliacao.entity.AvaliacaoMusica avaliacao : musica.getListaAvaliacoes()) {
            soma += avaliacao.getNota(); 
        }
        
        double media = soma / musica.getListaAvaliacoes().size();
        log.info("Média calculada para a música ID={}: {}", id, media);
        return media;
    }
    
}
