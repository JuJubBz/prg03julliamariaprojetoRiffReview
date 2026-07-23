/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.ifba.avaliacao.view;

import br.com.ifba.album.controller.AlbumIController;
import br.com.ifba.album.entity.Album;
import br.com.ifba.album.view.AlbumOptions;
import br.com.ifba.album.view.AlbumSave;
import br.com.ifba.album.view.AlbumView;
import br.com.ifba.avaliacao.controller.AvaliacaoIController;
import br.com.ifba.banda.controller.BandaIController;
import br.com.ifba.banda.entity.Banda;
import br.com.ifba.banda.view.BandaOptions;
import br.com.ifba.banda.view.BandaSave;
import br.com.ifba.banda.view.BandaView;
import br.com.ifba.musica.controller.MusicaIController;
import br.com.ifba.musica.entity.Musica;
import br.com.ifba.musica.view.MusicaOptions;
import br.com.ifba.musica.view.MusicaSave;
import br.com.ifba.musica.view.MusicaView;
import br.com.ifba.usuario.entity.Usuario;
import java.awt.event.ActionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Julia Freitas
 */

@Component
public class AvaliacaoViewAdm extends javax.swing.JFrame {
    
    private final AvaliacaoSave avaliacaoSave;
    
    private final BandaSave bandaSave;
    private final BandaView bandaView;
    private final BandaOptions bandaOptions; // Adicionado
    
    private final AlbumSave albumSave;
    private final AlbumView albumView;
    private final AlbumOptions albumOptions; // Adicionado
    
    private final MusicaSave musicaSave;
    private final MusicaView musicaView;
    private final MusicaOptions musicaOptions; // Adicionado

    private final br.com.ifba.banda.controller.BandaIController bandaController;
    private final br.com.ifba.album.controller.AlbumIController albumController;
    private final br.com.ifba.musica.controller.MusicaIController musicaController;
    
    private final AvaliacaoViewSearch avaliacaoViewSearch;
    
    private final AvaliacaoIController avaliacaoController;
    
    // Atributos de controle de estado
    private String contextoAtual = ""; 
    private Usuario usuarioLogado = null;
    
    
    @Autowired
    public AvaliacaoViewAdm(AvaliacaoSave avaliacaoSave,
            BandaSave bandaSave, BandaView bandaView, BandaOptions bandaOptions,
            AlbumSave albumSave, AlbumView albumView, AlbumOptions albumOptions,
            MusicaSave musicaSave, MusicaView musicaView, MusicaOptions musicaOptions,
            BandaIController bandaController,
            AlbumIController albumController,
            MusicaIController musicaController,
            AvaliacaoViewSearch avaliacaoViewSearch,
            AvaliacaoIController avaliacaoController) {
        
        this.avaliacaoSave = avaliacaoSave;
        this.bandaSave = bandaSave;
        this.bandaView = bandaView;
        this.bandaOptions = bandaOptions; 
        this.albumSave = albumSave;
        this.albumView = albumView;
        this.albumOptions = albumOptions; 
        this.musicaSave = musicaSave;
        this.musicaView = musicaView;
        this.musicaOptions = musicaOptions; 
        this.bandaController = bandaController;
        this.albumController = albumController;
        this.musicaController = musicaController;
        this.avaliacaoViewSearch = avaliacaoViewSearch;
        this.avaliacaoController = avaliacaoController;
        
        initComponents(); // O NetBeans gera isso aqui automático
    }

    
    
    public void inicializarTela(Usuario usuario) {
        if (usuario != null) {
            this.usuarioLogado = usuario;
            lblBoasVindas.setText("Bem vindo ADM: " + usuario.getNome() + "!");
            
            //arrega as avaliações específicas apenas deste usuário logado
            carregarAvaliacoesDoUsuario();
        }
    }
    
    private void carregarAvaliacoesDoUsuario() {
        
        try {
            // Obtém a tabela de dentro do painel de rolagem
            javax.swing.JTable tabela = (javax.swing.JTable) tblAvaliacoes.getViewport().getView();
            
            // Pega o modelo usando a própria variável 'tabela' que acabamos de criar
            javax.swing.table.DefaultTableModel tableModel = 
                    (javax.swing.table.DefaultTableModel) tabela.getModel();
            
            // Limpa os registros antigos da tabela antes de renderizar os novos
            tableModel.setRowCount(0); 
            
            // Busca a lista de avaliações pertencentes estritamente a este usuário
            java.util.List<br.com.ifba.avaliacao.entity.Avaliacao> lista = 
                    this.avaliacaoController.findByUsuario(this.usuarioLogado);
            
            if (lista != null) {
                for (br.com.ifba.avaliacao.entity.Avaliacao av : lista) {
                    
                    String tipoAvaliacao = "";
                    String nomeItemAvaliado = "";
                    
                    if (av instanceof br.com.ifba.avaliacao.entity.AvaliacaoBanda) {
                        tipoAvaliacao = "BANDA";
                        nomeItemAvaliado = ((br.com.ifba.avaliacao.entity.AvaliacaoBanda) av).getBandaAvaliada().getNome();
                
                    } else if (av instanceof br.com.ifba.avaliacao.entity.AvaliacaoAlbum) {
                        tipoAvaliacao = "ÁLBUM";
                        nomeItemAvaliado = ((br.com.ifba.avaliacao.entity.AvaliacaoAlbum) av).getAlbumAvaliado().getNome();
                
                    } else if (av instanceof br.com.ifba.avaliacao.entity.AvaliacaoMusica) {
                        tipoAvaliacao = "MÚSICA";
                        nomeItemAvaliado = ((br.com.ifba.avaliacao.entity.AvaliacaoMusica) av).getMusicaAvaliada().getTitulo();
                    }
                    
                    // Converte o comentário em HTML para forçar a quebra de linha automática
                    String comentarioFormatado = "<html><body style='width: 250px;'>" + av.getComentario() + "</body></html>";
                    
                    // Adiciona uma linha contendo as informações da avaliação
                    tableModel.addRow(new Object[]{
                        tipoAvaliacao,         // Coluna 1: Tipo
                        nomeItemAvaliado,      // Coluna 2: Item Avaliado
                        av.getNota(),          // Coluna 3: Nota
                        comentarioFormatado    // Coluna 4: Comentário com quebra automática
                    });
                }
                
                // Define a largura ideal para a coluna de comentário
                if (tabela.getColumnCount() >= 4) {
                    tabela.getColumnModel().getColumn(3).setPreferredWidth(300);
                }
                
                // ATUALIZADO AQUI: Força cada linha a se ajustar à altura do conteúdo textual interno
                for (int row = 0; row < tabela.getRowCount(); row++) {
                    int rowHeight = tabela.getRowHeight();
                    for (int column = 0; column < tabela.getColumnCount(); column++) {
                        java.awt.Component comp = tabela.prepareRenderer(tabela.getCellRenderer(row, column), row, column);
                        rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
                    }
                    tabela.setRowHeight(row, rowHeight);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar as avaliações do usuário: " + e.getMessage());
            e.printStackTrace();
        }
        
        
    /*try {
        // Obtém a tabela de dentro do painel de rolagem
        javax.swing.JTable tabela = (javax.swing.JTable) tblAvaliacoes.getViewport().getView();
        
        // Pega o modelo usando a própria variável 'tabela' que acabamos de criar
        javax.swing.table.DefaultTableModel tableModel = 
                (javax.swing.table.DefaultTableModel) tabela.getModel();
        
        // Limpa os registros antigos da tabela antes de renderizar os novos
        tableModel.setRowCount(0); 
        
        // Busca a lista de avaliações pertencentes estritamente a este usuário
        java.util.List<br.com.ifba.avaliacao.entity.Avaliacao> lista = 
                this.avaliacaoController.findByUsuario(this.usuarioLogado);
        
        if (lista != null) {
            for (br.com.ifba.avaliacao.entity.Avaliacao av : lista) {
                
                String tipoAvaliacao = "";
                String nomeItemAvaliado = "";
                
                if (av instanceof br.com.ifba.avaliacao.entity.AvaliacaoBanda) {
                tipoAvaliacao = "BANDA";
                nomeItemAvaliado = ((br.com.ifba.avaliacao.entity.AvaliacaoBanda) av).getBandaAvaliada().getNome();
            
            } else if (av instanceof br.com.ifba.avaliacao.entity.AvaliacaoAlbum) {
                tipoAvaliacao = "ÁLBUM";
                nomeItemAvaliado = ((br.com.ifba.avaliacao.entity.AvaliacaoAlbum) av).getAlbumAvaliado().getNome();
            
            } else if (av instanceof br.com.ifba.avaliacao.entity.AvaliacaoMusica) {
                tipoAvaliacao = "MÚSICA";
                nomeItemAvaliado = ((br.com.ifba.avaliacao.entity.AvaliacaoMusica) av).getMusicaAvaliada().getTitulo();
            }
                
                // Adiciona uma linha contendo as informações da avaliação
                tableModel.addRow(new Object[]{
                    tipoAvaliacao,         // Coluna 1: Tipo
                    nomeItemAvaliado,      // Coluna 2: Item Avaliado (O que faltava!)
                    av.getNota(),          // Coluna 3: Nota
                    av.getComentario()     // Coluna 4: Comentário
                });
            }
            
            if (tabela.getColumnCount() >= 4) {
                    tabela.getColumnModel().getColumn(3).setPreferredWidth(300);
                }
            
        }
    } catch (Exception e) {
        System.out.println("Erro ao carregar as avaliações do usuário: " + e.getMessage());
        e.printStackTrace();
    }*/
}  
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    /*@SuppressWarnings("unchecked")*/
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();
        lblBoasVindas = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnAvaliarBanda = new javax.swing.JButton();
        btnAvaliarAlbum = new javax.swing.JButton();
        btnAvaliarMusica = new javax.swing.JButton();
        btnBuscarReview = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnEngrenagemBanda = new javax.swing.JButton();
        btnEngrenagemAlbum = new javax.swing.JButton();
        btnEngrenagemMusica = new javax.swing.JButton();
        tblAvaliacoes = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 36)); // NOI18N
        jLabel1.setText("RiffReview");

        btnSair.setBackground(new java.awt.Color(255, 153, 153));
        btnSair.setText("SAIR");
        btnSair.addActionListener(this::btnSairActionPerformed);

        lblBoasVindas.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblBoasVindas.setText("Bem vindo ADM! ");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("MENU DE AÇÕES"));

        btnAvaliarBanda.setText("+");
        btnAvaliarBanda.addActionListener(this::btnAvaliarBandaActionPerformed);

        btnAvaliarAlbum.setText("+");
        btnAvaliarAlbum.addActionListener(this::btnAvaliarAlbumActionPerformed);

        btnAvaliarMusica.setText("+");
        btnAvaliarMusica.addActionListener(this::btnAvaliarMusicaActionPerformed);

        btnBuscarReview.setText("🔍");
        btnBuscarReview.addActionListener(this::btnBuscarReviewActionPerformed);

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        jLabel3.setText("Avaliar Álbum");

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        jLabel7.setText("Avaliar Música");

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        jLabel9.setText("Avaliar Banda");

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        jLabel8.setText("Buscar Review");

        btnEngrenagemBanda.setText("🛠️");
        btnEngrenagemBanda.addActionListener(this::btnEngrenagemBandaActionPerformed);

        btnEngrenagemAlbum.setText("🛠️");
        btnEngrenagemAlbum.addActionListener(this::btnEngrenagemAlbumActionPerformed);

        btnEngrenagemMusica.setText("🛠️");
        btnEngrenagemMusica.addActionListener(this::btnEngrenagemMusicaActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnBuscarReview)
                            .addComponent(btnAvaliarMusica)
                            .addComponent(btnAvaliarAlbum)
                            .addComponent(btnAvaliarBanda))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel3)))
                    .addComponent(btnEngrenagemAlbum, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEngrenagemMusica, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEngrenagemBanda, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 61, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAvaliarBanda)
                    .addComponent(jLabel9))
                .addGap(8, 8, 8)
                .addComponent(btnEngrenagemBanda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAvaliarAlbum)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEngrenagemAlbum)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAvaliarMusica)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEngrenagemMusica)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarReview)
                    .addComponent(jLabel8))
                .addGap(29, 29, 29))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tipo", "Item Avaliado", "Nota", "Comentário"
            }
        ));
        tblAvaliacoes.setViewportView(jTable1);

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 14)); // NOI18N
        jLabel4.setText("SUAS AVALIAÇÕES");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBoasVindas)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSair))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tblAvaliacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addGap(0, 25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btnSair))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblBoasVindas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tblAvaliacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(60, 60, 60))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        this.usuarioLogado = null; // Limpa sessão do usuário atual
        this.dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnAvaliarMusicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvaliarMusicaActionPerformed
        // Busca a lista de músicas através da interface do controller
        java.util.List<Musica> musicas = this.musicaController.findAll(); 
        
        // Abre a tela de avaliações enviando a lista
        avaliacaoSave.inicializarTela(this.usuarioLogado, "MUSICA", musicas);
        
        avaliacaoSave.abrirTela(this); 
    }//GEN-LAST:event_btnAvaliarMusicaActionPerformed

    private void btnAvaliarBandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvaliarBandaActionPerformed
        /*
        try {
        System.out.println("DEBUG: Clicou no botão. Verificando controller: " + (this.bandaController != null));
        
        // Busca a lista de bandas através da interface do controller
        java.util.List<Banda> bandas = this.bandaController.findAll(); 
        System.out.println("DEBUG: Buscou do banco com sucesso. Quantidade: " + (bandas != null ? bandas.size() : "nulo"));
        
        // Abre a tela de avaliações enviando a lista
        avaliacaoSave.inicializarTela(this.usuarioLogado, "BANDA", bandas);
        
    } catch (Exception e) {
        System.out.println("=== O ERRO ACONTECEU AQUI ===");
        e.printStackTrace(); // Isso joga o erro detalhado no console em vermelho
        System.out.println("=============================");
    }*/
        
        /// Busca a lista de bandas através da interface do controller
        java.util.List<Banda> bandas = this.bandaController.findAll(); 
        
        // Abre a tela de avaliações enviando a lista
        avaliacaoSave.inicializarTela(this.usuarioLogado, "BANDA", bandas);
        
        avaliacaoSave.abrirTela(this); 
    }//GEN-LAST:event_btnAvaliarBandaActionPerformed

    private void btnBuscarReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarReviewActionPerformed
        avaliacaoViewSearch.abrirTela(this);  
        //this.avaliacaoViewSearch.setVisible(true);
    }//GEN-LAST:event_btnBuscarReviewActionPerformed

    private void btnEngrenagemBandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEngrenagemBandaActionPerformed
        this.contextoAtual = "BANDA";
        bandaOptions.abrirTela(this);    
    //bandaOptions.setVisible(true); // Abre a tela de opções de Banda;
    }//GEN-LAST:event_btnEngrenagemBandaActionPerformed

    private void btnEngrenagemAlbumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEngrenagemAlbumActionPerformed
        this.contextoAtual = "ALBUM";
        albumOptions.abrirTela(this);
    //albumOptions.setVisible(true); // Abre a tela de opções de Álbum
    
    }//GEN-LAST:event_btnEngrenagemAlbumActionPerformed

    private void btnEngrenagemMusicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEngrenagemMusicaActionPerformed
        this.contextoAtual = "MUSICA";
        musicaOptions.abrirTela(this);
        //musicaOptions.setVisible(true); // Abre a tela de opções de Música
    }//GEN-LAST:event_btnEngrenagemMusicaActionPerformed

    private void btnAvaliarAlbumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvaliarAlbumActionPerformed
        // Busca a lista de álbuns através da interface do controller
        java.util.List<Album> albuns = this.albumController.findAll(); 
        
        // Abre a tela de avaliações enviando a lista
        avaliacaoSave.inicializarTela(this.usuarioLogado, "ALBUM", albuns);
        
        avaliacaoSave.abrirTela(this); 
    }//GEN-LAST:event_btnAvaliarAlbumActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Erro ao carregar o visual Nimbus: " + ex.getMessage());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAvaliarAlbum;
    private javax.swing.JButton btnAvaliarBanda;
    private javax.swing.JButton btnAvaliarMusica;
    private javax.swing.JButton btnBuscarReview;
    private javax.swing.JButton btnEngrenagemAlbum;
    private javax.swing.JButton btnEngrenagemBanda;
    private javax.swing.JButton btnEngrenagemMusica;
    private javax.swing.JButton btnSair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblBoasVindas;
    private javax.swing.JScrollPane tblAvaliacoes;
    // End of variables declaration//GEN-END:variables
}
