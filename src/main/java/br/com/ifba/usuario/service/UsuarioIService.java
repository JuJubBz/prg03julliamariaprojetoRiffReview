/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.ifba.usuario.service;

import br.com.ifba.avaliacao.entity.Avaliacao;
import br.com.ifba.usuario.entity.Usuario;
import java.util.List;

/**
 *
 * @author Julia Freitas
 */
public interface UsuarioIService {
    
    // Métodos CRUD 
    Usuario save(Usuario usuario) throws RuntimeException;
    List<Usuario> findAll() throws RuntimeException;
    Usuario update(Usuario usuario) throws RuntimeException;
    void delete(Long id) throws RuntimeException;
    Usuario findById(Long id) throws RuntimeException;
    
    // Método de Busca
    Usuario findByEmail(String email) throws RuntimeException;
    
    // Métodos Específicos 
    void criarLista(Long id) throws RuntimeException;
    
    
    
    /*void avaliar(Long id, Object avaliacao) throws RuntimeException;
    void exibirPerfil(Long id) throws RuntimeException;*/
    
}
