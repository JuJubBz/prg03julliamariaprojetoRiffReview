/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.avaliacao.controller;

import br.com.ifba.avaliacao.entity.Avaliacao;
import br.com.ifba.avaliacao.service.AvaliacaoIService;
import br.com.ifba.usuario.entity.Usuario;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Julia Freitas
 */
@Controller
public class AvaliacaoController <T extends Avaliacao> implements AvaliacaoIController<T>{
    
    @Autowired
    private AvaliacaoIService<T> avaliacaoService;

    @Override
    public List<T> findAll() throws RuntimeException {
        return avaliacaoService.findAll();
    }

    @Override
    public T save(T avaliacao) throws RuntimeException {
        // Aqui vai a nota, o comentário, o usuário e tudo mais
        return avaliacaoService.save(avaliacao);
    }

    @Override
    public T update(T avaliacao) throws RuntimeException {
        return avaliacaoService.update(avaliacao);
    }

    @Override
    public void delete(Long id) throws RuntimeException {
        avaliacaoService.delete(id);
    }

    @Override
    public String exibirReview(Long id) throws RuntimeException {
    // service para a logica do txt
    return avaliacaoService.exibirReview(id);
    }
    
    @Override
    public List<T> findByUsuarioNome(String nome) throws RuntimeException {
        // direto para a regra de negócio do Service
        return avaliacaoService.findByUsuarioNome(nome);
    }

    @Override
    public List<T> findByDataCriacao(LocalDateTime data) throws RuntimeException {
        return avaliacaoService.findByDataCriacao(data);
    }
    
    @Override
    public List<Avaliacao> findByUsuario(Usuario usuario) throws RuntimeException {
        return avaliacaoService.findByUsuario(usuario);
    }
    
    @Override
    public List<T> findByBandaId(Long bandaId) throws RuntimeException {
        return avaliacaoService.findByBandaId(bandaId);
    }

    @Override
    public List<T> findByAlbumId(Long albumId) throws RuntimeException {
        return avaliacaoService.findByAlbumId(albumId);
    }

    @Override
    public List<T> findByMusicaId(Long musicaId) throws RuntimeException {
        return avaliacaoService.findByMusicaId(musicaId);
    }
    
}
