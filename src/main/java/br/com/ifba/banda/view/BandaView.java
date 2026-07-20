/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.com.ifba.banda.view;

import br.com.ifba.album.controller.AlbumIController;
import br.com.ifba.banda.controller.BandaIController;
import br.com.ifba.banda.entity.Banda;
import br.com.ifba.musica.controller.MusicaIController;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Julia Freitas
 */

@Component
public class BandaView extends javax.swing.JFrame {
    
    private final AlbumIController albumController;
    private final BandaIController bandaController;
    private final MusicaIController musicaController;
    private final DefaultTableModel tableModel;

    
    private final BandaSave bandaSave;
    
    /**
     * Creates new form BandaView
     */
    @Autowired
    public BandaView(AlbumIController albumController, BandaIController bandaController, MusicaIController musicaController, BandaSave bandaSave) {
        this.albumController = albumController;
        this.bandaController = bandaController;
        this.musicaController = musicaController;
        this.bandaSave = bandaSave;
        initComponents();
        
        // Pega o modelo das colunas definido no seu Design (ID, Título, Genero, Duração, Deletar, Editar)
        this.tableModel = (DefaultTableModel) tblBandas.getModel();
        
        // Configura os botões nas colunas de Ação (Índice 4 = Deletar, Índice 5 = Editar)
        configurarBotoesTabela();
         
        // Carrega as linhas vindas do banco de dados
        carregarBandas();
    }

    private void carregarBandas() {
        try {
            tableModel.setRowCount(0); // Limpa registros antigos
            List<Banda> bandas = bandaController.findAll();
            
            for (Banda b : bandas) {
                tableModel.addRow(new Object[]{
                    b.getId(),
                    b.getNome(), // Se na sua entidade for 'getTitulo', ajuste aqui
                    b.getGeneroPrincipal(),
                    b.getAnoFormacao(),
                    "", // Espaço reservado para o botão Deletar
                    ""  // Espaço reservado para o botão Editar
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar as bandas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void configurarBotoesTabela() {
        // Inicializa o gerenciador para a coluna Deletar (coluna 4)
        BotaoAcaoTabela acaoDeletar = new BotaoAcaoTabela(tblBandas, "🗑️ Deletar", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executarDeletar();
            }
        });
        tblBandas.getColumnModel().getColumn(4).setCellRenderer((javax.swing.table.TableCellRenderer) acaoDeletar);
        tblBandas.getColumnModel().getColumn(4).setCellEditor((javax.swing.table.TableCellEditor) acaoDeletar);

        // Inicializa o gerenciador para a coluna Editar (coluna 5)
        BotaoAcaoTabela acaoEditar = new BotaoAcaoTabela(tblBandas, "✏️ Editar", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executarEditar();
            }
        });
        tblBandas.getColumnModel().getColumn(5).setCellRenderer((javax.swing.table.TableCellRenderer) acaoEditar);
        tblBandas.getColumnModel().getColumn(5).setCellEditor((javax.swing.table.TableCellEditor) acaoEditar);
    }
    
    private void executarDeletar() {
        int linha = tblBandas.getEditingRow();
        if (linha != -1) {
            Long idBanda = (Long) tblBandas.getValueAt(linha, 0);
            String nomeBanda = tblBandas.getValueAt(linha, 1).toString();

            int resposta = JOptionPane.showConfirmDialog(
                this, 
                "Tem certeza que deseja deletar a banda \"" + nomeBanda + "\"?", 
                "Confirmar Exclusão", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (resposta == JOptionPane.YES_OPTION) {
                try {
                    bandaController.delete(idBanda); // Ajuste para deleteById se necessário
                    JOptionPane.showMessageDialog(this, "Banda deletada com sucesso!");
                    carregarBandas(); // Atualiza a tabela instantaneamente
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao deletar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void executarEditar() {
        // Como o botão está dentro de uma célula customizada sob edição, usamos o getEditingRow()
        int linha = tblBandas.getEditingRow(); 
        
        if (linha != -1) {
            try {
                // 1. Recupera o ID da linha selecionada
                Long idBanda = (Long) tblBandas.getValueAt(linha, 0);
                
                // 2. Busca a entidade completa atualizada vinda do banco usando o controller
                Banda bandaParaEditar = this.bandaController.findById(idBanda); 
                
                if (bandaParaEditar != null) {
                    // 3. Alimenta a tela injetada com os dados da banda buscada
                    this.bandaSave.prepararEdicao(bandaParaEditar);
                    
                    // 4. Torna a interface visível para alteração
                    this.bandaSave.setVisible(true);
                    
                    // 5. Fecha a tela de listagem atual
                    this.dispose(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Banda não encontrada no sistema.", "Aviso", JOptionPane.WARNING_MESSAGE);
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
        btnCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBandas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 36)); // NOI18N
        jLabel1.setText("Gerenciamento Bandas");

        btnCancelar.setBackground(new java.awt.Color(255, 153, 153));
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        tblBandas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Título", "Genero", "Ano", "Deletar", "Editar"
            }
        ));
        jScrollPane1.setViewportView(tblBandas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(273, 273, 273)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addGap(411, 411, 411))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 783, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addGap(33, 33, 33))
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
    private javax.swing.JTable tblBandas;
    // End of variables declaration//GEN-END:variables
}
