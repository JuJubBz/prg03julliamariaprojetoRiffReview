/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.album.controller;

import br.com.ifba.album.entity.Album;
import br.com.ifba.album.service.AlbumIService;
import br.com.ifba.musica.entity.Musica;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Julia Freitas
 */

@Controller
public class AlbumController implements AlbumIController{
    
    @Autowired
    private AlbumIService albumService;

    @Override
    public Album save(Album album) throws RuntimeException {
        return albumService.save(album);
    }

    @Override
    public List<Album> findAll() throws RuntimeException {
        return albumService.findAll();
    }

    @Override
    public Album update(Album album) throws RuntimeException {
        return albumService.update(album);
    }

    @Override
    public void delete(Long id) throws RuntimeException {
        albumService.delete(id);
    }

    @Override
    public Album findById(Long id) throws RuntimeException {
        return albumService.findById(id);
    }

    @Override
    public List<Album> findByNome(String nome) throws RuntimeException {
        return albumService.findByNome(nome);
    }

    @Override
    public List<Album> findByAnoLancamento(int anoLancamento) throws RuntimeException {
        return albumService.findByAnoLancamento(anoLancamento);
    }

    @Override
    public void adicionarMusica(Long albumId, Musica musica) throws RuntimeException {
        albumService.adicionarMusica(albumId, musica);
    }

    @Override
    public double calcularMediaNotas(Long id) throws RuntimeException {
        return albumService.calcularMediaNotas(id);
    }

    @Override
    public List<Musica> exibirTracklist(Long id) throws RuntimeException {
        return albumService.exibirTracklist(id);
    }
    
}
