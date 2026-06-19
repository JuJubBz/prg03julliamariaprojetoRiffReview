/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.album.entity;

import br.com.ifba.banda.entity.Banda;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.musica.entity.Musica;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;
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
public class Album extends PersistenceEntity implements Serializable{
    
    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Column(name = "ano_lancamento", nullable = false)
    private int anoLancamento;
    
    @ManyToOne
    @JoinColumn(name = "banda_id", nullable = false)
    private Banda banda;
    
    @OneToMany(mappedBy = "album")
    private List<Musica>  musicas;
    
    //private List<AvaliacaoAlbum> listaavaliacoes;
    
}
