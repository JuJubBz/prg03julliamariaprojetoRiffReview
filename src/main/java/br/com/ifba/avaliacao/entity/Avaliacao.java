/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.avaliacao.entity;

import br.com.ifba.infrastructure.entity.PersistenceEntity;
import br.com.ifba.usuario.entity.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@Table(name = "avaliacoes")
@Inheritance(strategy = InheritanceType.JOINED) //Serve pra criar tabelas separadas ligadas pelo id
public abstract class Avaliacao extends PersistenceEntity implements Serializable{
    
    @ManyToOne
    @JoinColumn(name = "UsuarioId", nullable = false)
    private Usuario usuario;
    
    @Column(name = "Nota", nullable = false)
    private double nota;
    
    @Column(name = "Comentario", length = 500)
    private String comentario;
    
    @Column(name = "Data_criação", nullable = false)
    private LocalDateTime dataCriacao; //
    
}
