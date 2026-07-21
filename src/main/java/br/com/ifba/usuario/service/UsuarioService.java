/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.usuario.service;

import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.repository.UsuarioRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Julia Freitas
 */
@Slf4j
@Service
public class UsuarioService implements UsuarioIService{
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() throws RuntimeException {
        log.info("Iniciando busca por todos os usuários.");
        List<Usuario> usuarios = usuarioRepository.findAll();
        log.info("Busca finalizada. Total de usuários encontrados: {}", usuarios.size());
        return usuarios;
    }

    @Override
    public Usuario save(Usuario usuario) throws RuntimeException {
        log.info("Iniciando cadastro de usuário.");

        // Validação 1: O objeto não pode ser nulo
        if (usuario == null) {
            log.error("Objeto Usuario recebido é nulo.");
            throw new RuntimeException("Dados do usuário não preenchidos!");
        }
        
        // Validação 2: Se o id primitivo long for diferente de 0, já existe no banco
        if (usuario.getId() != 0) {
            log.warn("Tentativa de cadastrar usuário que já possui ID={}", usuario.getId());
            throw new RuntimeException("Usuário já existente no Banco de dados!");
        }
        
        // Validação de Campos Obrigatórios do UML
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            log.warn("Tentativa de cadastrar usuário sem nome.");
            throw new RuntimeException("O nome do usuário é obrigatório!");
        }
        
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            log.warn("Tentativa de cadastrar usuário sem e-mail.");
            throw new RuntimeException("O e-mail do usuário é obrigatório!");
        }
        
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            log.warn("Tentativa de cadastrar usuário sem senha.");
            throw new RuntimeException("A senha do usuário é obrigatória!");
        }

        Usuario salvo = usuarioRepository.save(usuario);
        log.info("Usuário '{}' cadastrado com sucesso. ID={}", salvo.getNome(), salvo.getId());
        return salvo;
    }

    @Override
    public Usuario update(Usuario usuario) throws RuntimeException {
        log.info("Iniciando atualização de usuário.");

        if (usuario == null) {
            log.error("Objeto Usuario recebido para atualização é nulo.");
            throw new RuntimeException("Dados do usuário não preenchidos!");
        }
        
        // Para atualizar, o ID primitivo necessita estar preenchido e constar no banco
        if (usuario.getId() == 0 || !usuarioRepository.existsById(usuario.getId())) {
            log.error("Usuário não encontrado para atualização. ID={}", usuario.getId());
            throw new RuntimeException("Usuário não encontrado para atualização!");
        }
        
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            log.warn("Tentativa de atualizar usuário deixando o nome vazio.");
            throw new RuntimeException("O nome do usuário não pode ficar vazio!");
        }

        Usuario atualizado = usuarioRepository.save(usuario);
        log.info("Usuário ID={} atualizado com sucesso.", atualizado.getId());
        return atualizado;
    }

    @Override
    public void delete(Long id) throws RuntimeException {
        log.info("Iniciando exclusão do usuário com ID={}", id);

        if (id == null) {
            log.error("ID fornecido para exclusão é nulo.");
            throw new RuntimeException("ID inválido para exclusão!");
        }
        
        if (!usuarioRepository.existsById(id)) {
            log.error("Usuário não encontrado no banco de dados para exclusão. ID={}", id);
            throw new RuntimeException("Usuário não encontrado no banco de dados!");
        }

        usuarioRepository.deleteById(id);
        log.info("Usuário ID={} excluído com sucesso.", id);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public Usuario findById(Long id) throws RuntimeException {
        log.info("Buscando usuário por ID={}", id);

        if (id == null) {
            log.error("ID fornecido para busca é nulo.");
            throw new RuntimeException("ID fornecido é inválido!");
        }
        
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Usuário não encontrado para o ID={}", id);
                    return new RuntimeException("Usuário não encontrado!");
                });
                
        log.info("Usuário encontrado com sucesso: {}", usuario.getNome());
        return usuario;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public Usuario findByEmail(String email) throws RuntimeException {
        log.info("Buscando usuário por e-mail: '{}'", email);

        if (email == null || email.trim().isEmpty()) {
            log.warn("E-mail de busca vazio.");
            throw new RuntimeException("O e-mail de busca não pode estar vazio!");
        }
        
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Nenhum usuário localizado com o e-mail informado: '{}'", email);
                    return new RuntimeException("Nenhum usuário localizado com o e-mail informado!");
                });
                
        log.info("Usuário encontrado pelo e-mail com sucesso: {}", usuario.getNome());
        return usuario;
    }

    // --- MÉTODOS DE DIRETRIZ E REGRAS DE NEGÓCIO DO UML ---

    @Override
    public void criarLista(Long id) throws RuntimeException {
        log.info("Criando nova lista musical para o usuário ID={}", id);

        Usuario usuario = this.findById(id);
        
        // Simulação do escopo da regra de negócio para criar listas de reprodução/favoritos
        System.out.println("Criando nova lista musical para o usuário: " + usuario.getNome());
        log.info("Lista musical criada com sucesso para o usuário: {}", usuario.getNome());
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
