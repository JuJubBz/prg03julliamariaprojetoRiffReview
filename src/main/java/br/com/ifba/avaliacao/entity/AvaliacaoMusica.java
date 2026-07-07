/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.avaliacao.entity;

import br.com.ifba.musica.entity.Musica;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Julia Freitas
 */

@Getter
@Setter
@Entity
@Table(name = "avaliacoes_musica")
@PrimaryKeyJoinColumn(name = "avaliacao_id")
@NoArgsConstructor
public class AvaliacaoMusica extends Avaliacao{
    
    @ManyToOne
    @JoinColumn(name = "musica_id", nullable = false)
    private Musica musicaAvaliada;
    
}
