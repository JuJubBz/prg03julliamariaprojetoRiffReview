/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.controller;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.service.UsuarioIService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Julia Freitas
 */
@Controller
public class UsuarioController implements UsuarioIController{
    
    @Autowired
    private UsuarioIService usuarioService;

    @Override
    public Usuario save(Usuario usuario) throws RuntimeException {
        return usuarioService.save(usuario);
    }

    @Override
    public List<Usuario> findAll() throws RuntimeException {
        return usuarioService.findAll();
    }

    @Override
    public Usuario update(Usuario usuario) throws RuntimeException {
        return usuarioService.update(usuario);
    }

    @Override
    public void delete(Long id) throws RuntimeException {
        usuarioService.delete(id);
    }

    @Override
    public Usuario findById(Long id) throws RuntimeException {
        return usuarioService.findById(id);
    }

    @Override
    public Usuario findByEmail(String email) throws RuntimeException {
        return usuarioService.findByEmail(email);
    }

    @Override
    public void criarLista(Long id) throws RuntimeException {
        usuarioService.criarLista(id);
    }

   /* @Override
    public void avaliar(Long id, Object avaliacao) throws RuntimeException {
        usuarioService.avaliar(id, avaliacao);
    }

    @Override
    public void exibirPerfil(Long id) throws RuntimeException {
        usuarioService.exibirPerfil(id);
    }
    */
    
}
