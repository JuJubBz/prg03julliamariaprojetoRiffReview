/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.avaliacao.controller;

import br.com.ifba.avaliacao.entity.Avaliacao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Julia Freitas
 */
public class AvaliacaoController <T extends Avaliacao> implements AvaliacaoIController<T>{
    
    @Autowired
    private AvaliacaoIService<T> avaliacaoService;

    @Override
    public List<T> findAll() throws RuntimeException {
        return avaliacaoService.findAll();
    }

    @Override
    public T save(T avaliacao) throws RuntimeException {
        // Aqui vai a nota, o comentário, o usuário e tudo mais que você setou na View!
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
    public void exibirReview(Long id) throws RuntimeException {
        T avaliacao = avaliacaoService.findById(id); 
        if (avaliacao != null) {
            avaliacao.exibirReview(); 
        }
    }
    
}
