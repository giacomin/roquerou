package telas;

import config.HibernateUtil;
import dao.CompraDAO;
import entidades.Compra;
import entidades.Fornecedor;
import entidades.Produto;
import entidades.Usuario;
import java.awt.Color;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import wservice.SMSService;

/**
 *
 * @author giacomin
 */
public class TelaCompra extends javax.swing.JInternalFrame {

    private static final Logger LOG = Logger.getLogger(TelaCompra.class.getName());

    /**
     * Creates new form TelaCompra
     */
    public TelaCompra() {

        try {
            Handler console = new ConsoleHandler();
            Handler file = new FileHandler("/tmp/roquerou.log");
            console.setLevel(Level.ALL);
            file.setLevel(Level.ALL);
            LOG.addHandler(file);
            LOG.addHandler(console);
            LOG.setUseParentHandlers(false);

            file.setFormatter(new SimpleFormatter());
        } catch (IOException io) {
            LOG.warning("O ficheiro hellologgin.xml não pode ser criado");
        }

        LOG.info("Abertura da tela de compra de produtos com fornecedores");

        initComponents();

    }

    // *** Método (provisório) popularProduto() ***
    public void popularProduto() {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Produto");
        List<Produto> dados_produto = query.list();
        Produto prod = new Produto();

        for (Produto produtorow : dados_produto) {

            prod.setIdProduto(produtorow.getIdProduto());
            prod.setNome(produtorow.getNome());
            comboProduto.addItem(produtorow); // Adiciona o objeto inteiro
        }

        session.getTransaction().commit();
    }

    // *** Método (provisório) popularFornecedor() ***
    public void popularFornecedor() {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Fornecedor");
        List<Fornecedor> dados_fornecedor = query.list();
        Fornecedor forn = new Fornecedor();

        for (Fornecedor fornecedorrow : dados_fornecedor) {

            forn.setIdFornecedor(fornecedorrow.getIdFornecedor());
            forn.setNome(fornecedorrow.getNome());
            comboFornecedor.addItem(fornecedorrow); // Adiciona o objeto inteiro
        }

        session.getTransaction().commit();
    }

    // *** Método novoCompra(): limpa campos e deixa apto a salvar ***
    public void novoCompra() {

        tabelaCompra.clearSelection();
        botaoEditar.setEnabled(false);
        botaoExcluir.setEnabled(false);
        comboProduto.setEnabled(true);
        comboFornecedor.setEnabled(true);
        campoQuantidade.setEnabled(true);
        campoCusto.setEnabled(true);
        campoData.setEnabled(true);
        botaoSalvar.setEnabled(true);
        botaoNovo.setEnabled(false);

        popularProduto();
        popularFornecedor();
    }

    // *** Método salvarCompra() ***
    public void salvarCompra() throws Exception {

        comboProduto.setBackground(Color.white);
        comboFornecedor.setBackground(Color.white);
        campoQuantidade.setBackground(Color.white);
        campoCusto.setBackground(Color.white);
        campoData.setBackground(Color.white);
        campoUsuario.setBackground(Color.white);

        Compra comp = new Compra();
        Produto prod = new Produto();
        Fornecedor forn = new Fornecedor();
        Usuario usua = new Usuario();
        Date data = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        boolean bnomeProduto = comp.validaProduto(comboProduto.getSelectedItem().toString());
        boolean bnomeFornecedor = comp.validaFornecedor(comboFornecedor.getSelectedItem().toString());
        boolean bquantidade = comp.validaQuantidade(campoQuantidade.getText());
        boolean bcusto = comp.validaCusto(campoCusto.getText());
        boolean bdata = comp.validaData(campoData.getText());

        // Se todos os campos estiverem OK...
        if (bnomeProduto == true && bnomeFornecedor == true && bquantidade == true && bcusto == true && bdata == true) {

            if ("".equals(campoId.getText())) {

                prod = (Produto) comboProduto.getSelectedItem(); // prod receberá todo o objeto Produto (com id e nome)
                forn = (Fornecedor) comboFornecedor.getSelectedItem(); // forn receberá todo o objeto Fornecedor (com id e nome)
                usua.setIdUsuario(1); // Aplicar o mesmo método de Produto e Fornecedor para o usuário quando a autenticação estiver implementada

                comp.setQuantidade(Integer.parseInt(campoQuantidade.getText()));
                comp.setCustoUnit(Float.parseFloat(campoCusto.getText()));

                //Data
                try {
                    comp.setData(sdf.parse(campoData.getText()));
                } catch (ParseException ex) {
                    Logger.getLogger(TelaCompra.class.getName()).log(Level.SEVERE, null, ex);
                }

                comp.setProduto(prod);
                comp.setFornecedor(forn);
                comp.setUsuario(usua);
            }

            String retorno = new CompraDAO().salvar(comp);

            if (retorno == null) {

                SMSService sms = new SMSService("aulafabricio", "aula");
                sms.enviarSMS("51", "992150368", "Compra\n" + "Nome: " + prod.getNome() + "\n Quantidade: " + comp.getQuantidade() + "\n Custo unit: " + comp.getCustoUnit());
                System.out.println("Compra\n" + "Nome: " + prod.getNome() + "\n Quantidade: " + comp.getQuantidade() + "\n Custo unit: " + comp.getCustoUnit());

                LOG.info("Compra realizada com sucesso!");
                JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
            } else {
                LOG.info("Compra falhou");
                JOptionPane.showMessageDialog(null, "Erro! Verifique os campos.");
            }

            campoId.setText("");
            comboProduto.removeAllItems();
            comboFornecedor.removeAllItems();
            campoQuantidade.setText("");
            campoCusto.setText("");
            campoData.setText("");

            comboProduto.setEnabled(false);
            comboFornecedor.setEnabled(false);
            campoQuantidade.setEnabled(false);
            campoCusto.setEnabled(false);
            campoData.setEnabled(false);

            botaoSalvar.setEnabled(false);
            botaoNovo.setEnabled(true);

            pesquisarCompra();

            
        } // Caso contrário (se a validação não estiver OK)...
        else {
            if (bnomeProduto == false) {
                comboProduto.setForeground(Color.red);
                comboProduto.setBackground(Color.red);

            }
            if (bnomeFornecedor == false) {
                comboFornecedor.setForeground(Color.pink);
            }

            if (bquantidade == false) {
                campoQuantidade.setBackground(Color.pink);
            }

            if (bcusto == false) {
                campoCusto.setBackground(Color.pink);
            }

            if (bdata == false) {
                campoData.setBackground(Color.pink);
            }

            JOptionPane.showMessageDialog(null, "Verifique os campos em destaque", "Campo(s) inválidos", JOptionPane.WARNING_MESSAGE);
            LOG.severe("Erro ao salvar compra");
        }

    }

    // *** Método pesquisarProdutos() ***
    public void pesquisarCompra() {

        comboProduto.setBackground(Color.white);
        comboFornecedor.setBackground(Color.white);
        campoQuantidade.setBackground(Color.white);
        campoCusto.setBackground(Color.white);
        campoData.setBackground(Color.white);

        botaoSalvar.setEnabled(false);
        botaoEditar.setEnabled(false);
        botaoExcluir.setEnabled(false);
        botaoNovo.setEnabled(true);

        campoQuantidade.setText("");
        campoCusto.setText("");
        campoData.setText("");

        comboProduto.setEnabled(false);
        comboFornecedor.setEnabled(false);
        campoQuantidade.setEnabled(false);
        campoCusto.setEnabled(false);
        campoData.setEnabled(false);

        CompraDAO pesquisa = new CompraDAO();
        Compra comp = new Compra();

        String comboPesquisa = comboPesquisaCompra.getSelectedItem().toString();
        String campoPesquisa = campoPesquisaCompra.getText();

        comp.setComboPesquisaCompra(comboPesquisa);
        comp.setCampoPesquisaCompra(campoPesquisa);

        pesquisa.listarCompra(tabelaCompra, comp);
    }

    /* NAO EXISTE MÉTODO EDITAR COMPRA
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
     campoProduto.setText(prod.getNome());
     campoFornecedor.setText(prod.getDescricao());
     campoQuantidade.setText(prod.getUnidade());
     campoCusto.setText(Float.toString(prod.getValorUnit()));
     //campoEstoque.setText(prod.getEstoque().toString());

     campoProduto.setEnabled(true);
     campoFornecedor.setEnabled(true);
     campoQuantidade.setEnabled(true);
     campoCusto.setEnabled(true);
     //campoEstoque.setEnabled(true);
     }
     */
    /* COMPRA COM FORNECEDORES PODE SER EXCLUÍDA?
     public void excluirProduto() {
     if (JOptionPane.showConfirmDialog(null, "Deseja mesmo remover o registro?", "Cuidado", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
     int row = tabelaCompra.getSelectedRow();

     Object id = tabelaCompra.getValueAt(row, 0);
     Object nome = tabelaCompra.getValueAt(row, 1);
     Object descricao = tabelaCompra.getValueAt(row, 2);
     Object unidade = tabelaCompra.getValueAt(row, 3);
     Object valorunit = tabelaCompra.getValueAt(row, 4);
     Object estoque = tabelaCompra.getValueAt(row, 5);

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
     } */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        campoId = new javax.swing.JTextField();
        botaoNovo = new javax.swing.JButton();
        botaoSalvar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        campoQuantidade = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        campoData = new javax.swing.JTextField();
        campoCusto = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        campoUsuario = new javax.swing.JTextField();
        comboProduto = new javax.swing.JComboBox();
        comboFornecedor = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        comboPesquisaCompra = new javax.swing.JComboBox();
        campoPesquisaCompra = new javax.swing.JTextField();
        botaoBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaCompra = new javax.swing.JTable();
        botaoExcluir = new javax.swing.JButton();
        botaoEditar = new javax.swing.JButton();
        botaoFechar = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setClosable(true);
        setTitle("Compra");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("Produto");

        jLabel2.setText("Quantidade");

        jLabel3.setText("Custo R$");

        jLabel4.setText("Código:");

        campoId.setEnabled(false);

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

        jLabel5.setText("Fornecedor");

        campoQuantidade.setEnabled(false);

        jLabel6.setText("Data");

        campoData.setEnabled(false);

        campoCusto.setEnabled(false);

        jLabel7.setText("Operador");

        campoUsuario.setEnabled(false);

        comboProduto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<nenhum>" }));
        comboProduto.setToolTipText("");
        comboProduto.setEnabled(false);

        comboFornecedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<nenhum>" }));
        comboFornecedor.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(campoCusto, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoData, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(comboFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(campoId, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(226, 275, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botaoNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jLabel5)
                    .addComponent(comboProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(campoQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(campoCusto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(campoData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(campoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        comboPesquisaCompra.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Produto", "Fornecedor", "Vendedor" }));

        botaoBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/document_preview.png"))); // NOI18N
        botaoBuscar.setText("Buscar");
        botaoBuscar.setMaximumSize(new java.awt.Dimension(80, 27));
        botaoBuscar.setMinimumSize(new java.awt.Dimension(80, 27));
        botaoBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoBuscarActionPerformed(evt);
            }
        });

        tabelaCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Cód.", "Produto", "Fornecedor", "Qtde", "Custo", "Data", "Vendedor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaCompra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaCompraMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaCompra);
        if (tabelaCompra.getColumnModel().getColumnCount() > 0) {
            tabelaCompra.getColumnModel().getColumn(0).setMaxWidth(60);
            tabelaCompra.getColumnModel().getColumn(1).setMinWidth(150);
            tabelaCompra.getColumnModel().getColumn(2).setMinWidth(250);
            tabelaCompra.getColumnModel().getColumn(3).setMaxWidth(75);
            tabelaCompra.getColumnModel().getColumn(4).setMaxWidth(75);
            tabelaCompra.getColumnModel().getColumn(5).setMinWidth(65);
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
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(comboPesquisaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoPesquisaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(comboPesquisaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoPesquisaCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoFechar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarActionPerformed
        try {
            salvarCompra();
        } catch (Exception ex) {
            Logger.getLogger(TelaCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botaoSalvarActionPerformed

    private void botaoBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoBuscarActionPerformed
        pesquisarCompra();
    }//GEN-LAST:event_botaoBuscarActionPerformed

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void botaoNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoNovoActionPerformed
        novoCompra();
    }//GEN-LAST:event_botaoNovoActionPerformed

    private void tabelaCompraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaCompraMouseClicked
        botaoEditar.setEnabled(false); //Alterar para true se for editar
        botaoExcluir.setEnabled(false); // Alterar para true se for excluir
    }//GEN-LAST:event_tabelaCompraMouseClicked

    private void botaoExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoExcluirActionPerformed
        //excluirProduto();
        //pesquisarProduto();
    }//GEN-LAST:event_botaoExcluirActionPerformed
    private void botaoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditarActionPerformed
        //editarCompra();
    }//GEN-LAST:event_botaoEditarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoBuscar;
    private javax.swing.JButton botaoEditar;
    private javax.swing.JButton botaoExcluir;
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton botaoNovo;
    private javax.swing.JButton botaoSalvar;
    private javax.swing.JFormattedTextField campoCusto;
    private javax.swing.JTextField campoData;
    private javax.swing.JTextField campoId;
    private javax.swing.JTextField campoPesquisaCompra;
    private javax.swing.JTextField campoQuantidade;
    private javax.swing.JTextField campoUsuario;
    private javax.swing.JComboBox comboFornecedor;
    private javax.swing.JComboBox comboPesquisaCompra;
    private javax.swing.JComboBox comboProduto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelaCompra;
    // End of variables declaration//GEN-END:variables
}
