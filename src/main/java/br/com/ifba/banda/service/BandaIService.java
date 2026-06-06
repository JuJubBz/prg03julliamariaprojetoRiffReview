/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.banda.service;

import br.com.ifba.banda.entity.Banda;
import java.util.List;

/**
 *
 * @author Julia Freitas
 */
public interface BandaIService {
    Banda save(Banda banda) throws RuntimeException;
    List<Banda> findAll() throws RuntimeException;
    Banda update(Banda banda) throws RuntimeException;
    void delete(Long id) throws RuntimeException;
    //double calcularMediaAvaliacoes(Long bandaId) throws RuntimeException;
}
