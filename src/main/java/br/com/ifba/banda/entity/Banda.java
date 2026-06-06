/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.banda.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Julia Freitas
 */
@Entity
@Getter
@Setter
public class Banda extends PersistenceEntity implements Serializable {
    
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Column(name = "Genero", nullable = false)
    private String generoPrincipal;
    
    @Column(name = "Ano", nullable = false)
    private int anoFormacao;
    
    //private List<AvaliacaoBanda> listaAvaliacoes;
}
