/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.ifba.musica.view;

import br.com.ifba.album.controller.AlbumIController;
import br.com.ifba.banda.controller.BandaIController;
import br.com.ifba.musica.controller.MusicaIController;
import br.com.ifba.musica.entity.Musica;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import org.springframework.stereotype.Component;

/**
 *
 * @author Julia Freitas
 */

@Component
public class MusicaView extends javax.swing.JFrame {
  
    private final AlbumIController albumController;
    private final BandaIController bandaController;
    private final MusicaIController musicaController;
    private final DefaultTableModel tableModel;

    /**
     * Creates new form MusicaView
     */
    public MusicaView(AlbumIController albumController, BandaIController bandaController, MusicaIController musicaController) {
        
        this.albumController = albumController;
        this.bandaController = bandaController;
        this.musicaController = musicaController;
        
        initComponents();
        
        // Pega o modelo das colunas definido no Design (ID, Título, Gênero, Álbum, Deletar, Editar)
        this.tableModel = (DefaultTableModel) tblMusicas.getModel();
        
        // Configura os botões nas colunas de Ação (Índice 4 = Deletar, Índice 5 = Editar)
        configurarBotoesTabela();
         
        // Carrega as linhas vindas do banco de dados
        carregarMusicas();
        
    }

    private void carregarMusicas() {
        try {
            tableModel.setRowCount(0); // Limpa registros antigos
            List<Musica> musicas = musicaController.findAll();
            
            for (Musica m : musicas) {
                tableModel.addRow(new Object[]{
                    m.getId(),
                    m.getTitulo(),
                    m.getGeneroPrincipal(),
                    m.getAlbum() != null ? m.getAlbum().getNome() : "Sem Álbum",
                    "", // Espaço reservado para o botão Deletar
                    ""  // Espaço reservado para o botão Editar
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar as músicas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void configurarBotoesTabela() {
        // Inicializa o gerenciador para a coluna Deletar (coluna 4)
        BotaoAcaoTabela acaoDeletar = new BotaoAcaoTabela(tblMusicas, "🗑️ Deletar", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executarDeletar();
            }
        });
        tblMusicas.getColumnModel().getColumn(4).setCellRenderer((javax.swing.table.TableCellRenderer) acaoDeletar);
        tblMusicas.getColumnModel().getColumn(4).setCellEditor((javax.swing.table.TableCellEditor) acaoDeletar);

        // Inicializa o gerenciador para a coluna Editar (coluna 5)
        BotaoAcaoTabela acaoEditar = new BotaoAcaoTabela(tblMusicas, "✏️ Editar", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executarEditar();
            }
        });
        tblMusicas.getColumnModel().getColumn(5).setCellRenderer((javax.swing.table.TableCellRenderer) acaoEditar);
        tblMusicas.getColumnModel().getColumn(5).setCellEditor((javax.swing.table.TableCellEditor) acaoEditar);
    }
    
    private void executarDeletar() {
        int linha = tblMusicas.getEditingRow();
        if (linha != -1) {
            Long idMusica = (Long) tblMusicas.getValueAt(linha, 0);
            String tituloMusica = tblMusicas.getValueAt(linha, 1).toString();

            int resposta = JOptionPane.showConfirmDialog(
                this, 
                "Tem certeza que deseja deletar a música \"" + tituloMusica + "\"?", 
                "Confirmar Exclusão", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (resposta == JOptionPane.YES_OPTION) {
                try {
                    musicaController.delete(idMusica); // Se seu controller usar deleteById, altere aqui
                    JOptionPane.showMessageDialog(this, "Música deletada com sucesso!");
                    carregarMusicas(); // Atualiza a tabela instantaneamente
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao deletar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void executarEditar() {
        int linha = tblMusicas.getEditingRow();
        if (linha != -1) {
            Long idMusica = (Long) tblMusicas.getValueAt(linha, 0);
            
            try {
                // Aqui você buscaria a música caso fosse abrir a tela de salvamento em modo edição
                // Musica musicaParaEditar = musicaController.findById(idMusica);
                
                // Exemplo de redirecionamento caso você possua a tela MusicaSave:
                // MusicaSave telaCadastro = new MusicaSave(albumController, bandaController, musicaController);
                // telaCadastro.setVisible(true);
                // this.dispose();
                
                JOptionPane.showMessageDialog(this, "Ação Editar acionada para o ID: " + idMusica);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao abrir edição: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMusicas = new javax.swing.JTable();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 36)); // NOI18N
        jLabel1.setText("Gerenciamento Musicas");

        tblMusicas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Título", "Genero", "Duração", "Album", "Deletar", "Editar"
            }
        ));
        jScrollPane1.setViewportView(tblMusicas);

        btnCancelar.setBackground(new java.awt.Color(255, 153, 153));
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 783, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addGap(406, 406, 406))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(258, 258, 258))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel1)
                .addGap(43, 43, 43)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnCancelar)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

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
    
    private static class BotaoAcaoTabela extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
        private final JButton botao;
        private final JTable tabela;
        private Object valorCelula;

        public BotaoAcaoTabela(JTable tabela, String textoBotao, ActionListener acaoClique) {
            this.tabela = tabela;
            this.botao = new JButton(textoBotao);
            
            this.botao.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    acaoClique.actionPerformed(e);
                }
            });
        }

        @Override
        public Object getCellEditorValue() {
            return valorCelula;
        }

        @Override
        public java.awt.Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.valorCelula = value;
            return botao;
        }

        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return botao;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblMusicas;
    // End of variables declaration//GEN-END:variables
}
