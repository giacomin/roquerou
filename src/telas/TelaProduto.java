package telas;

import dao.ProdutoDAO;
import entidades.Produto;
import java.awt.Color;
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

    // *** Método salvarProduto() ***
    public void salvarProduto() {

        Produto prod = new Produto();

        campoNome.setBackground(Color.white);
        campoUnidade.setBackground(Color.white);
        campoValorUnit.setBackground(Color.white);

        boolean nomeProduto = prod.validaNomeProduto(campoNome.getText());
        boolean nomeUnidade = prod.validaUnidadeProduto(campoUnidade.getText());
        boolean valorProduto = prod.validaValorProduto(campoValorUnit.getText());

        // Se todos os campos estiverem OK...
        if (nomeProduto == true && nomeUnidade == true && valorProduto == true) {

            // Quando for um novo produto
            if ("".equals(campoId.getText())) {
                prod.setNome(campoNome.getText());
                prod.setDescricao(campoDescricao.getText());
                prod.setUnidade(campoUnidade.getText());
                prod.setValorUnit(Float.parseFloat(campoValorUnit.getText()));
                //prod.setEstoque(Integer.parseInt(campoEstoque.getText()));

            } // Quando for uma edição de produto
            else {
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

            pesquisarProduto();

        } // Caso contrário (se a validação não estiver OK)...
        else {
            if (prod.validaNomeProduto(campoNome.getText()) == false) {
                campoNome.setBackground(Color.pink);
            }
            if (prod.validaUnidadeProduto(campoUnidade.getText()) == false) {
                campoUnidade.setBackground(Color.pink);
            }

            if (prod.validaValorProduto(campoValorUnit.getText()) == false) {
                campoValorUnit.setBackground(Color.pink);
            }

            JOptionPane.showMessageDialog(null, "Verifique os campos em destaque", "Campo(s) inválidos", JOptionPane.WARNING_MESSAGE);
        }
    }

    // *** Método pesquisarProdutos() ***
    public void pesquisarProduto() {

        campoNome.setBackground(Color.white);
        campoUnidade.setBackground(Color.white);
        campoValorUnit.setBackground(Color.white);

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

    // *** Método editarProduto() ***
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

    // *** Método novoProduto(): limpa campos e deixa apto a salvar ***
    public void novoProduto() {
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
                JOptionPane.showMessageDialog(null, "Produto removido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Erro! Não foi possível remover o produto.");
            }
        }
    }
   
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
        jLabel6 = new javax.swing.JLabel();
        campoEstoque = new javax.swing.JTextField();
        campoValorUnit = new javax.swing.JFormattedTextField();
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

        jLabel3.setText("Valor R$:");

        jLabel4.setText("Código:");

        campoId.setEnabled(false);

        campoNome.setEnabled(false);

        botaoNovo.setIcon(new javax.swing.ImageIcon("/home/giacomin/Documentos/NetBeansProjects/Roquerou/icons/edit_add.png")); // NOI18N
        botaoNovo.setText("Novo");
        botaoNovo.setMaximumSize(new java.awt.Dimension(80, 27));
        botaoNovo.setMinimumSize(new java.awt.Dimension(80, 27));
        botaoNovo.setPreferredSize(new java.awt.Dimension(80, 27));
        botaoNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoNovoActionPerformed(evt);
            }
        });

        botaoSalvar.setIcon(new javax.swing.ImageIcon("/home/giacomin/Documentos/NetBeansProjects/Roquerou/icons/dialog_ok_apply.png")); // NOI18N
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

        jLabel6.setText("Estoque:");

        campoEstoque.setEnabled(false);

        campoValorUnit.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoId, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(botaoNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(botaoSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(campoUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(22, 22, 22)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(campoValorUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(28, 28, 28)
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(campoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(campoDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(campoUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(campoValorUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(campoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        comboPesquisaProduto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nome" }));

        botaoBuscar.setIcon(new javax.swing.ImageIcon("/home/giacomin/Documentos/NetBeansProjects/Roquerou/icons/edit_find.png")); // NOI18N
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
                "Cód.", "Nome", "Descrição", "Unid.", "Valor Unit.", "Estoque"
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
        if (tabelaProduto.getColumnModel().getColumnCount() > 0) {
            tabelaProduto.getColumnModel().getColumn(0).setMaxWidth(60);
            tabelaProduto.getColumnModel().getColumn(1).setMinWidth(150);
            tabelaProduto.getColumnModel().getColumn(2).setMinWidth(250);
            tabelaProduto.getColumnModel().getColumn(3).setMaxWidth(75);
            tabelaProduto.getColumnModel().getColumn(4).setMaxWidth(75);
            tabelaProduto.getColumnModel().getColumn(5).setMinWidth(65);
        }

        botaoExcluir.setIcon(new javax.swing.ImageIcon("/home/giacomin/Documentos/NetBeansProjects/Roquerou/icons/list_remove.png")); // NOI18N
        botaoExcluir.setText("Excluir");
        botaoExcluir.setEnabled(false);
        botaoExcluir.setMaximumSize(new java.awt.Dimension(80, 27));
        botaoExcluir.setMinimumSize(new java.awt.Dimension(80, 27));
        botaoExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoExcluirActionPerformed(evt);
            }
        });

        botaoEditar.setIcon(new javax.swing.ImageIcon("/home/giacomin/Documentos/NetBeansProjects/Roquerou/icons/edit.png")); // NOI18N
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
                        .addComponent(campoPesquisaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botaoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
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

        botaoFechar.setIcon(new javax.swing.ImageIcon("/home/giacomin/Documentos/NetBeansProjects/Roquerou/icons/editdelete.png")); // NOI18N
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
                        .addGap(0, 0, Short.MAX_VALUE)
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
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarActionPerformed
        salvarProduto();
    }//GEN-LAST:event_botaoSalvarActionPerformed

    private void botaoBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoBuscarActionPerformed
        pesquisarProduto();
    }//GEN-LAST:event_botaoBuscarActionPerformed

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void botaoNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoNovoActionPerformed
        novoProduto();
    }//GEN-LAST:event_botaoNovoActionPerformed

    private void tabelaProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaProdutoMouseClicked
        botaoEditar.setEnabled(true);
        botaoExcluir.setEnabled(true);
    }//GEN-LAST:event_tabelaProdutoMouseClicked

    private void botaoExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoExcluirActionPerformed
        excluirProduto();
        pesquisarProduto();
    }//GEN-LAST:event_botaoExcluirActionPerformed
    private void botaoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditarActionPerformed
        editarProduto();
    }//GEN-LAST:event_botaoEditarActionPerformed


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
    private javax.swing.JFormattedTextField campoValorUnit;
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
