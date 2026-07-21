/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.avaliacao.service;

import br.com.ifba.avaliacao.entity.Avaliacao;
import br.com.ifba.avaliacao.repository.AvaliacaoRepository;
import br.com.ifba.usuario.entity.Usuario;
import java.time.LocalDateTime;
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
public class AvaliacaoService<T extends Avaliacao> implements AvaliacaoIService<T>{
    
    @Autowired
    private AvaliacaoRepository<T> avaliacaoRepository;

    @Override
    public List<T> findAll() throws RuntimeException {
        log.info("Iniciando busca por todas as avaliações.");
        List<T> avaliacoes = avaliacaoRepository.findAll();
        log.info("Busca finalizada. Total de avaliações encontradas: {}", avaliacoes.size());
        return avaliacoes;
    }

    @Override
    public T findById(Long id) throws RuntimeException {
        log.info("Buscando avaliação por ID={}", id);

        if (id == null) {
            log.error("ID fornecido para busca é nulo.");
            throw new RuntimeException("ID inválido para busca!");
        }
        
        T avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Avaliação não encontrada para o ID={}", id);
                    return new RuntimeException("Avaliação não encontrada!");
                });
                
        log.info("Avaliação encontrada com sucesso para o ID={}", id);
        return avaliacao;
    }

    @Override
    public T save(T avaliacao) throws RuntimeException {
        log.info("Iniciando cadastro de avaliação.");

        // Validação 1: O objeto não pode ser nulo
        if (avaliacao == null) {
            log.error("Objeto Avaliacao recebido é nulo.");
            throw new RuntimeException("Dados da avaliação não preenchidos!");
        }
        
        // Validação 2: Se já tem ID, não é um cadastro novo
        if (avaliacao.getId() != 0) {
            log.warn("Tentativa de cadastrar avaliação que já possui ID={}", avaliacao.getId());
            throw new RuntimeException("Avaliação já existente no Banco de dados!");
        }
        
        // Validação de Negócio: Nota deve estar entre 0 e 10
        if (avaliacao.getNota() < 0 || avaliacao.getNota() > 10) {
            log.warn("Tentativa de cadastrar avaliação com nota inválida: {}", avaliacao.getNota());
            throw new RuntimeException("A nota deve ser um valor entre 0 e 10!");
        }

        // Validação de Negócio: O usuário que avalia é obrigatório
        if (avaliacao.getUsuario() == null) {
            log.warn("Tentativa de cadastrar avaliação sem usuário associado.");
            throw new RuntimeException("O usuário associado à avaliação é obrigatório!");
        }

        T salvo = avaliacaoRepository.save(avaliacao);
        log.info("Avaliação cadastrada com sucesso. ID={}", salvo.getId());
        return salvo;
    }

    @Override
    public T update(T avaliacao) throws RuntimeException {
        log.info("Iniciando atualização de avaliação.");

        if (avaliacao == null) {
            log.error("Objeto Avaliacao recebido para atualização é nulo.");
            throw new RuntimeException("Dados da avaliação não preenchidos!");
        }
        
        // Para atualizar, o registro necessita existir no banco antes
        if (avaliacao.getId() == 0 || !avaliacaoRepository.existsById(avaliacao.getId())) {
            log.error("Avaliação não encontrada para atualização. ID={}", avaliacao.getId());
            throw new RuntimeException("Avaliação não encontrada para atualização!");
        }

        T atualizado = avaliacaoRepository.save(avaliacao);
        log.info("Avaliação ID={} atualizada com sucesso.", atualizado.getId());
        return atualizado;
    }

    @Override
    public void delete(Long id) throws RuntimeException {
        log.info("Iniciando exclusão da avaliação com ID={}", id);

        if (id == null) {
            log.error("ID fornecido para exclusão é nulo.");
            throw new RuntimeException("ID inválido para exclusão!");
        }
        
        if (!avaliacaoRepository.existsById(id)) {
            log.error("Avaliação não encontrada no banco de dados para exclusão. ID={}", id);
            throw new RuntimeException("Avaliação não encontrada no banco de dados!");
        }

        avaliacaoRepository.deleteById(id);
        log.info("Avaliação ID={} excluída com sucesso.", id);
    }

    @Override
    public List<T> findByUsuarioNome(String nome) throws RuntimeException {
        log.info("Buscando avaliações por nome do usuário contendo: '{}'", nome);

        if (nome == null || nome.trim().isEmpty()) {
            log.warn("Termo de busca por nome de usuário vazio.");
            throw new RuntimeException("O nome do usuário é obrigatório para a pesquisa!");
        }
        
        List<T> avaliacoes = avaliacaoRepository.findByUsuarioNomeContainingIgnoreCase(nome);
        log.info("Busca por nome de usuário finalizada. Resultados encontrados: {}", avaliacoes.size());
        return avaliacoes;
    }

    @Override
    public List<T> findByDataCriacao(LocalDateTime data) throws RuntimeException {
        log.info("Buscando avaliações pela data de criação: {}", data);

        if (data == null) {
            log.warn("Data inválida fornecida para pesquisa.");
            throw new RuntimeException("Data inválida para pesquisa!");
        }
        
        List<T> avaliacoes = avaliacaoRepository.findByDataCriacao(data);
        log.info("Busca por data finalizada. Resultados encontrados: {}", avaliacoes.size());
        return avaliacoes;
    }
    
    @Override
    public String exibirReview(Long id) throws RuntimeException {
        log.info("Exibindo review da avaliação ID={}", id);

        T avaliacao = findById(id);
    
        if (avaliacao == null) {
            log.error("Avaliação ID={} não encontrada para exibir review.", id);
            throw new RuntimeException("Avaliação não encontrada para exibir!");
        }

        String reviewFormatada = "Usuário: " + avaliacao.getUsuario().getNome() + "\n"
                + "Nota: " + avaliacao.getNota() + "\n"
                + "Comentário: " + avaliacao.getComentario() + "\n"
                + "Data: " + avaliacao.getDataCriacao();

        log.info("Review formatada com sucesso para a avaliação ID={}", id);
        return reviewFormatada;
    }
    
    @Override
    public List<Avaliacao> findByUsuario(Usuario usuario) throws RuntimeException {
        log.info("Buscando avaliações por usuário ID={}", usuario != null ? usuario.getId() : "nulo");

        if (usuario == null) {
            log.warn("Usuário informado para a busca é nulo/inválido.");
            throw new RuntimeException("O usuário informado para a busca de avaliações é inválido!");
        }
        
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByUsuario(usuario);
        log.info("Busca por usuário finalizada. Resultados encontrados: {}", avaliacoes.size());
        return avaliacoes;
    }
    
    @Override
    public List<T> findByBandaId(Long bandaId) throws RuntimeException {
        log.info("Buscando avaliações pelo ID da banda: {}", bandaId);

        if (bandaId == null) {
            log.warn("ID da banda fornecido é nulo.");
            throw new RuntimeException("ID da banda é obrigatório!");
        }
        
        List<T> avaliacoes = avaliacaoRepository.findByBandaId(bandaId);
        log.info("Busca por banda ID={} finalizada. Resultados encontrados: {}", bandaId, avaliacoes.size());
        return avaliacoes;
    }

    @Override
    public List<T> findByAlbumId(Long albumId) throws RuntimeException {
        log.info("Buscando avaliações pelo ID do álbum: {}", albumId);

        if (albumId == null) {
            log.warn("ID do álbum fornecido é nulo.");
            throw new RuntimeException("ID do álbum é obrigatório!");
        }
        
        List<T> avaliacoes = avaliacaoRepository.findByAlbumId(albumId);
        log.info("Busca por álbum ID={} finalizada. Resultados encontrados: {}", albumId, avaliacoes.size());
        return avaliacoes;
    }

    @Override
    public List<T> findByMusicaId(Long musicaId) throws RuntimeException {
        log.info("Buscando avaliações pelo ID da música: {}", musicaId);

        if (musicaId == null) {
            log.warn("ID da música fornecido é nulo.");
            throw new RuntimeException("ID da música é obrigatório!");
        }
        
        List<T> avaliacoes = avaliacaoRepository.findByMusicaId(musicaId);
        log.info("Busca por música ID={} finalizada. Resultados encontrados: {}", musicaId, avaliacoes.size());
        return avaliacoes;
    }
    
}
