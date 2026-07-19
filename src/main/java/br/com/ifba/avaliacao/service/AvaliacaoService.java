/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.avaliacao.service;

import br.com.ifba.avaliacao.entity.Avaliacao;
import br.com.ifba.avaliacao.repository.AvaliacaoRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Julia Freitas
 */

@Service
public class AvaliacaoService<T extends Avaliacao> implements AvaliacaoIService<T>{
    
    @Autowired
    private AvaliacaoRepository<T> avaliacaoRepository;

    @Override
    public List<T> findAll() throws RuntimeException {
        return avaliacaoRepository.findAll();
    }

    @Override
    public T findById(Long id) throws RuntimeException {
        if (id == null) {
            throw new RuntimeException("ID inválido para busca!");
        }
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada!"));
    }

    @Override
    public T save(T avaliacao) throws RuntimeException {
        // Validação 1: O objeto não pode ser nulo
        if (avaliacao == null) {
            throw new RuntimeException("Dados da avaliação não preenchidos!");
        }
        
        // Validação 2: Se já tem ID, não é um cadastro novo
        if (avaliacao.getId() != 0) {
            throw new RuntimeException("Avaliação já existente no Banco de dados!");
        }
        
        // Validação de Negócio: Nota deve estar entre 0 e 10 (exemplo prático de validação)
        if (avaliacao.getNota() < 0 || avaliacao.getNota() > 10) {
            throw new RuntimeException("A nota deve ser um valor entre 0 e 10!");
        }

        // Validação de Negócio: O usuário que avalia é obrigatório
        if (avaliacao.getUsuario() == null) {
            throw new RuntimeException("O usuário associado à avaliação é obrigatório!");
        }

        return avaliacaoRepository.save(avaliacao);
    }

    
    @Override
    public T update(T avaliacao) throws RuntimeException {
        if (avaliacao == null) {
            throw new RuntimeException("Dados da avaliação não preenchidos!");
        }
        
        // Para atualizar, o registro necessita existir no banco antes
        if (avaliacao.getId() == 0 || !avaliacaoRepository.existsById(avaliacao.getId())) {
            throw new RuntimeException("Avaliação não encontrada para atualização!");
        }

        return avaliacaoRepository.save(avaliacao);
    }

    @Override
    public void delete(Long id) throws RuntimeException {
        if (id == null) {
            throw new RuntimeException("ID inválido para exclusão!");
        }
        
        if (!avaliacaoRepository.existsById(id)) {
            throw new RuntimeException("Avaliação não encontrada no banco de dados!");
        }

        avaliacaoRepository.deleteById(id);
    }

    // 6. nome de usuario
    @Override
    public List<T> findByUsuarioNome(String nome) throws RuntimeException {
    if (nome == null || nome.trim().isEmpty()) {
        throw new RuntimeException("O nome do usuário é obrigatório para a pesquisa!");
    }
    // Repassa a string de texto para o repositório
    return avaliacaoRepository.findByUsuarioNomeContainingIgnoreCase(nome);
}

    @Override
    public List<T> findByDataCriacao(LocalDateTime data) throws RuntimeException {
        if (data == null) {
            throw new RuntimeException("Data inválida para pesquisa!");
        }
        return avaliacaoRepository.findByDataCriacao(data);
    }
    
    @Override
    public String exibirReview(Long id) throws RuntimeException {
    // 1. Busca a avaliação no banco pelo ID
    T avaliacao = findById(id);
    
    if (avaliacao == null) {
        throw new RuntimeException("Avaliação não encontrada para exibir!");
    }

    // Monta o texto usando os atributos que estão na classe pai (Avaliacao)
    String reviewFormatada = "Usuário: " + avaliacao.getUsuario().getNome() + "\n"
            + "Nota: " + avaliacao.getNota() + "\n"
            + "Comentário: " + avaliacao.getComentario() + "\n"
            + "Data: " + avaliacao.getDataCriacao();

    return reviewFormatada;
}
    
}
