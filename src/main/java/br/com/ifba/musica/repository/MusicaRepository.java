/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.musica.repository;

import br.com.ifba.musica.entity.Musica;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Julia Freitas
 */

@Repository
public interface MusicaRepository extends JpaRepository <Musica, Long>{
    //especificos de musica
    
    // 1. Busca todas as músicas que tenham o título EXATO informado
    //List<Musica> findByTitulo(String titulo);

    // 2. Busca todas as músicas que pertencem a um gênero específico
    //List<Musica> findByGeneroPrincipal(String generoPrincipal);
    
    // Busca aproximada (Usa o "LIKE" do SQL). Se buscar por "Rock", acha "Hard Rock", "Pop Rock", etc.
    List<Musica> findByGeneroPrincipalContaining(String generoPrincipal);
    
    // Busca ignorando maiúsculas e minúsculas
    List<Musica> findByTituloIgnoreCase(String titulo);
    
}
