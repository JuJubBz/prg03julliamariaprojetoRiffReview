/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.avaliacao.controller;

import br.com.ifba.avaliacao.entity.Avaliacao;
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
    
    void exibirReview(Long id) throws RuntimeException;
    
}
