/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.album.controller;

import br.com.ifba.album.entity.Album;
import br.com.ifba.musica.entity.Musica;
import java.util.List;

/**
 *
 * @author Julia Freitas
 */
public interface AlbumIController {
    
    // Métodos Padrão 
    Album save(Album album) throws RuntimeException;
    List<Album> findAll() throws RuntimeException;
    Album update(Album album) throws RuntimeException;
    void delete(Long id) throws RuntimeException;
    Album findById(Long id) throws RuntimeException;
    
    // Métodos Adicionais de Busca 
    List<Album> findByNome(String nome) throws RuntimeException;
    List<Album> findByAnoLancamento(int anoLancamento) throws RuntimeException;
    
    // Métodos de Negócio Específicos (Do Diagrama UML)
    void adicionarMusica(Long albumId, Musica musica) throws RuntimeException;
    void calcularMediaNotas(Long id) throws RuntimeException;
    void exibirTracklist(Long id) throws RuntimeException;
    
}
