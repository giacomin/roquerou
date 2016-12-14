package telas;

import dao.CidadeDAO;
import entidades.Cidade;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

//Logs
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author giacomin
 */
public class TelaCidade extends javax.swing.JInternalFrame {

    private static final Logger LOG = Logger.getLogger(TelaCidade.class.getName());
    
    /**
     * Creates new form TelaCidade
     */
    public TelaCidade() {

        try {
            Handler console = new ConsoleHandler();
            Handler file = new FileHandler("/tmp/roquerou.log");
            console.setLevel(Level.ALL);
            file.setLevel(Level.ALL);
            LOG.addHandler(file);
            //LOG.addHandler(console);
            LOG.setUseParentHandlers(false);
            
            file.setFormatter(new SimpleFormatter());
        } catch (IOException io) {
            LOG.warning("O ficheiro hellologgin.xml não pode ser criado");
        }

        LOG.info("Abertura da Tela de Cidades");
        
        initComponents();

        

    }

    // *** Método novaCidade(): prepara os campos (limpa) ***
    public void novaCidade() {

        LOG.info("Insersão de registro");

        tabelaCidade.clearSelection();

        botaoEditar.setEnabled(false);
        botaoExcluir.setEnabled(false);

        campoNome.setEnabled(true);
        campoCEP.setEnabled(true);
        comboUF.setEnabled(true);

        botaoSalvar.setEnabled(true);
        botaoNovo.setEnabled(false);
    }

    // *** Método salvarCidade() ***
    public void salvarCidade() {

        Cidade cid = new Cidade();

        boolean nomeCidade = cid.validaNomeCidade(campoNome.getText());
        boolean cepCidade = cid.validaCepCidade(campoCEP.getText());

        campoNome.setBackground(Color.white);
        campoCEP.setBackground(Color.white);

        // Se todos os campos estiverem OK...
        if (nomeCidade == true && cepCidade == true) {

            // Quando for uma nova cidade (o campo ID vai estar vazio)
            if ("".equals(campoId.getText())) {
                cid.setNome(campoNome.getText());
                cid.setUf((String) comboUF.getSelectedItem());
                cid.setCep(Integer.parseInt(campoCEP.getText()));

            } // Quando for uma edição
            else {
                cid.setIdCidade(Integer.parseInt(campoId.getText()));
                cid.setNome(campoNome.getText());
                cid.setUf((String) comboUF.getSelectedItem());
                cid.setCep(Integer.parseInt(campoCEP.getText()));

            }
            String retorno = new CidadeDAO().salvar(cid);

            if (retorno == null) {
                LOG.info("Registro de id " + cid.getIdCidade() + " salvo com sucesso!");
                JOptionPane.showMessageDialog(null, "Salvo com sucesso!");

            } else {
                LOG.severe("Problemas ao salvar registro.");
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

            pesquisarCidade();

        } // Caso contrário (se algum dos campos não estiver validado)...
        else {

            LOG.severe("Erro no preenchimento dos campos ao salvar registro");

            if (cid.validaNomeCidade(campoNome.getText()) == false) {
                campoNome.setBackground(Color.pink);
            }

            if (cid.validaCepCidade(campoCEP.getText()) == false) {
                campoCEP.setBackground(Color.pink);
            }

            JOptionPane.showMessageDialog(null, "Verifique os campos em destaque", "Campo(s) inválidos", JOptionPane.WARNING_MESSAGE);
        }
    }

    // *** Método editarCidade(): Carrega os campos na tela de salvamento ***
    public void editarCidade() {

        botaoNovo.setEnabled(false);
        botaoSalvar.setEnabled(true);

        campoNome.setBackground(Color.white);
        campoCEP.setBackground(Color.white);

        int row = tabelaCidade.getSelectedRow();

        Object id = tabelaCidade.getValueAt(row, 0);
        Object nome = tabelaCidade.getValueAt(row, 1);
        Object cep = tabelaCidade.getValueAt(row, 2);
        Object uf = tabelaCidade.getValueAt(row, 3);

        Cidade cid = new Cidade();

        cid.setIdCidade(Integer.parseInt(id.toString()));
        cid.setNome(nome.toString());
        cid.setUf(uf.toString());
        cid.setCep(Integer.parseInt(cep.toString()));

        campoId.setText(cid.getIdCidade().toString());
        campoNome.setText(cid.getNome());
        campoCEP.setText(cid.getCep().toString());
        comboUF.setSelectedItem(cep);

        campoNome.setEnabled(true);
        campoCEP.setEnabled(true);
        comboUF.setEnabled(true);

        LOG.info("Edição do registro de id " + cid.getIdCidade());
    }

    // *** Método pesquisarCidades() ***
    public void pesquisarCidade() {

        LOG.info("Pesquisa por registros");

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

    // *** Método excluirCidade() ***
    public void excluirCidade() {

        LOG.warning("Exclusão de registro");

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
                LOG.info("Registro de id " + cid.getIdCidade() + " excluído com sucesso!");
                JOptionPane.showMessageDialog(null, "Cidade removida com sucesso!");
            } else {
                LOG.severe("Não foi possível excluir o registro");
                JOptionPane.showMessageDialog(null, "Erro! Não foi possível remover a cidade.");
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

        campoCEP.setEnabled(false);
        campoCEP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoCEPKeyTyped(evt);
            }
        });

        botaoNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/list_add.png"))); // NOI18N
        botaoNovo.setText("Novo");
        botaoNovo.setMaximumSize(new java.awt.Dimension(80, 27));
        botaoNovo.setMinimumSize(new java.awt.Dimension(80, 27));
        botaoNovo.setPreferredSize(new java.awt.Dimension(80, 27));
        botaoNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoNovoActionPerformed(evt);
            }
        });

        botaoSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/document_save_all.png"))); // NOI18N
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(campoId, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(botaoNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(botaoSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(comboUF, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(campoCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(campoNome))
                .addGap(12, 12, 12))
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

        botaoBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/document_preview.png"))); // NOI18N
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
        if (tabelaCidade.getColumnModel().getColumnCount() > 0) {
            tabelaCidade.getColumnModel().getColumn(0).setMaxWidth(60);
            tabelaCidade.getColumnModel().getColumn(1).setMinWidth(175);
            tabelaCidade.getColumnModel().getColumn(3).setMaxWidth(30);
        }

        botaoExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/list_remove.png"))); // NOI18N
        botaoExcluir.setText("Excluir");
        botaoExcluir.setEnabled(false);
        botaoExcluir.setMaximumSize(new java.awt.Dimension(80, 27));
        botaoExcluir.setMinimumSize(new java.awt.Dimension(80, 27));
        botaoExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoExcluirActionPerformed(evt);
            }
        });

        botaoEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit2.png"))); // NOI18N
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(botaoEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(comboPesquisaCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(campoPesquisaCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(botaoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        botaoFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/dialog_close.png"))); // NOI18N
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
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarActionPerformed
        salvarCidade();
    }//GEN-LAST:event_botaoSalvarActionPerformed

    private void botaoBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoBuscarActionPerformed
        pesquisarCidade();
    }//GEN-LAST:event_botaoBuscarActionPerformed

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void botaoNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoNovoActionPerformed
        novaCidade();
    }//GEN-LAST:event_botaoNovoActionPerformed

    private void tabelaCidadeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaCidadeMouseClicked
        botaoEditar.setEnabled(true);
        botaoExcluir.setEnabled(true);
    }//GEN-LAST:event_tabelaCidadeMouseClicked

    private void botaoExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoExcluirActionPerformed
        excluirCidade();
        pesquisarCidade();
    }//GEN-LAST:event_botaoExcluirActionPerformed
    private void botaoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditarActionPerformed
        editarCidade();
    }//GEN-LAST:event_botaoEditarActionPerformed

    private void campoCEPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoCEPKeyTyped
        // Permite apenas digitar números
        int k = evt.getKeyChar();
        if ((k > 47 && k < 58)) {
            if (campoCEP.getText().length() == 8) {
                evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            }
        } else {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
        }
    }//GEN-LAST:event_campoCEPKeyTyped


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
