/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.service;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.repository.UsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Julia Freitas
 */
@Service
public class UsuarioService implements UsuarioIService{
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() throws RuntimeException {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario save(Usuario usuario) throws RuntimeException {
        // Validação 1: O objeto não pode ser nulo
        if (usuario == null) {
            throw new RuntimeException("Dados do usuário não preenchidos!");
        }
        
        // Validação 2: Se o id primitivo long for diferente de 0, já existe no banco
        if (usuario.getId() != 0) {
            throw new RuntimeException("Usuário já existente no Banco de dados!");
        }
        
        // Validação de Campos Obrigatórios do UML
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new RuntimeException("O nome do usuário é obrigatório!");
        }
        
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new RuntimeException("O e-mail do usuário é obrigatório!");
        }
        
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            throw new RuntimeException("A senha do usuário é obrigatória!");
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) throws RuntimeException {
        if (usuario == null) {
            throw new RuntimeException("Dados do usuário não preenchidos!");
        }
        
        // Para atualizar, o ID primitivo necessita estar preenchido e constar no banco
        if (usuario.getId() == 0 || !usuarioRepository.existsById(usuario.getId())) {
            throw new RuntimeException("Usuário não encontrado para atualização!");
        }
        
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new RuntimeException("O nome do usuário não pode ficar vazio!");
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public void delete(Long id) throws RuntimeException {
        if (id == null) {
            throw new RuntimeException("ID inválido para exclusão!");
        }
        
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado no banco de dados!");
        }

        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario findById(Long id) throws RuntimeException {
        if (id == null) {
            throw new RuntimeException("ID fornecido é inválido!");
        }
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    @Override
    public Usuario findByEmail(String email) throws RuntimeException {
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("O e-mail de busca não pode estar vazio!");
        }
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Nenhum usuário localizado com o e-mail informado!"));
    }

    // --- MÉTODOS DE DIRETRIZ E REGRAS DE NEGÓCIO DO UML ---

    @Override
    public void criarLista(Long id) throws RuntimeException {
        Usuario usuario = this.findById(id);
        
        // Simulação do escopo da regra de negócio para criar listas de reprodução/favoritos
        System.out.println("Criando nova lista musical para o usuário: " + usuario.getNome());
    }

    /*@Override
    public void avaliar(Long id, Object avaliacao) throws RuntimeException {
        Usuario usuario = this.findById(id);
        
        if (avaliacao == null) {
            throw new RuntimeException("A avaliação enviada está nula!");
        }
        
        // Estrutura engatilhada para quando herdar e instanciar Avaliacao
        System.out.println("O usuário " + usuario.getNome() + " realizou uma nova avaliação no sistema.");
    }

    @Override
    public void exibirPerfil(Long id) throws RuntimeException {
        Usuario usuario = this.findById(id);
        
        System.out.println("=== Perfil do Usuário ===");
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("E-mail: " + usuario.getEmail());
        System.out.println("=========================");
    }*/
    
}
