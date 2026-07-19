/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.musica.controller;

import br.com.ifba.musica.entity.Musica;
import br.com.ifba.musica.service.MusicaIService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Julia Freitas
 */

@Controller
public class MusicaController implements MusicaIController{
    
    @Autowired
    private MusicaIService musicaService;

    @Override
    public Musica save(Musica musica) throws RuntimeException {
       return musicaService.save(musica);
    }

    @Override
    public List<Musica> findAll() throws RuntimeException {
        return musicaService.findAll();
    }

    @Override
    public Musica update(Musica musica) throws RuntimeException {
        return musicaService.update(musica);
    }

    @Override
    public void delete(Long id) throws RuntimeException {
        musicaService.delete(id);
    }

    @Override
    public Musica findById(Long id) throws RuntimeException {
        return musicaService.findById(id);
    }

    @Override
    public List<Musica> findByTitulo(String titulo) throws RuntimeException {
        return musicaService.findByTitulo(titulo);
    }

    @Override
    public List<Musica> findByGeneroPrincipal(String generoPrincipal) throws RuntimeException {
        return musicaService.findByGeneroPrincipal(generoPrincipal);
    }

    @Override
    public double calcularMediaNotas(Long id) throws RuntimeException {
        return musicaService.calcularMediaNotas(id);
    }

    /*@Override
    public Musica exibirDetalhes(Long id) throws RuntimeException {
        return musicaService.exibirDetalhes(id);
    }*/
    
}
    

