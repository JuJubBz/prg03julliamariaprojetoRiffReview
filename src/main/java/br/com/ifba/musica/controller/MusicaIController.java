/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */


package br.com.ifba.musica.controller;

import br.com.ifba.musica.entity.Musica;
import java.util.List;

/**
 * @author Julia Freitas
 */

public interface MusicaIController {
    
    // Métodos Padrão 
    Musica save(Musica musica) throws RuntimeException;
    List<Musica> findAll() throws RuntimeException;
    Musica update(Musica musica) throws RuntimeException;
    void delete(Long id) throws RuntimeException;
    Musica findById(Long id) throws RuntimeException;
    
    // Métodos Adicionais de Busca 
    List<Musica> findByTitulo(String titulo) throws RuntimeException;
    List<Musica> findByGeneroPrincipal(String generoPrincipal) throws RuntimeException;
    
    // Métodos de Negócio Específicos 
    double calcularMediaNotas(Long id) throws RuntimeException; 
    //Musica exibirDetalhes(Long id) throws RuntimeException; --> Método Rendundante.    
}

