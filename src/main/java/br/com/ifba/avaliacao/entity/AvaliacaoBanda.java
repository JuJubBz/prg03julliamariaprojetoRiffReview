/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.avaliacao.entity;

import br.com.ifba.banda.entity.Banda;
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
@Table(name = "Avaliacoes_banda")
@PrimaryKeyJoinColumn(name = "avaliacao_id") // Une as tabelas pelo id
@NoArgsConstructor
public class AvaliacaoBanda extends Avaliacao{
    
    @ManyToOne
    @JoinColumn(name = "Banda_id", nullable = false)
    private Banda bandaAvaliada;
    
}
