/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.main.banda.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 *
 * @author Julia Freitas
 */
@Entity
public class Banda {
    
    @Column(name = "nome", nullable = false)
    private String nome;
    private String generoPrincipal;
    private int anoFormacao;
    
}
