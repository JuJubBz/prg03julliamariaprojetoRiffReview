/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.listamusical.entity;

import br.com.ifba.album.entity.Album;
import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.musica.entity.Musica;
import br.com.ifba.usuario.entity.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//import java.util.List;
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
@Table(name = "listas_musicais")
@NoArgsConstructor
public class ListaMusical extends PersistenceEntity implements Serializable{
    
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuarioCriador;

    //relacionamento Muitos-para-Muitos com Álbum
    @ManyToMany
    @JoinTable(
        name = "lista_musical_albuns",
        joinColumns = @JoinColumn(name = "lista_musical_id"),
        inverseJoinColumns = @JoinColumn(name = "album_id")
    )
    private List<Album> albunsIncluidos = new ArrayList<>();

    // Relacionamento Muitos-para-Muitos com Música
    @ManyToMany
    @JoinTable(
        name = "lista_musical_musicas",
        joinColumns = @JoinColumn(name = "lista_musical_id"),
        inverseJoinColumns = @JoinColumn(name = "musica_id")
    )
    private List<Musica> musicasIncluidas = new ArrayList<>();
       
}
