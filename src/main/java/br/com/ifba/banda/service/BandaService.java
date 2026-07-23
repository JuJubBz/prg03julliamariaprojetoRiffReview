/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.banda.service;

import br.com.ifba.banda.entity.Banda;
import br.com.ifba.banda.repository.BandaRepository;
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
@org.springframework.transaction.annotation.Transactional
public class BandaService implements BandaIService{

   @Autowired
    private BandaRepository bandaRepository;

    // 1. REGRA PARA BUSCAR TODOS
    @Override
    public List<Banda> findAll() throws RuntimeException {
        log.info("Iniciando busca por todas as bandas.");
        List<Banda> bandas = bandaRepository.findAll();
        log.info("Busca finalizada. Total de bandas encontradas: {}", bandas.size());
        return bandas;
    }

    // 2. REGRA PARA SALVAR 
    @Override
    public Banda save(Banda banda) throws RuntimeException {
        log.info("Iniciando cadastro de banda.");

        /// Validação 1: O objeto não pode ser nulo
        if (banda == null) {
            log.error("Objeto Banda recebido é nulo.");
            throw new RuntimeException("Dados da banda não preenchidos!");
        }

        // Validação de Negócio Customizada: Evitar nomes vazios
        if (banda.getNome() == null || banda.getNome().trim().isEmpty()) {
            log.warn("Tentativa de cadastrar banda sem nome.");
            throw new RuntimeException("O nome da banda é obrigatório!");
        }
        
        // Se já tem ID válido (diferente de null e > 0), direciona para a regra de atualização
        if (banda.getId() != 0 && banda.getId() > 0) {
            log.warn("Tentativa de cadastrar banda que já possui ID={}. Direcionando para atualização.", banda.getId());
            return this.update(banda);
        }

        Banda salva = bandaRepository.save(banda);
        log.info("Banda '{}' cadastrada com sucesso. ID={}", salva.getNome(), salva.getId());
        return salva;
    }

    // 3. REGRA PARA ATUALIZAR
    @Override
    public Banda update(Banda banda) throws RuntimeException {
        log.info("Iniciando atualização de banda.");

        if (banda == null) {
            log.error("Objeto Banda recebido para atualização é nulo.");
            throw new RuntimeException("Dados da banda não preenchidos!");
        }
        
        // Para atualizar, o registro NECESSITA existir no banco antes
        if (banda.getId() == 0 || !bandaRepository.existsById(banda.getId())) {
            log.error("Banda não encontrada para atualização. ID={}", banda.getId());
            throw new RuntimeException("Banda não encontrada para atualização!");
        }

        if (banda.getNome() == null || banda.getNome().trim().isEmpty()) {
            log.warn("Tentativa de atualizar banda deixando o nome vazio.");
            throw new RuntimeException("O nome da banda não pode ficar vazio!");
        }

        Banda atualizada = bandaRepository.save(banda);
        log.info("Banda ID={} atualizada com sucesso.", atualizada.getId());
        return atualizada;
    }

    // 4. REGRA PARA DELETAR
    @Override
    public void delete(Long id) throws RuntimeException {
        log.info("Iniciando exclusão da banda com ID={}", id);

        if (id == null) {
            log.error("ID fornecido para exclusão é nulo.");
            throw new RuntimeException("ID inválido para exclusão!");
        }
        
        if (!bandaRepository.existsById(id)) {
            log.error("Banda não encontrada no banco de dados para exclusão. ID={}", id);
            throw new RuntimeException("Banda não encontrada no banco de dados!");
        }

        bandaRepository.deleteById(id);
        log.info("Banda ID={} excluída com sucesso.", id);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public double calcularMediaAvaliacoes(Long bandaId) throws RuntimeException {
        log.info("Calculando média de avaliações para a banda ID={}", bandaId);

        // 1. Busca a banda para garantir que ela existe
        Banda banda = bandaRepository.findById(bandaId)
                .orElseThrow(() -> {
                    log.error("Banda ID={} não encontrada para calcular a média.", bandaId);
                    return new RuntimeException("Banda não encontrada para calcular a média!");
                });

        // 2. Se a lista de avaliações estiver vazia ou nula, a média é 0
        if (banda.getListaAvaliacoes() == null || banda.getListaAvaliacoes().isEmpty()) {
            log.info("Banda ID={} não possui avaliações. Média retornada: 0.0", bandaId);
            return 0.0;
        }

        // 3. Percorre a lista somando as notas
        double soma = 0.0;
        for (br.com.ifba.avaliacao.entity.AvaliacaoBanda avaliacao : banda.getListaAvaliacoes()) {
            soma += avaliacao.getNota(); 
        }

        // 4. Retorna a média aritmética simples
        double media = soma / banda.getListaAvaliacoes().size();
        log.info("Média calculada para a banda ID={}: {}", bandaId, media);
        return media;
    }
    
    @Override
    @org.springframework.transaction.annotation.Transactional
    public Banda findById(Long id) throws RuntimeException {
        log.info("Buscando banda por ID={}", id);

        if (id == null) {
            log.error("ID fornecido para busca é nulo.");
            throw new RuntimeException("ID inválido para busca!");
        }

        Banda banda = bandaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Banda não encontrada para o ID={}", id);
                    return new RuntimeException("Banda não encontrada!");
                });
                
        log.info("Banda encontrada com sucesso: {}", banda.getNome());
        return banda;
    }
    
}
