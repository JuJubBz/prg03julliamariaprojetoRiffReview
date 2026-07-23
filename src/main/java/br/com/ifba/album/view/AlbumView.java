/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.ifba.album.view;

import br.com.ifba.album.controller.AlbumIController;
import br.com.ifba.album.entity.Album;
import br.com.ifba.banda.controller.BandaIController;
import br.com.ifba.musica.controller.MusicaIController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.springframework.stereotype.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author Julia Freitas
 */

@Component
public class AlbumView extends javax.swing.JFrame {
    
    private final AlbumIController albumController;
    private final BandaIController bandaController;
    private final MusicaIController musicaController;
    private final DefaultTableModel tableModel;
    
    
    private final AlbumSave albumSave;
    private javax.swing.JFrame telaAnterior;
    
    public void abrirTela(javax.swing.JFrame telaAnterior) {
    this.telaAnterior = telaAnterior;
        if (this.telaAnterior != null) {
            this.telaAnterior.setVisible(false);
        }
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    /**
     * Creates new form AlbumView
     */
    @Autowired
    public AlbumView(AlbumIController albumController, BandaIController bandaController, MusicaIController musicaController, AlbumSave albumSave) {
        this.albumController = albumController;
        this.bandaController = bandaController;
        this.musicaController = musicaController;
        this.albumSave = albumSave;
        
        initComponents();
        
        // Pega o modelo das colunas definido no Design (ID, Título, Ano Lançamento, Banda, Deletar, Editar)
        this.tableModel = (DefaultTableModel) tblAlbuns.getModel();
        
        // Configura os botões nas colunas de Ação (Índice 4 = Deletar, Índice 5 = Editar)
        configurarBotoesTabela();
         
        // Carrega as linhas vindas do Supabase
        carregarAlbuns();
    }

    private void carregarAlbuns() {
        try {
            tableModel.setRowCount(0); // Limpa registros antigos
            List<Album> albuns = albumController.findAll();
            
            for (Album a : albuns) {
                tableModel.addRow(new Object[]{
                    a.getId(),
                    a.getNome(),
                    a.getAnoLancamento(),
                    a.getBanda() != null ? a.getBanda().getNome() : "Sem Banda",
                    "", // Espaço reservado para o botão Deletar
                    ""  // Espaço reservado para o botão Editar
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os álbuns: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void configurarBotoesTabela() {
        BotaoAcaoTabela acaoDeletar = new BotaoAcaoTabela(tblAlbuns, "🗑️ Deletar", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pega a linha exata que veio do e.getActionCommand()
                int linha = Integer.parseInt(e.getActionCommand());
                executarDeletar(linha);
            }
        });
        tblAlbuns.getColumnModel().getColumn(4).setCellRenderer((TableCellRenderer) acaoDeletar);
        tblAlbuns.getColumnModel().getColumn(4).setCellEditor((TableCellEditor) acaoDeletar);

        BotaoAcaoTabela acaoEditar = new BotaoAcaoTabela(tblAlbuns, "✏️ Editar", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pega a linha exata que veio do e.getActionCommand()
                int linha = Integer.parseInt(e.getActionCommand());
                executarEditar(linha);
            }
        });
        tblAlbuns.getColumnModel().getColumn(5).setCellRenderer((TableCellRenderer) acaoEditar);
        tblAlbuns.getColumnModel().getColumn(5).setCellEditor((TableCellEditor) acaoEditar);
    }
    
    private void executarDeletar(int linha) {
        if (linha >= 0 && linha < tblAlbuns.getRowCount()) {
            Long idAlbum = (Long) tblAlbuns.getValueAt(linha, 0);
            String nomeAlbum = tblAlbuns.getValueAt(linha, 1).toString();

            int resposta = JOptionPane.showConfirmDialog(
                this, 
                "Tem certeza que deseja deletar o álbum \"" + nomeAlbum + "\"?", 
                "Confirmar Exclusão", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (resposta == JOptionPane.YES_OPTION) {
                try {
                    albumController.delete(idAlbum);
                    JOptionPane.showMessageDialog(this, "Álbum deletado com sucesso!");
                    carregarAlbuns(); // Atualiza a tabela instantaneamente
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao deletar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void executarEditar(int linha) {
        if (linha >= 0 && linha < tblAlbuns.getRowCount()) {
            Long idAlbum = (Long) tblAlbuns.getValueAt(linha, 0);
            
            try {
                // Busca o objeto completo e atualizado no banco
                Album albumParaEditar = albumController.findById(idAlbum);
                
                if (albumParaEditar != null) {
                    // Prepara os campos e o ID na tela de gravação antes de abri-la
                    this.albumSave.prepararEdicao(albumParaEditar);
                    albumSave.abrirTela(this);
                    this.dispose(); // Fecha a tela atual de listagem
                } else {
                    JOptionPane.showMessageDialog(this, "Álbum não encontrado no sistema.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
                
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
        tblAlbuns = new javax.swing.JTable();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 36)); // NOI18N
        jLabel1.setText("Gerenciamento Albuns");

        tblAlbuns.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Título", "Ano Lançamento", "Banda", "Deletar", "Editar"
            }
        ));
        jScrollPane1.setViewportView(tblAlbuns);

        btnCancelar.setBackground(new java.awt.Color(255, 153, 153));
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(418, 418, 418)
                .addComponent(btnCancelar)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 783, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(279, 279, 279))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addGap(54, 54, 54)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnCancelar)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
        if (this.telaAnterior != null) {
            this.telaAnterior.setVisible(true); // Reexibe a janela pai ao voltar
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]){ 
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
        private int linhaAtual = -1; // Guarda o índice da linha clicada

        public BotaoAcaoTabela(JTable tabela, String textoBotao, ActionListener acaoClique) {
            this.tabela = tabela;
            this.botao = new JButton(textoBotao);
            
            this.botao.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    // Envia o número da linha clicada no ActionEvent
                    ActionEvent eventoComLinha = new ActionEvent(e.getSource(), e.getID(), String.valueOf(linhaAtual));
                    acaoClique.actionPerformed(eventoComLinha);
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
            this.linhaAtual = row; // Captura a linha no momento da edicao/clique
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
    private javax.swing.JTable tblAlbuns;
    // End of variables declaration//GEN-END:variables
}
