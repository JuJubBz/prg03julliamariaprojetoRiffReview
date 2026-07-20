/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.ifba.avaliacao.view;

import br.com.ifba.album.controller.AlbumIController;
import br.com.ifba.album.entity.Album;
import br.com.ifba.avaliacao.controller.AvaliacaoIController;
import br.com.ifba.banda.controller.BandaIController;
import br.com.ifba.banda.entity.Banda;
import br.com.ifba.musica.controller.MusicaIController;
import br.com.ifba.musica.entity.Musica;
import br.com.ifba.usuario.entity.Usuario;
import org.springframework.stereotype.Component;

/**
 *
 * @author Julia Freitas
 */
@Component
public class AvaliacaoView extends javax.swing.JFrame {
    
   private final AvaliacaoSave avaliacaoSave;
    private final AvaliacaoViewSearch avaliacaoViewSearch;
    private final AvaliacaoIController avaliacaoController;
    
    private final BandaIController bandaController;
    private final AlbumIController albumController;
    private final MusicaIController musicaController;
    
    private Usuario usuarioLogado = null;

    public AvaliacaoView(AvaliacaoSave avaliacaoSave,
                         AvaliacaoViewSearch avaliacaoViewSearch,
                         AvaliacaoIController avaliacaoController,
                         BandaIController bandaController,
                         AlbumIController albumController,
                         MusicaIController musicaController) {
        
        this.avaliacaoSave = avaliacaoSave;
        this.avaliacaoViewSearch = avaliacaoViewSearch;
        this.avaliacaoController = avaliacaoController;
        this.bandaController = bandaController;
        this.albumController = albumController;
        this.musicaController = musicaController;
        
        initComponents();
    }

    public void inicializarTela(Usuario usuario) {
        if (usuario != null) {
        // ESSA LINHA É A MAIS IMPORTANTE: ela salva o usuário para os botões usarem depois
        this.usuarioLogado = usuario; 
        
        lblBoasVindas.setText("Bem vindo: " + usuario.getNome() + "!");
    } else {
        System.out.println("ALERTA: O método inicializarTela recebeu um usuário NULO!");
    }
    
    // Recarrega a tabela com os dados do usuário correto
    carregarAvaliacoesDoUsuario();
    }
    
    private void carregarAvaliacoesDoUsuario() {
        
        try {
        // Use diretamente a variavel da tabela gerada pelo NetBeans
        javax.swing.table.DefaultTableModel tableModel = 
                (javax.swing.table.DefaultTableModel) jTable1.getModel();
        
        tableModel.setRowCount(0); 
        
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
                
                tableModel.addRow(new Object[]{
                    tipoAvaliacao,         // Coluna 1: Tipo
                    nomeItemAvaliado,      // Coluna 2: Nome
                    av.getNota(),          // Coluna 3: Nota
                    comentarioFormatado    // Coluna 4: Texto (Comentário)
                });
            }
            
            if (jTable1.getColumnCount() >= 4) {
                jTable1.getColumnModel().getColumn(3).setPreferredWidth(300);
            }
            
            // Força cada linha a se ajustar à altura do conteúdo textual interno
            for (int row = 0; row < jTable1.getRowCount(); row++) {
                int rowHeight = jTable1.getRowHeight();
                for (int column = 0; column < jTable1.getColumnCount(); column++) {
                    java.awt.Component comp = jTable1.prepareRenderer(jTable1.getCellRenderer(row, column), row, column);
                    rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
                }
                jTable1.setRowHeight(row, rowHeight);
            }
        }
    } catch (Exception e) {
        System.out.println("Erro ao carregar as avaliações do usuário: " + e.getMessage());
        e.printStackTrace();
    }
        
        /*try {
            javax.swing.JTable tabela = (javax.swing.JTable) tblAvaliacoes.getViewport().getView();
            javax.swing.table.DefaultTableModel tableModel = 
                    (javax.swing.table.DefaultTableModel) tabela.getModel();
            
            tableModel.setRowCount(0); 
            
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
                    
                    tableModel.addRow(new Object[]{
                        tipoAvaliacao,         // Coluna 1: Tipo
                        nomeItemAvaliado,      // Coluna 2: Nome
                        av.getNota(),          // Coluna 3: Nota
                        comentarioFormatado    // Coluna 4: Texto (Comentário)
                    });
                }
                
                if (tabela.getColumnCount() >= 4) {
                    tabela.getColumnModel().getColumn(3).setPreferredWidth(300);
                }
                
                // Força cada linha a se ajustar à altura do conteúdo textual interno
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
        }*/
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
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
        lblBoasVindas.setText("Bem vindo! ");

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
        jLabel8.setText("Reviews");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBuscarReview)
                    .addComponent(btnAvaliarMusica)
                    .addComponent(btnAvaliarAlbum)
                    .addComponent(btnAvaliarBanda))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel3)
                    .addComponent(jLabel9))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAvaliarBanda)
                    .addComponent(jLabel9))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAvaliarAlbum)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAvaliarMusica)
                    .addComponent(jLabel7))
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarReview)
                    .addComponent(jLabel8))
                .addGap(41, 41, 41))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Tipo", "Nome", "Nota", "Texto"
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
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(lblBoasVindas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tblAvaliacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(0, 34, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
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
        this.usuarioLogado = null;
        this.dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnAvaliarMusicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvaliarMusicaActionPerformed
        java.util.List<Musica> musicas = this.musicaController.findAll(); 
        avaliacaoSave.inicializarTela(this.usuarioLogado, "MUSICA", musicas);
    }//GEN-LAST:event_btnAvaliarMusicaActionPerformed

    private void btnAvaliarBandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvaliarBandaActionPerformed
        java.util.List<Banda> bandas = this.bandaController.findAll(); 
        
        System.out.println("DEBUG - Usuário na View Principal: " + this.usuarioLogado);
        
        avaliacaoSave.inicializarTela(this.usuarioLogado, "BANDA", bandas);
    }//GEN-LAST:event_btnAvaliarBandaActionPerformed

    private void btnBuscarReviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarReviewActionPerformed
        this.avaliacaoViewSearch.setVisible(true);
    }//GEN-LAST:event_btnBuscarReviewActionPerformed

    private void btnAvaliarAlbumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvaliarAlbumActionPerformed
        java.util.List<Album> albuns = this.albumController.findAll(); 
        avaliacaoSave.inicializarTela(this.usuarioLogado, "ALBUM", albuns);
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
