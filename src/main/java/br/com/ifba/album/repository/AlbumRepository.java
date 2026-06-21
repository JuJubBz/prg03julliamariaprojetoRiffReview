/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.album.repository;

import br.com.ifba.album.entity.Album;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Julia Freitas
 */
@Repository
public interface AlbumRepository extends JpaRepository <Album, Long>{
    
    
    
    //List<Album> findByNome(String nome);

    // 2. Busca todos os álbuns lançados em um ano específico
    List<Album> findByAnoLancamento(int anoLancamento);
    
    
    // Se buscar por "Rock", acha "Classic Rock", "Rock Sessions", etc.
    List<Album> findByNomeContaining(String nome);
    
    // Busca pelo nome ignorando maiúsculas e minúsculas (Ex: "album", "ALBUM" dão no mesmo)
    List<Album> findByNomeIgnoreCase(String nome);
    
    // Busca todos os álbuns lançados a partir de um determinado ano (Útil para filtros)
    List<Album> findByAnoLancamentoGreaterThanEqual(int anoLancamento);
    
    
}
