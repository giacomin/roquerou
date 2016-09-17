package telas;

import dao.ProdutoDAO;
import entidades.Produto;
import javax.swing.JOptionPane;

/**
 *
 * @author giacomin
 */
public class TelaProduto extends javax.swing.JInternalFrame {

    /**
     * Creates new form TelaProduto
     */
    public TelaProduto() {
        initComponents();
    }

    // Método salvarProduto()
    public void salvarProduto() {

        Produto prod = new Produto();

        if ("".equals(campoId.getText())) {
            // New Produto
            prod.setNome(campoNome.getText());
            prod.setDescricao(campoDescricao.getText());
            prod.setUnidade(campoUnidade.getText());
            prod.setValorUnit(Float.parseFloat(campoValorUnit.getText()));
            //prod.setEstoque(Integer.parseInt(campoEstoque.getText()));

        } else {
            // Updade Produto
            prod.setIdProduto(Integer.parseInt(campoId.getText()));
            prod.setNome(campoNome.getText());
            prod.setDescricao(campoDescricao.getText());
            prod.setUnidade(campoUnidade.getText());
            prod.setValorUnit(Float.parseFloat(campoValorUnit.getText()));
            //prod.setEstoque(Integer.parseInt(campoEstoque.getText()));
        }

        String retorno = new ProdutoDAO().salvar(prod);

        if (retorno == null) {
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Erro! Verifique os campos.");
        }

        campoId.setText("");
        campoNome.setText("");
        campoDescricao.setText("");
        campoUnidade.setText("");
        campoValorUnit.setText("");
        campoEstoque.setText("");

        campoNome.setEnabled(false);
        campoDescricao.setEnabled(false);
        campoUnidade.setEnabled(false);
        campoValorUnit.setEnabled(false);
        campoEstoque.setEnabled(false);

        botaoSalvar.setEnabled(false);
        botaoNovo.setEnabled(true);
    }

    // Método listaProdutos()
    public void listaProdutos() {

        botaoSalvar.setEnabled(false);
        botaoEditar.setEnabled(false);
        botaoExcluir.setEnabled(false);
        botaoNovo.setEnabled(true);
        campoNome.setText("");

        campoDescricao.setText("");
        campoUnidade.setText("");
        campoValorUnit.setText("");
        campoEstoque.setText("");

        campoNome.setEnabled(false);
        campoDescricao.setEnabled(false);
        campoUnidade.setEnabled(false);
        campoValorUnit.setEnabled(false);
        campoEstoque.setEnabled(false);

        ProdutoDAO pesquisa = new ProdutoDAO();
        Produto prod = new Produto();

        String comboPesquisa = comboPesquisaProduto.getSelectedItem().toString();
        String campoPesquisa = campoPesquisaProduto.getText();

        prod.setComboPesquisaProduto(comboPesquisa);
        prod.setCampoPesquisaProduto(campoPesquisa);

        pesquisa.listarProduto(tabelaProduto, prod);
    }

    // Método editarProduto()
    public void editarProduto() {

        botaoNovo.setEnabled(false);
        botaoSalvar.setEnabled(true);

        int row = tabelaProduto.getSelectedRow();

        Object id = tabelaProduto.getValueAt(row, 0);
        Object nome = tabelaProduto.getValueAt(row, 1);
        Object descricao = tabelaProduto.getValueAt(row, 2);
        Object unidade = tabelaProduto.getValueAt(row, 3);
        Object valorunit = tabelaProduto.getValueAt(row, 4);
        //Object estoque = tabelaProduto.getValueAt(row, 5);

        Produto prod = new Produto();

        prod.setIdProduto(Integer.parseInt(id.toString()));
        prod.setNome(nome.toString());
        prod.setDescricao(descricao.toString());
        prod.setUnidade(unidade.toString());
        prod.setValorUnit(Float.parseFloat(valorunit.toString()));
        //prod.setEstoque(Integer.parseInt(estoque.toString()));

        campoId.setText(prod.getIdProduto().toString());
        campoNome.setText(prod.getNome());
        campoDescricao.setText(prod.getDescricao());
        campoUnidade.setText(prod.getUnidade());
        campoValorUnit.setText(Float.toString(prod.getValorUnit()));
        //campoEstoque.setText(prod.getEstoque().toString());

        campoNome.setEnabled(true);
        campoDescricao.setEnabled(true);
        campoUnidade.setEnabled(true);
        campoValorUnit.setEnabled(true);
        //campoEstoque.setEnabled(true);
    }

    // Método cadastrarProduto(): limpa campos e deixa apto a salvar
    public void cadastrarProduto() {
        tabelaProduto.clearSelection();
        botaoEditar.setEnabled(false);
        botaoExcluir.setEnabled(false);
        campoNome.setEnabled(true);
        campoDescricao.setEnabled(true);
        campoUnidade.setEnabled(true);
        campoValorUnit.setEnabled(true);
        botaoSalvar.setEnabled(true);
        botaoNovo.setEnabled(false);
    }

    public void excluirProduto() {
        if (JOptionPane.showConfirmDialog(null, "Deseja mesmo remover o registro?", "Cuidado", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            int row = tabelaProduto.getSelectedRow();

            Object id = tabelaProduto.getValueAt(row, 0);
            Object nome = tabelaProduto.getValueAt(row, 1);
            Object descricao = tabelaProduto.getValueAt(row, 2);
            Object unidade = tabelaProduto.getValueAt(row, 3);
            Object valorunit = tabelaProduto.getValueAt(row, 4);
            Object estoque = tabelaProduto.getValueAt(row, 5);

            ProdutoDAO prodDAO = new ProdutoDAO();
            Produto prod = new Produto();

            prod.setIdProduto(Integer.parseInt(id.toString()));
            prod.setNome(nome.toString());
            prod.setDescricao(descricao.toString());
            prod.setUnidade(unidade.toString());
            prod.setValorUnit(Float.parseFloat(valorunit.toString()));
            //prod.setEstoque(Integer.parseInt(estoque.toString()));

            String retorno = prodDAO.remover(prod);

            if (retorno == null) {
                JOptionPane.showMessageDialog(null, "Produto removida com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro! Não foi possível remover a prodade.");
            }
        } else {
            // Não faz nada.
        }
    }

    ;
   
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
        botaoNovo = new javax.swing.JButton();
        botaoSalvar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        campoDescricao = new javax.swing.JTextField();
        campoUnidade = new javax.swing.JTextField();
        campoValorUnit = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        campoEstoque = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        comboPesquisaProduto = new javax.swing.JComboBox();
        campoPesquisaProduto = new javax.swing.JTextField();
        botaoBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaProduto = new javax.swing.JTable();
        botaoExcluir = new javax.swing.JButton();
        botaoEditar = new javax.swing.JButton();
        botaoFechar = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setClosable(true);
        setTitle("Produto");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("Nome:");

        jLabel2.setText("Unidade:");

        jLabel3.setText("Valor Unit:");

        jLabel4.setText("Código:");

        campoId.setEnabled(false);

        campoNome.setEnabled(false);

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

        jLabel5.setText("Descrição:");

        campoDescricao.setEnabled(false);

        campoUnidade.setEnabled(false);

        campoValorUnit.setEnabled(false);

        jLabel6.setText("Estoque:");

        campoEstoque.setEnabled(false);

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
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel5)))
                            .addComponent(campoId, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(campoUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoValorUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoDescricao)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(campoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botaoNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                    .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(campoDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(campoUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoValorUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(campoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        comboPesquisaProduto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nome" }));
        comboPesquisaProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboPesquisaProdutoActionPerformed(evt);
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

        tabelaProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Nome", "Descrição", "Unidade", "Valor Unit.", "Estoque"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaProdutoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaProduto);

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
                        .addComponent(comboPesquisaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoPesquisaProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botaoEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboPesquisaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoPesquisaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 710, Short.MAX_VALUE)
                        .addComponent(botaoFechar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoFechar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarActionPerformed
        salvarProduto();
        listaProdutos();
    }//GEN-LAST:event_botaoSalvarActionPerformed

    private void botaoBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoBuscarActionPerformed
        listaProdutos();
    }//GEN-LAST:event_botaoBuscarActionPerformed

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void botaoNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoNovoActionPerformed
        cadastrarProduto();
    }//GEN-LAST:event_botaoNovoActionPerformed

    private void tabelaProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaProdutoMouseClicked
        botaoEditar.setEnabled(true);
        botaoExcluir.setEnabled(true);
    }//GEN-LAST:event_tabelaProdutoMouseClicked

    private void botaoExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoExcluirActionPerformed
        excluirProduto();
        listaProdutos();
    }//GEN-LAST:event_botaoExcluirActionPerformed
    private void botaoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditarActionPerformed
        editarProduto();


    }//GEN-LAST:event_botaoEditarActionPerformed

    private void comboPesquisaProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboPesquisaProdutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboPesquisaProdutoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoBuscar;
    private javax.swing.JButton botaoEditar;
    private javax.swing.JButton botaoExcluir;
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton botaoNovo;
    private javax.swing.JButton botaoSalvar;
    private javax.swing.JTextField campoDescricao;
    private javax.swing.JTextField campoEstoque;
    private javax.swing.JTextField campoId;
    private javax.swing.JTextField campoNome;
    private javax.swing.JTextField campoPesquisaProduto;
    private javax.swing.JTextField campoUnidade;
    private javax.swing.JTextField campoValorUnit;
    private javax.swing.JComboBox comboPesquisaProduto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaProduto;
    // End of variables declaration//GEN-END:variables
}
