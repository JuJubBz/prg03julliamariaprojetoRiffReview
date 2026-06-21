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
        // Validação 1: O objeto não pode ser nulo
        if (banda == null) {
            throw new RuntimeException("Dados da banda não preenchidos!");
        }
        
        // Validação 2: Se já tem ID, não é um cadastro novo (Evita sobrescrever dados)
        if (banda.getId() != 0) {
        throw new RuntimeException("Banda já existente no Banco de dados!");
    }
        
        // Validação de Negócio Customizada: Evitar nomes vazios
        if (banda.getNome() == null || banda.getNome().trim().isEmpty()) {
            throw new RuntimeException("O nome da banda é obrigatório!");
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

    // 5. REGRA DE NEGÓCIO: CALCULAR MÉDIA DE AVALIAÇÕES
    /*@Override
    public double calcularMediaAvaliacoes(Long bandaId) throws RuntimeException {
        // 1. Busca a banda para garantir que ela existe
        Banda banda = bandaRepository.findById(bandaId)
                .orElseThrow(() -> new RuntimeException("Banda não encontrada para calcular a média!"));

        // 2. Se a lista de avaliações estiver vazia ou nula, a média é 0
        if (banda.getListaAvaliacoes() == null || banda.getListaAvaliacoes().isEmpty()) {
            return 0.0;
        }

        // 3. Percorre a lista somando as notas (supondo que a classe avaliacaoBanda tenha o método getNota())
        double soma = 0.0;
        for (var avaliacao : banda.getListaAvaliacoes()) {
            soma += avaliacao.getNota(); // Substitua 'getNota()' pelo nome real do método na sua classe avaliacaoBanda
        }

        // 4. Retorna a média aritmética simples
        return soma / banda.getListaAvaliacoes().size();
    }*/
}
