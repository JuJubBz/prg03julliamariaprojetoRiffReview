/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.banda.repository;

import br.com.ifba.banda.entity.Banda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Julia Freitas
 */

@Repository
public interface BandaRepository extends JpaRepository <Banda, Long> {
 //aqui so vai os especificos de banda
}
