/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.banda.controller;

import br.com.ifba.banda.entity.Banda;
import java.util.List;

/**
 *
 * @author Julia Freitas
 */
public interface BandaIController  {
    Banda save(Banda banda) throws RuntimeException;
    List<Banda> findAll() throws RuntimeException;
    Banda update(Banda banda) throws RuntimeException;
    void delete(Long id) throws RuntimeException;
    //void adicionarAlbum(Long bandaId, Object album) throws RuntimeException; vou add dps 
    double calcularMediaAvaliacoes(Long bandaId) throws RuntimeException;
}
