/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.banda.service;

import br.com.ifba.banda.entity.Banda;
import br.com.ifba.banda.repository.BandaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Julia Freitas
 */
@Service 
@org.springframework.transaction.annotation.Transactional(readOnly = true)
public class BandaService implements BandaIService{

   @Autowired
    private BandaRepository bandaRepository;

    // 1. REGRA PARA BUSCAR TODOS
    @Override
    public List<Banda> findAll() throws RuntimeException {
        return bandaRepository.findAll();
    }

    // 2. REGRA PARA SALVAR 
    @Override
    public Banda save(Banda banda) throws RuntimeException {
        /// Validação 1: O objeto não pode ser nulo
        if (banda == null) {
            throw new RuntimeException("Dados da banda não preenchidos!");
        }

        // Validação de Negócio Customizada: Evitar nomes vazios
        if (banda.getNome() == null || banda.getNome().trim().isEmpty()) {
            throw new RuntimeException("O nome da banda é obrigatório!");
        }
        
        // Se já tem ID válido (diferente de null e > 0), direciona para a regra de atualização
        if (banda.getId() != 0 && banda.getId() > 0) {
            return this.update(banda);
        }

        return bandaRepository.save(banda);
    }

    // 3. REGRA PARA ATUALIZAR
    @Override
    public Banda update(Banda banda) throws RuntimeException {
        if (banda == null) {
            throw new RuntimeException("Dados da banda não preenchidos!");
        }
        
        // Para atualizar, o registro NECESSITA existir no banco antes
        if (banda.getId() == 0 || !bandaRepository.existsById(banda.getId())) {
        throw new RuntimeException("Banda não encontrada para atualização!");
        }

        return bandaRepository.save(banda);
    }

    // 4. REGRA PARA DELETAR
    @Override
    public void delete(Long id) throws RuntimeException {
        if (id == null) {
            throw new RuntimeException("ID inválido para exclusão!");
        }
        
        if (!bandaRepository.existsById(id)) {
            throw new RuntimeException("Banda não encontrada no banco de dados!");
        }

        bandaRepository.deleteById(id);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public double calcularMediaAvaliacoes(Long bandaId) throws RuntimeException {
        // 1. Busca a banda para garantir que ela existe
        Banda banda = bandaRepository.findById(bandaId)
                .orElseThrow(() -> new RuntimeException("Banda não encontrada para calcular a média!"));

        // 2. Se a lista de avaliações estiver vazia ou nula, a média é 0
        if (banda.getListaAvaliacoes() == null || banda.getListaAvaliacoes().isEmpty()) {
            return 0.0;
        }

        // 3. Percorre a lista somando as notas
        double soma = 0.0;
        for (br.com.ifba.avaliacao.entity.AvaliacaoBanda avaliacao : banda.getListaAvaliacoes()) {
            soma += avaliacao.getNota(); 
        }

        // 4. Retorna a média aritmética simples
        return soma / banda.getListaAvaliacoes().size();
    }
    
    @Override
    public Banda findById(Long id) throws RuntimeException {
    if (id == null) {
        throw new RuntimeException("ID inválido para busca!");
    }

    return bandaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Banda não encontrada!"));
    }
    
}
