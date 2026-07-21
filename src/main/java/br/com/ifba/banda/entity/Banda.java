/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.banda.entity;

import br.com.ifba.album.entity.Album;
import br.com.ifba.avaliacao.entity.AvaliacaoBanda;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Julia Freitas
 */
@Entity
@Getter
@Setter
//@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Banda extends PersistenceEntity implements Serializable {
    
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Column(name = "Genero", nullable = false)
    private String generoPrincipal;
    
    @Column(name = "Ano", nullable = false)
    private int anoFormacao;
    
    @ToString.Exclude
    @OneToMany(mappedBy = "banda", fetch = FetchType.EAGER)
    private List<Album> albuns;
    
    @ToString.Exclude
    @OneToMany(mappedBy = "bandaAvaliada", cascade = jakarta.persistence.CascadeType.ALL, fetch = jakarta.persistence.FetchType.EAGER)
    private List<AvaliacaoBanda> listaAvaliacoes;
}
