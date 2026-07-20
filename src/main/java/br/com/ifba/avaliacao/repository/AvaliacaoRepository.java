/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.avaliacao.repository;

import br.com.ifba.avaliacao.entity.Avaliacao;
import br.com.ifba.usuario.entity.Usuario;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Julia Freitas
 */
@Repository
public interface AvaliacaoRepository<T extends Avaliacao> extends JpaRepository<T, Long> {
    
    // busca por parte do nome ignorando maiúsculas/minúsculas
    List<T> findByUsuarioNomeContainingIgnoreCase(String nome);
    
    //listar as avaliações criadas em uma data específica
    List<T> findByDataCriacao(LocalDateTime dataCriacao);
    
    List<Avaliacao> findByUsuario(Usuario usuario);
    
    @Query("SELECT a FROM AvaliacaoBanda a WHERE a.bandaAvaliada.id = :bandaId")
    List<T> findByBandaId(@Param("bandaId") Long bandaId);
    
    @Query("SELECT a FROM AvaliacaoAlbum a WHERE a.albumAvaliado.id = :albumId")
    List<T> findByAlbumId(@Param("albumId") Long albumId);
    
    @Query("SELECT a FROM AvaliacaoMusica a WHERE a.musicaAvaliada.id = :musicaId")
    List<T> findByMusicaId(@Param("musicaId") Long musicaId);
}
