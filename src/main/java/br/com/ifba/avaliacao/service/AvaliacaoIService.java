/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.avaliacao.service;

import br.com.ifba.avaliacao.entity.Avaliacao;
import br.com.ifba.usuario.entity.Usuario;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Julia Freitas
 */

public interface AvaliacaoIService <T extends Avaliacao>{
    
    //t generico p n precisar ficar fazendo de cada um
    
    T save(T avaliacao) throws RuntimeException;
    List<T> findAll() throws RuntimeException;
    T update(T avaliacao) throws RuntimeException;
    void delete(Long id) throws RuntimeException;
    T findById(Long id) throws RuntimeException;
    
    List<T> findByUsuarioNome(String Nome) throws RuntimeException;
    List<T> findByDataCriacao(LocalDateTime data) throws RuntimeException;
    String exibirReview(Long id) throws RuntimeException;
}
