package telas;

import config.HibernateUtil;
import dao.CidadeDAO;
import entidades.Cidade;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author giacomin
 */
public class TelaCidade extends javax.swing.JInternalFrame {

    /**
     * Creates new form TelaCidade
     */
    public TelaCidade() {
        initComponents();
    }

    // Método salvarCidade()
    public void salvarCidade() {

        Cidade cid = new Cidade();

        if ("".equals(campoId.getText())) {
            cid.setNome(campoNome.getText());
            cid.setUf((String) comboUF.getSelectedItem());
            cid.setCep(Integer.parseInt(campoCEP.getText()));
        } else {
            cid.setIdCidade(Integer.parseInt(campoId.getText()));
            cid.setNome(campoNome.getText());
            cid.setUf((String) comboUF.getSelectedItem());
            cid.setCep(Integer.parseInt(campoCEP.getText()));
        }

        String retorno = new CidadeDAO().salvar(cid);

        if (retorno == null) {
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Erro! Verifique os campos.");
        }

        campoId.setText("");
        campoNome.setText("");
        campoCEP.setText("");

        campoNome.setEnabled(false);
        campoCEP.setEnabled(false);
        comboUF.setEnabled(false);

        botaoSalvar.setEnabled(false);
        botaoNovo.setEnabled(true);
    }

    // Método listaCidades()
    public void listaCidades() {

        botaoSalvar.setEnabled(false);
        botaoEditar.setEnabled(false);
        botaoExcluir.setEnabled(false);
        botaoNovo.setEnabled(true);
        campoNome.setText("");
        campoCEP.setText("");
        campoNome.setEnabled(false);
        comboUF.setEnabled(false);
        campoCEP.setEnabled(false);

        CidadeDAO pesquisa = new CidadeDAO();
        Cidade cid = new Cidade();

        String comboPesquisa = comboPesquisaCidade.getSelectedItem().toString();
        String campoPesquisa = campoPesquisaCidade.getText();

        cid.setComboPesquisaCidade(comboPesquisa);
        cid.setCampoPesquisaCidade(campoPesquisa);

        pesquisa.listarCidade(tabelaCidade, cid);
    }

    // Método cadastrarCidade(): limpa campos e deixa apto a salvar
    public void cadastrarCidade() {

        tabelaCidade.clearSelection();

        botaoEditar.setEnabled(false);
        botaoExcluir.setEnabled(false);

        campoNome.setEnabled(true);
        campoCEP.setEnabled(true);
        comboUF.setEnabled(true);

        botaoSalvar.setEnabled(true);
        botaoNovo.setEnabled(false);
    }

    public void excluirCidade() {

        if (JOptionPane.showConfirmDialog(null, "Deseja mesmo remover o registro?", "Cuidado", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            int row = tabelaCidade.getSelectedRow();

            Object id = tabelaCidade.getValueAt(row, 0);
            Object nome = tabelaCidade.getValueAt(row, 1);
            Object cep = tabelaCidade.getValueAt(row, 2);
            Object uf = tabelaCidade.getValueAt(row, 3);

            CidadeDAO cidDAO = new CidadeDAO();
            Cidade cid = new Cidade();

            cid.setIdCidade(Integer.parseInt(id.toString()));
            cid.setNome(nome.toString());
            cid.setCep(Integer.parseInt(cep.toString()));
            cid.setUf(uf.toString());

            String retorno = cidDAO.remover(cid);

            if (retorno == null) {
                JOptionPane.showMessageDialog(null, "Cidade removida com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro! Não foi possível remover a cidade.");
            }
        } else {
            // Não faz nada.
        }
    };
   
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        campoId = new javax.swing.JTextField();
        campoNome = new javax.swing.JTextField();
        comboUF = new javax.swing.JComboBox();
        campoCEP = new javax.swing.JTextField();
        botaoNovo = new javax.swing.JButton();
        botaoSalvar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        comboPesquisaCidade = new javax.swing.JComboBox();
        campoPesquisaCidade = new javax.swing.JTextField();
        botaoBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaCidade = new javax.swing.JTable();
        botaoExcluir = new javax.swing.JButton();
        botaoEditar = new javax.swing.JButton();
        botaoFechar = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setClosable(true);
        setTitle("Cidade");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("Nome:");

        jLabel2.setText("UF:");

        jLabel3.setText("CEP:");

        jLabel4.setText("Código:");

        campoId.setEnabled(false);

        campoNome.setEnabled(false);

        comboUF.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RS", "SC", "PR" }));
        comboUF.setEnabled(false);
        comboUF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboUFActionPerformed(evt);
            }
        });

        campoCEP.setEnabled(false);

        botaoNovo.setText("Novo");
        botaoNovo.setMaximumSize(new java.awt.Dimension(80, 27));
        botaoNovo.setMinimumSize(new java.awt.Dimension(80, 27));
        botaoNovo.setPreferredSize(new java.awt.Dimension(80, 27));
        botaoNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoNovoActionPerformed(evt);
            }
        });

        botaoSalvar.setText("Salvar");
        botaoSalvar.setEnabled(false);
        botaoSalvar.setMaximumSize(new java.awt.Dimension(80, 27));
        botaoSalvar.setMinimumSize(new java.awt.Dimension(80, 27));
        botaoSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(comboUF, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoCEP))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(campoId, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(campoNome)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 392, Short.MAX_VALUE)
                        .addComponent(botaoNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(campoId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(comboUF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(campoCEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        comboPesquisaCidade.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nome", "UF" }));
        comboPesquisaCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPesquisaCidadeActionPerformed(evt);
            }
        });

        botaoBuscar.setText("Buscar");
        botaoBuscar.setMaximumSize(new java.awt.Dimension(80, 27));
        botaoBuscar.setMinimumSize(new java.awt.Dimension(80, 27));
        botaoBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoBuscarActionPerformed(evt);
            }
        });

        tabelaCidade.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código", "Nome", "CEP", "UF"
            }
        ));
        tabelaCidade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaCidadeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaCidade);

        botaoExcluir.setText("Excluir");
        botaoExcluir.setEnabled(false);
        botaoExcluir.setMaximumSize(new java.awt.Dimension(80, 27));
        botaoExcluir.setMinimumSize(new java.awt.Dimension(80, 27));
        botaoExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoExcluirActionPerformed(evt);
            }
        });

        botaoEditar.setText("Editar");
        botaoEditar.setEnabled(false);
        botaoEditar.setMaximumSize(new java.awt.Dimension(80, 27));
        botaoEditar.setMinimumSize(new java.awt.Dimension(80, 27));
        botaoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(comboPesquisaCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoPesquisaCidade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botaoEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboPesquisaCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoPesquisaCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        botaoFechar.setText("Fechar");
        botaoFechar.setMaximumSize(new java.awt.Dimension(80, 27));
        botaoFechar.setMinimumSize(new java.awt.Dimension(80, 27));
        botaoFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoFecharActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(botaoFechar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botaoFechar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarActionPerformed
        salvarCidade();
        listaCidades();
    }//GEN-LAST:event_botaoSalvarActionPerformed

    private void botaoBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoBuscarActionPerformed
        listaCidades();
    }//GEN-LAST:event_botaoBuscarActionPerformed

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void botaoNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoNovoActionPerformed
        cadastrarCidade();
    }//GEN-LAST:event_botaoNovoActionPerformed

    private void comboUFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboUFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboUFActionPerformed

    private void comboPesquisaCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPesquisaCidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPesquisaCidadeActionPerformed

    private void tabelaCidadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaCidadeMouseClicked
        botaoEditar.setEnabled(true);
        botaoExcluir.setEnabled(true);
    }//GEN-LAST:event_tabelaCidadeMouseClicked

    private void botaoExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoExcluirActionPerformed
        excluirCidade();
        listaCidades();
    }//GEN-LAST:event_botaoExcluirActionPerformed
    private void botaoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditarActionPerformed
        
        botaoNovo.setEnabled(false);
        botaoSalvar.setEnabled(true);

        int row = tabelaCidade.getSelectedRow();

        Object id = tabelaCidade.getValueAt(row, 0);
        Object nome = tabelaCidade.getValueAt(row, 1);
        Object cep = tabelaCidade.getValueAt(row, 2);
        Object uf = tabelaCidade.getValueAt(row, 3);

        Cidade cid = new Cidade();

        cid.setIdCidade(Integer.parseInt(id.toString()));
        cid.setNome(nome.toString());
        cid.setCep(Integer.parseInt(cep.toString()));
        cid.setUf(uf.toString());

        campoId.setText(cid.getIdCidade().toString());
        campoNome.setText(cid.getNome());
        campoCEP.setText(cid.getCep().toString());
        comboUF.setSelectedItem(cep);

        campoNome.setEnabled(true);
        campoCEP.setEnabled(true);
        comboUF.setEnabled(true);

    }//GEN-LAST:event_botaoEditarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoBuscar;
    private javax.swing.JButton botaoEditar;
    private javax.swing.JButton botaoExcluir;
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton botaoNovo;
    private javax.swing.JButton botaoSalvar;
    private javax.swing.JTextField campoCEP;
    private javax.swing.JTextField campoId;
    private javax.swing.JTextField campoNome;
    private javax.swing.JTextField campoPesquisaCidade;
    private javax.swing.JComboBox comboPesquisaCidade;
    private javax.swing.JComboBox comboUF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaCidade;
    // End of variables declaration//GEN-END:variables
}
