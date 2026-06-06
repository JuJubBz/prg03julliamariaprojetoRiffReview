/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.banda.controller;

import br.com.ifba.banda.entity.Banda;
import br.com.ifba.banda.service.BandaIService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Julia Freitas
 */
@Controller
public class BandaController implements BandaIController{

    @Autowired
    private BandaIService bandaService;

    @Override
    public List<Banda> findAll() throws RuntimeException {
        return bandaService.findAll();
    }

    @Override
    public Banda save(Banda banda) throws RuntimeException {
        return bandaService.save(banda);
    }

    @Override
    public Banda update(Banda banda) throws RuntimeException {
        return bandaService.update(banda);
    }

    @Override
    public void delete(Long id) throws RuntimeException {
        bandaService.delete(id);
    }
    
    /*@Override
    public double calcularMediaAvaliacoes(Long bandaId) throws RuntimeException {
        return bandaService.calcularMediaAvaliacoes(bandaId);
    }*/
    
}
