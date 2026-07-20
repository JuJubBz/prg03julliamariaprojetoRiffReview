/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.avaliacao.controller;

import br.com.ifba.avaliacao.entity.Avaliacao;
import br.com.ifba.usuario.entity.Usuario;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Julia Freitas
 * @param <T>
 */
public interface AvaliacaoIController <T extends Avaliacao> {
    
    T save(T avaliacao) throws RuntimeException;
    
    List<T> findAll() throws RuntimeException;
    
    T update(T avaliacao) throws RuntimeException;
    
    void delete(Long id) throws RuntimeException;
    
    String exibirReview(Long id) throws RuntimeException;
    
    List<T> findByUsuarioNome(String nome) throws RuntimeException;
    
    List<T> findByDataCriacao(LocalDateTime data) throws RuntimeException; 
    
    List<Avaliacao> findByUsuario(Usuario usuario) throws RuntimeException;
    
    List<T> findByBandaId(Long bandaId) throws RuntimeException;

    List<T> findByAlbumId(Long albumId) throws RuntimeException;

    List<T> findByMusicaId(Long musicaId) throws RuntimeException;
    
}
