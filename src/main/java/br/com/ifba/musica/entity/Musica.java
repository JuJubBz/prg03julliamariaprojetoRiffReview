/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.musica.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.io.Serializable;
//import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Julia Freitas
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Musica extends PersistenceEntity implements Serializable{
    
    @Column(name = "Titulo", nullable = false)
    private String titulo;
    
    @Column(name = "Genero", nullable = false)
    private String generoPrincipal;
    
    @Column(name = "Duração", nullable = false)
    private String duracao;
    
    // private Album album;
    //private List<AvaliacaoMusica> avaliacao;
    
}
