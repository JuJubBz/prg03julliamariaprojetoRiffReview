/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.ifba.musica.service;

import br.com.ifba.musica.entity.Musica;
import br.com.ifba.musica.repository.MusicaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Julia Freitas
 */

@Service
public class MusicaService implements MusicaIService{
    
    @Autowired
    private MusicaRepository musicaRepository;

    @Override
    public List<Musica> findAll() throws RuntimeException {
        return musicaRepository.findAll();
    }

    @Override
    public Musica save(Musica musica) throws RuntimeException {
        // Validação 1: Objeto não pode ser nulo
        if (musica == null) {
            throw new RuntimeException("Dados da música não preenchidos!");
        }
        
        // Validação 2: Se já tem ID no banco de dados (Assumindo id herdado da classe mãe)
        if (musica.getId() != 0 ) {
            throw new RuntimeException("Música já existente no Banco de dados!");
        } else {
        }
        
        // Validação 3: Validação de campos obrigatórios
        if (musica.getTitulo() == null || musica.getTitulo().trim().isEmpty()) {
            throw new RuntimeException("O título da música é obrigatório!");
        }
        
        if (musica.getGeneroPrincipal() == null || musica.getGeneroPrincipal().trim().isEmpty()) {
            throw new RuntimeException("O gênero principal da música é obrigatório!");
        }

        return musicaRepository.save(musica);
    }

    @Override
    public Musica update(Musica musica) throws RuntimeException {
        if (musica == null) {
            throw new RuntimeException("Dados da música não preenchidos!");
        }
        
        // Validação para garantir que o registro já existe antes de tentar atualizar
        if (musica.getId() == 0 || !musicaRepository.existsById(musica.getId())) {
            throw new RuntimeException("Música não encontrada para atualização!");
        }
        
        if (musica.getTitulo() == null || musica.getTitulo().trim().isEmpty()) {
            throw new RuntimeException("O título da música não pode ficar vazio!");
        }

        return musicaRepository.save(musica);
    }

    @Override
    public void delete(Long id) throws RuntimeException {
        if (id == null) {
            throw new RuntimeException("ID inválido para exclusão!");
        }
        
        if (!musicaRepository.existsById(id)) {
            throw new RuntimeException("Música não encontrada no banco de dados!");
        }

        musicaRepository.deleteById(id);
    }

    @Override
    public Musica findById(Long id) throws RuntimeException {
        if (id == null) {
            throw new RuntimeException("ID fornecido é inválido!");
        }
        return musicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Música não encontrada!"));
    }

    @Override
    public List<Musica> findByTitulo(String titulo) throws RuntimeException {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new RuntimeException("O termo de busca por título não pode estar vazio!");
        }
        return musicaRepository.findByTituloIgnoreCase(titulo);
    }

    @Override
    public List<Musica> findByGeneroPrincipal(String generoPrincipal) throws RuntimeException {
        if (generoPrincipal == null || generoPrincipal.trim().isEmpty()) {
            throw new RuntimeException("O termo de busca por gênero não pode estar vazio!");
        }
        return musicaRepository.findByGeneroPrincipalContaining(generoPrincipal);
    }

    // --- MÉTODOS DE NEGÓCIO ---

   /* @Override
    public void calcularMediaNotas(Long id) throws RuntimeException {
        Musica musica = this.findById(id);
        
        // TODO: Quando você implementar a classe 'AvaliacaoMusica', coloque a lógica de média aqui,
        // parecido com o que você rascunhou na classe Banda!
        System.out.println("Calculando média de notas para a música: " + musica.getTitulo());
    }

    @Override
    public void exibirDetalhes(Long id) throws RuntimeException {
        Musica musica = this.findById(id);
        
        // Exemplo simples de implementação do método de visualização do UML
        System.out.println("=== Detalhes da Música ===");
        System.out.println("Título: " + musica.getTitulo());
        System.out.println("Gênero: " + musica.getGeneroPrincipal());
        System.out.println("Duração: " + musica.getDuracao());
        if (musica.getAlbum() != null) {
            System.out.println("Álbum: " + musica.getAlbum().getNome());
        }
    }
}*/
    
}
