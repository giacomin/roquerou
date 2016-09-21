/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import config.HibernateUtil;
import dao.ClienteDAO;
import dao.FornecedorDAO;
import entidades.Cidade;
import entidades.Cliente;
import entidades.Fornecedor;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Eduardo
 */
public class TelaFornecedor extends javax.swing.JInternalFrame {

    /**
     * Creates new form TelaFornecedor
     */
    public TelaFornecedor() {
        initComponents();
        popularCombo();
        setarLabels();
    }

    public void setarLabels() {

        jbEmail.setText("");
        jbEmail.setForeground(Color.red);

        jbNome.setText("");
        jbNome.setForeground(Color.red);

        jbFone.setText("");
        jbFone.setForeground(Color.red);

        jbEndereco.setText("");
        jbEndereco.setForeground(Color.red);

        jbBairro.setText("");
        jbBairro.setForeground(Color.red);

        jbCidade.setText("");
        jbCidade.setForeground(Color.red);

        jbCnpj.setText("");
        jbCnpj.setForeground(Color.red);

    }

    public void habilitarCampos() {

        tabelaForn.clearSelection();
        campoNomeForn.requestFocus();

        campoNomeForn.setEnabled(true);
        campoFoneForn.setEnabled(true);
        comboCidadeForn.setEnabled(true);
        campoBairroForn.setEnabled(true);
        campoEmailForn.setEnabled(true);
        campoEnderecoForn.setEnabled(true);
        campoCnpjForn.setEnabled(true);

        botaoEditarForn.setEnabled(false);
        botaoExcluirForn.setEnabled(false);
        botaoNovoForn.setEnabled(false);
    }

    public void bloquearCampos() {

        campoNomeForn.setEnabled(false);
        campoFoneForn.setEnabled(false);
        comboCidadeForn.setEnabled(false);
        campoBairroForn.setEnabled(false);
        campoEmailForn.setEnabled(false);
        campoEnderecoForn.setEnabled(false);
        campoCnpjForn.setEnabled(false);

        botaoSalvarForn.setEnabled(false);
        botaoNovoForn.setEnabled(true);
        botaoCancelarForn.setEnabled(false);
    }

    public void zerarCampos() {
        campoCodForn.setText("");
        campoNomeForn.setText("");
        campoFoneForn.setText("");
        campoBairroForn.setText("");
        campoEmailForn.setText("");
        campoEnderecoForn.setText("");
        campoCnpjForn.setText("");
        comboCidadeForn.setSelectedIndex(0);
    }

    // método habilitarSalvar()
    public void habilitarSalvar() {
        botaoSalvarForn.setEnabled(true);
        botaoCancelarForn.setEnabled(true);

    }

    public void ValidaNumero(JTextField Numero) {
        long valor;
        if (Numero.getText().length() != 0) {
            try {
                valor = Long.parseLong(Numero.getText());
            } catch (NumberFormatException ex) {

                jbFone.setText("Apenas números");
                Numero.grabFocus();
            }
        }

    }

    public void popularCombo() {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Cidade");
        List<Cidade> dados_cidade = query.list();
        Cidade cid = new Cidade();

        for (Cidade cidaderow : dados_cidade) {

            cid.setIdCidade(cidaderow.getIdCidade());
            cid.setNome(cidaderow.getNome());
            comboCidadeForn.addItem(cidaderow.getNome());
        }

        session.getTransaction().commit();
    }

    public void excluirFornecedor() {

        if (JOptionPane.showConfirmDialog(null, "Deseja mesmo remover o registro?", "Cuidado", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            Cidade cid = new Cidade();
            Fornecedor forn = new Fornecedor();

            cid.setIdCidade((int) tabelaForn.getValueAt(tabelaForn.getSelectedRow(), 6));
            forn.setIdFornecedor((int) tabelaForn.getValueAt(tabelaForn.getSelectedRow(), 0));
            forn.setCidade(cid);
            forn.setNome((String) tabelaForn.getValueAt(tabelaForn.getSelectedRow(), 1));
            forn.setFone((String) tabelaForn.getValueAt(tabelaForn.getSelectedRow(), 2));
            forn.setEndereco((String) tabelaForn.getValueAt(tabelaForn.getSelectedRow(), 4));

            String retorno = new FornecedorDAO().deletar(forn);

            buscarFornecedor();

            if (retorno == null) {
                System.out.println("Fornecedor Excluído");
            } else {
                System.out.println("ERRO na EXCLUSÃO");
            }

        } else {

        }
    }

    public void salvarFornecedor() {

        Fornecedor forn = new Fornecedor();
        Cidade cid = new Cidade();

        String nome = campoNomeForn.getText().trim();
        String fone = campoFoneForn.getText().trim();
        String email = campoEmailForn.getText().trim();
        String endereco = campoEnderecoForn.getText().trim();
        String bairro = campoBairroForn.getText().trim();
        //int cnpj = Integer.parseInt(campoCnpjForn.getText());

        if (nome == null || nome.isEmpty()) {
            jbNome.setText("Digite o Nome");
        } else {
            jbNome.setText("");
            if (email == null || email.isEmpty()) {
                jbEmail.setText("Digite o E-mail");
            } else {
                jbEmail.setText("");
                if (fone.length() < 10 || fone == null || fone.isEmpty()) {
                    jbFone.setText("Telefone Inválido");
                } else {
                    jbFone.setText("");
                    if (bairro == null || bairro.isEmpty()) {
                        jbBairro.setText("Digite o Bairro");
                    } else {
                        jbBairro.setText("");
                        if (endereco == null || endereco.isEmpty()) {
                            jbEndereco.setText("Digite o Endereço");
                        } else {
                            jbEndereco.setText("");

                            forn.setNome(campoNomeForn.getText());
                            forn.setFone(campoFoneForn.getText());
                            forn.setEmail(campoEmailForn.getText());
                            forn.setEndereco(campoEnderecoForn.getText());
                            forn.setBairro(campoBairroForn.getText());
                            forn.setCnpj(Integer.parseInt(campoCnpjForn.getText()));
                            cid.setIdCidade(comboCidadeForn.getSelectedIndex());
                            if (comboCidadeForn.getSelectedIndex() == 0) {
                                jbCidade.setText("Selecione a Cidade");
                            } else {
                                jbCidade.setText("");

                                if ("".equals(campoCodForn.getText())) {

                                    forn.setCidade(cid);

                                    String retorno = new FornecedorDAO().salvar(forn);

                                    if (retorno == null) {
                                        JOptionPane.showMessageDialog(null, "Fornecedor Adicionado!");
                                        zerarCampos();
                                        bloquearCampos();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Erro ao adicionar fornecedor!");
                                    }

                                } else {

                                    forn.setIdFornecedor(Integer.parseInt(campoCodForn.getText()));

                                    forn.setCidade(cid);

                                    String retorno = new FornecedorDAO().salvar(forn);

                                    if (retorno == null) {
                                        JOptionPane.showMessageDialog(null, "Fornecedor Adicionado!");
                                        zerarCampos();
                                        bloquearCampos();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Erro ao adicionar fornecedor!");
                                    }

                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public void buscarFornecedor() {

        FornecedorDAO pesquisa = new FornecedorDAO();
        Fornecedor forn = new Fornecedor();

        String campoPesquisa = campoPesquisaFornecedor.getText();

        forn.setCampoPesquisaFornecedor(campoPesquisa);

        pesquisa.listarFornecedor(tabelaForn, forn);

    }

    /*
    public void editarFornecedor() {

        Fornecedor forn = new Fornecedor();
        Cidade cid = new Cidade();

        forn.setIdFornecedor((Integer) tabelaForn.getValueAt(tabelaForn.getSelectedRow(), 0));
        forn.setNome((String) tabelaForn.getValueAt(tabelaForn.getSelectedRow(), 1));
        forn.setFone((String) tabelaForn.getValueAt(tabelaForn.getSelectedRow(), 2));
        forn.setEmail((String) tabelaForn.getValueAt(tabelaForn.getSelectedRow(), 3));
        forn.setEndereco((String) tabelaForn.getValueAt(tabelaForn.getSelectedRow(), 4));
        forn.setBairro((String) tabelaForn.getValueAt(tabelaForn.getSelectedRow(), 5));
        forn.setIdCidade((Integer) tabelaForn.getValueAt(tabelaForn.getSelectedRow(), 6));
        forn.setCidade(cid);

        // return cli;
    }
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpForn1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        campoCodForn = new javax.swing.JTextField();
        campoNomeForn = new javax.swing.JTextField();
        campoEmailForn = new javax.swing.JTextField();
        campoEnderecoForn = new javax.swing.JTextField();
        campoBairroForn = new javax.swing.JTextField();
        comboCidadeForn = new javax.swing.JComboBox<>();
        botaoSalvarForn = new javax.swing.JButton();
        botaoNovoForn = new javax.swing.JButton();
        jbEmail = new javax.swing.JLabel();
        jbBairro = new javax.swing.JLabel();
        jbEndereco = new javax.swing.JLabel();
        jbCidade = new javax.swing.JLabel();
        jbFone = new javax.swing.JLabel();
        jbNome = new javax.swing.JLabel();
        campoFoneForn = new javax.swing.JTextField();
        botaoCancelarForn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        campoCnpjForn = new javax.swing.JTextField();
        jbCnpj = new javax.swing.JLabel();
        jpForn2 = new javax.swing.JPanel();
        campoPesquisaFornecedor = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaForn = new javax.swing.JTable();
        botaoEditarForn = new javax.swing.JButton();
        botaoExcluirForn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setClosable(true);
        setTitle("Fornecedor");
        setPreferredSize(new java.awt.Dimension(613, 562));

        jpForn1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("Código:");

        jLabel2.setText("Nome:");

        jLabel3.setText("Fone:");

        jLabel4.setText("Email:");

        jLabel5.setText("Endereço:");

        jLabel6.setText("Bairro:");

        jLabel7.setText("Cidade:");

        campoCodForn.setEnabled(false);
        campoCodForn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoCodFornActionPerformed(evt);
            }
        });

        campoNomeForn.setEnabled(false);
        campoNomeForn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoNomeFornActionPerformed(evt);
            }
        });

        campoEmailForn.setEnabled(false);
        campoEmailForn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoEmailFornFocusLost(evt);
            }
        });

        campoEnderecoForn.setEnabled(false);

        campoBairroForn.setEnabled(false);

        comboCidadeForn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "" }));
        comboCidadeForn.setEnabled(false);
        comboCidadeForn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCidadeFornActionPerformed(evt);
            }
        });

        botaoSalvarForn.setText("Salvar");
        botaoSalvarForn.setEnabled(false);
        botaoSalvarForn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSalvarFornActionPerformed(evt);
            }
        });

        botaoNovoForn.setText("Novo");
        botaoNovoForn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoNovoFornActionPerformed(evt);
            }
        });

        jbEmail.setText("email invalido");
        jbEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jbEmailFocusLost(evt);
            }
        });

        jbBairro.setText("bairro invalido");

        jbEndereco.setText("endereco invalido");

        jbCidade.setText("cidade invalido");

        jbFone.setText("fone invalido");

        jbNome.setText("nome invalido");

        campoFoneForn.setEnabled(false);
        campoFoneForn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoFoneFornFocusLost(evt);
            }
        });
        campoFoneForn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoFoneFornActionPerformed(evt);
            }
        });

        botaoCancelarForn.setText("Cancelar");
        botaoCancelarForn.setEnabled(false);
        botaoCancelarForn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCancelarFornActionPerformed(evt);
            }
        });

        jLabel8.setText("CNPJ:");

        campoCnpjForn.setEnabled(false);

        jbCnpj.setText("CNPJ inválido");

        javax.swing.GroupLayout jpForn1Layout = new javax.swing.GroupLayout(jpForn1);
        jpForn1.setLayout(jpForn1Layout);
        jpForn1Layout.setHorizontalGroup(
            jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpForn1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpForn1Layout.createSequentialGroup()
                        .addGroup(jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbNome)
                            .addComponent(campoNomeForn, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpForn1Layout.createSequentialGroup()
                                .addGroup(jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(campoBairroForn)
                                    .addComponent(campoEnderecoForn, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                                    .addComponent(campoEmailForn))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpForn1Layout.createSequentialGroup()
                                        .addGroup(jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel7))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(comboCidadeForn, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jbFone)
                                            .addComponent(campoFoneForn, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jbCidade)))
                                    .addGroup(jpForn1Layout.createSequentialGroup()
                                        .addComponent(botaoNovoForn, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(botaoSalvarForn)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(botaoCancelarForn))))
                            .addComponent(jbEndereco)
                            .addComponent(jbBairro)
                            .addComponent(jbEmail)
                            .addComponent(campoCnpjForn, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbCnpj)))
                    .addComponent(jLabel4)
                    .addGroup(jpForn1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(campoCodForn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jpForn1Layout.setVerticalGroup(
            jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpForn1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(campoCodForn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoNomeForn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(campoEmailForn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoFoneForn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbEmail)
                    .addComponent(jbFone))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboCidadeForn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(campoBairroForn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbBairro)
                    .addComponent(jbCidade))
                .addGroup(jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoEnderecoForn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbEndereco)
                .addGroup(jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpForn1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botaoNovoForn)
                            .addComponent(botaoSalvarForn)
                            .addComponent(botaoCancelarForn))
                        .addGap(21, 21, 21))
                    .addGroup(jpForn1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jpForn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(campoCnpjForn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCnpj)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jpForn2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tabelaForn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Fone", "Email", "Endereço", "Bairro", "Cidade", "Cnpj"
            }
        ));
        tabelaForn.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tabelaFornFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaForn);

        botaoEditarForn.setText("Editar");
        botaoEditarForn.setEnabled(false);
        botaoEditarForn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarFornActionPerformed(evt);
            }
        });

        botaoExcluirForn.setText("Excluir");
        botaoExcluirForn.setEnabled(false);
        botaoExcluirForn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoExcluirFornActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpForn2Layout = new javax.swing.GroupLayout(jpForn2);
        jpForn2.setLayout(jpForn2Layout);
        jpForn2Layout.setHorizontalGroup(
            jpForn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpForn2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpForn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpForn2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botaoEditarForn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botaoExcluirForn))
                    .addGroup(jpForn2Layout.createSequentialGroup()
                        .addGroup(jpForn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpForn2Layout.createSequentialGroup()
                                .addComponent(campoPesquisaFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 10, Short.MAX_VALUE))))
        );
        jpForn2Layout.setVerticalGroup(
            jpForn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpForn2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpForn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoPesquisaFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpForn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoExcluirForn)
                    .addComponent(botaoEditarForn)))
        );

        jButton2.setText("Fechar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jpForn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jpForn2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpForn1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpForn2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void campoCodFornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoCodFornActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoCodFornActionPerformed

    private void campoNomeFornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoNomeFornActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoNomeFornActionPerformed

    private void campoEmailFornFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoEmailFornFocusLost

        if (campoEmailForn.isEnabled()) {

            if ((campoEmailForn.getText().contains("@"))
                    && (campoEmailForn.getText().contains("."))
                    && (!campoEmailForn.getText().contains(" "))) {

                String usuario = new String(campoEmailForn.getText().substring(0,
                        campoEmailForn.getText().lastIndexOf('@')));

                String dominio = new String(campoEmailForn.getText().substring(campoEmailForn.getText().lastIndexOf('@') + 1, campoEmailForn.getText().length()));

                if ((usuario.length() >= 1) && (!usuario.contains("@"))
                        && (dominio.contains(".")) && (!dominio.contains("@")) && (dominio.indexOf(".")
                        >= 1) && (dominio.lastIndexOf(".") < dominio.length() - 1)) {

                    jbEmail.setText("");

                } else {

                    jbEmail.setText("E-mail Inválido");

                    campoEmailForn.requestFocus();

                }

            } else {

                jbEmail.setText("E-mail Inválido");

                campoEmailForn.requestFocus();

            }

        } else {
            jbEmail.setText("");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_campoEmailFornFocusLost

    private void comboCidadeFornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCidadeFornActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_comboCidadeFornActionPerformed

    private void botaoSalvarFornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarFornActionPerformed

        salvarFornecedor();

        // TODO add your handling code here:
    }//GEN-LAST:event_botaoSalvarFornActionPerformed

    private void botaoNovoFornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoNovoFornActionPerformed

        habilitarCampos();
        habilitarSalvar();
        zerarCampos();
        // TODO add your handling code here:
    }//GEN-LAST:event_botaoNovoFornActionPerformed

    private void jbEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jbEmailFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jbEmailFocusLost

    private void campoFoneFornFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoFoneFornFocusLost

        ValidaNumero(campoFoneForn);
        // TODO add your handling code here:
    }//GEN-LAST:event_campoFoneFornFocusLost

    private void campoFoneFornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoFoneFornActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoFoneFornActionPerformed

    private void botaoCancelarFornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCancelarFornActionPerformed

        setarLabels();
        zerarCampos();
        bloquearCampos();

        // TODO add your handling code here:
    }//GEN-LAST:event_botaoCancelarFornActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        buscarFornecedor();

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tabelaFornFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabelaFornFocusGained

        this.tabelaForn.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                //altera os botoes para ativados somente se houver linha selecionada
                botaoExcluirForn.setEnabled(!lsm.isSelectionEmpty());
                botaoEditarForn.setEnabled(!lsm.isSelectionEmpty());
            }
        });

        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaFornFocusGained

    private void botaoEditarFornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditarFornActionPerformed

        botaoNovoForn.setEnabled(false);
        botaoSalvarForn.setEnabled(true);
        botaoCancelarForn.setEnabled(true);

        int row = tabelaForn.getSelectedRow();

        Object id = tabelaForn.getValueAt(row, 0);
        Object nome = tabelaForn.getValueAt(row, 1);
        Object fone = tabelaForn.getValueAt(row, 2);
        Object email = tabelaForn.getValueAt(row, 3);
        Object endereco = tabelaForn.getValueAt(row, 4);
        Object bairro = tabelaForn.getValueAt(row, 5);
        Cidade cid = new Cidade();
        cid.setIdCidade((Integer) tabelaForn.getValueAt(row, 6));
        Object cnpj = tabelaForn.getValueAt(row, 7);

        Fornecedor forn = new Fornecedor();

        forn.setIdFornecedor(Integer.parseInt(id.toString()));
        forn.setNome(nome.toString());
        forn.setFone(fone.toString());
        forn.setEmail(email.toString());
        forn.setEndereco(endereco.toString());
        forn.setBairro(bairro.toString());
        forn.setCnpj((int) cnpj);
        forn.setCidade(cid);

        campoCodForn.setText(forn.getIdFornecedor().toString());
        campoNomeForn.setText(forn.getNome());
        campoFoneForn.setText(forn.getFone());
        campoEmailForn.setText(forn.getEmail());
        campoEnderecoForn.setText(forn.getEndereco());
        campoBairroForn.setText(forn.getBairro());
        campoCnpjForn.setText(String.valueOf(forn.getCnpj()));
        comboCidadeForn.setSelectedIndex(forn.getCidade().getIdCidade());

        campoNomeForn.setEnabled(true);
        campoFoneForn.setEnabled(true);
        campoEmailForn.setEnabled(true);
        campoEnderecoForn.setEnabled(true);
        campoBairroForn.setEnabled(true);
        campoCnpjForn.setEnabled(true);
        comboCidadeForn.setEnabled(true);

    }//GEN-LAST:event_botaoEditarFornActionPerformed

    private void botaoExcluirFornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoExcluirFornActionPerformed

        excluirFornecedor();

    }//GEN-LAST:event_botaoExcluirFornActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoCancelarForn;
    private javax.swing.JButton botaoEditarForn;
    private javax.swing.JButton botaoExcluirForn;
    private javax.swing.JButton botaoNovoForn;
    private javax.swing.JButton botaoSalvarForn;
    private javax.swing.JTextField campoBairroForn;
    private javax.swing.JTextField campoCnpjForn;
    private javax.swing.JTextField campoCodForn;
    private javax.swing.JTextField campoEmailForn;
    private javax.swing.JTextField campoEnderecoForn;
    private javax.swing.JTextField campoFoneForn;
    private javax.swing.JTextField campoNomeForn;
    private javax.swing.JTextField campoPesquisaFornecedor;
    private javax.swing.JComboBox<String> comboCidadeForn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jbBairro;
    private javax.swing.JLabel jbCidade;
    private javax.swing.JLabel jbCnpj;
    private javax.swing.JLabel jbEmail;
    private javax.swing.JLabel jbEndereco;
    private javax.swing.JLabel jbFone;
    private javax.swing.JLabel jbNome;
    private javax.swing.JPanel jpForn1;
    private javax.swing.JPanel jpForn2;
    private javax.swing.JTable tabelaForn;
    // End of variables declaration//GEN-END:variables
}
