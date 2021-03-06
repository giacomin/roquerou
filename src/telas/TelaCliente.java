/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import chat.Email;
import config.HibernateUtil;
import dao.ClienteDAO;
import dao.NivelDAO;
import entidades.Cidade;
import entidades.Cliente;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.commons.mail.EmailException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Eduardo
 */
public class TelaCliente extends javax.swing.JInternalFrame {

    private static final Logger LOG = Logger.getLogger(TelaCliente.class.getName());

    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {

        try {
            Handler console = new ConsoleHandler();
            Handler file = new FileHandler("/tmp/roquerou.log");
            console.setLevel(Level.ALL);
            file.setLevel(Level.ALL);
            file.setFormatter(new SimpleFormatter());
            LOG.addHandler(file);
            LOG.addHandler(console);
            LOG.setUseParentHandlers(false);
        } catch (IOException io) {
            LOG.warning("O ficheiro hellologgin.xml não pode ser criado");
        }

        initComponents();
        NivelDAO nd = new NivelDAO();
        if (nd.buscar() == 3) {
            botaoNovoCliente.setEnabled(false);
            buscarcli.setEnabled(false);
            tabelaCliente.setEnabled(false);
            popularCombo();
            setarLabels();

            LOG.info("Abertura da Tela de Clientes");
        } else {
            popularCombo();
            setarLabels();

            LOG.info("Abertura da Tela de Clientes");
        }
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

    }

    public void habilitarCampos() {

        tabelaCliente.clearSelection();
        campoNomeCliente.requestFocus();

        campoNomeCliente.setEnabled(true);
        campoFoneCliente.setEnabled(true);
        comboCidadeCliente.setEnabled(true);
        campoBairroCliente.setEnabled(true);
        campoEmailCliente.setEnabled(true);
        campoEnderecoCliente.setEnabled(true);

        botaoEditarCliente.setEnabled(false);
        botaoExcluirCliente.setEnabled(false);
        botaoNovoCliente.setEnabled(false);
    }

    public void bloquearCampos() {

        campoNomeCliente.setEnabled(false);
        campoFoneCliente.setEnabled(false);
        comboCidadeCliente.setEnabled(false);
        campoBairroCliente.setEnabled(false);
        campoEmailCliente.setEnabled(false);
        campoEnderecoCliente.setEnabled(false);

        botaoSalvarCliente.setEnabled(false);
        botaoNovoCliente.setEnabled(true);
        //botaoCancelarCliente.setEnabled(false);
    }

    public void zerarCampos() {
        campoCodCliente.setText("");
        campoNomeCliente.setText("");
        campoFoneCliente.setText("");
        campoBairroCliente.setText("");
        campoEmailCliente.setText("");
        campoEnderecoCliente.setText("");
        comboCidadeCliente.setSelectedIndex(0);
    }

    // método habilitarSalvar()
    public void habilitarSalvar() {
        botaoSalvarCliente.setEnabled(true);
        //botaoCancelarCliente.setEnabled(true);

    }

    //método para permitir apenas números no campo Fone
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
            comboCidadeCliente.addItem(cidaderow.getNome());
        }

        session.getTransaction().commit();
    }

    public void excluirCliente() {

        if (JOptionPane.showConfirmDialog(null, "Deseja mesmo remover o registro?", "Cuidado", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Cidade cid = new Cidade();
            Cliente cli = new Cliente();

            cid.setIdCidade((int) tabelaCliente.getValueAt(tabelaCliente.getSelectedRow(), 6));
            cli.setIdCliente((int) tabelaCliente.getValueAt(tabelaCliente.getSelectedRow(), 0));
            cli.setCidade(cid);
            cli.setNome((String) tabelaCliente.getValueAt(tabelaCliente.getSelectedRow(), 1));

            String retorno = new ClienteDAO().deletar(cli);

            buscarCliente();

            if (retorno == null) {
                LOG.info("Registro de id " + cli.getIdCliente() + " excluído com sucesso.");
                System.out.println("Cliente Excluído");
            } else {
                LOG.info("Problemas ao excluir registro");
                System.out.println("ERRO na EXCLUSÃO");
            }

        } else {

        }
    }

    public void salvarCliente() throws EmailException {

        Cliente cli = new Cliente();
        Cidade cid = new Cidade();

        String nome = campoNomeCliente.getText().trim();
        String fone = campoFoneCliente.getText().trim();
        String email = campoEmailCliente.getText().trim();
        String endereco = campoEnderecoCliente.getText().trim();
        String bairro = campoBairroCliente.getText().trim();

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

                            cli.setNome(campoNomeCliente.getText());
                            cli.setFone(campoFoneCliente.getText());
                            cli.setEmail(campoEmailCliente.getText());
                            cli.setEndereco(campoEnderecoCliente.getText());
                            cli.setBairro(campoBairroCliente.getText());
                            cid.setIdCidade(comboCidadeCliente.getSelectedIndex());
                            if (comboCidadeCliente.getSelectedIndex() == 0) {
                                jbCidade.setText("Selecione a Cidade");
                            } else {
                                jbCidade.setText("");

                                if ("".equals(campoCodCliente.getText())) {

                                    cli.setCidade(cid);

                                    String retorno = new ClienteDAO().salvar(cli);

                                    if (retorno == null) {

                                        LOG.info("Registro de id " + cli.getIdCliente() + " salvo com sucesso!");
                                        Email em = new Email();
                                        //em.sendEmail(cli.getNome(), cli.getFone(), cli.getEmail());
                                        JOptionPane.showMessageDialog(null, "Cliente Adicionado!");
                                        zerarCampos();
                                        bloquearCampos();

                                    } else {
                                        LOG.info("Problemas ao adicionar registro");
                                        JOptionPane.showMessageDialog(null, "Erro ao adicionar cliente!");
                                    }

                                } else {

                                    cli.setIdCliente(Integer.parseInt(campoCodCliente.getText()));

                                    cli.setCidade(cid);

                                    String retorno = new ClienteDAO().salvar(cli);

                                    if (retorno == null) {
                                        LOG.info("Registro de id " + cli.getIdCliente() + " salvo com sucesso!");
                                        JOptionPane.showMessageDialog(null, "Cliente Adicionado!");
                                        zerarCampos();
                                        bloquearCampos();
                                    } else {
                                        LOG.info("Problemas ao adicionar registro");
                                        JOptionPane.showMessageDialog(null, "Erro ao adicionar cliente!");
                                    }

                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public void buscarCliente() {

        LOG.info("Pesquisa por registros");

        ClienteDAO pesquisa = new ClienteDAO();
        Cliente cli = new Cliente();

        String campoPesquisa = campoPesquisaCliente.getText();

        cli.setCampoPesquisaCliente(campoPesquisa);

        pesquisa.listarCliente(tabelaCliente, cli);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jpCliente1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        campoCodCliente = new javax.swing.JTextField();
        campoNomeCliente = new javax.swing.JTextField();
        campoEmailCliente = new javax.swing.JTextField();
        campoEnderecoCliente = new javax.swing.JTextField();
        campoBairroCliente = new javax.swing.JTextField();
        comboCidadeCliente = new javax.swing.JComboBox<String>();
        botaoSalvarCliente = new javax.swing.JButton();
        botaoNovoCliente = new javax.swing.JButton();
        jbEmail = new javax.swing.JLabel();
        jbBairro = new javax.swing.JLabel();
        jbEndereco = new javax.swing.JLabel();
        jbCidade = new javax.swing.JLabel();
        jbFone = new javax.swing.JLabel();
        jbNome = new javax.swing.JLabel();
        campoFoneCliente = new javax.swing.JTextField();
        jpCliente2 = new javax.swing.JPanel();
        campoPesquisaCliente = new javax.swing.JTextField();
        buscarcli = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaCliente = new javax.swing.JTable();
        botaoEditarCliente = new javax.swing.JButton();
        botaoExcluirCliente = new javax.swing.JButton();
        botaoFechar = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setClosable(true);
        setTitle("Cliente");
        setPreferredSize(new java.awt.Dimension(613, 562));

        jpCliente1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("Código:");

        jLabel2.setText("Nome:");

        jLabel3.setText("Fone:");

        jLabel4.setText("Email:");

        jLabel5.setText("Endereço:");

        jLabel6.setText("Bairro:");

        jLabel7.setText("Cidade:");

        campoCodCliente.setEnabled(false);
        campoCodCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoCodClienteActionPerformed(evt);
            }
        });

        campoNomeCliente.setEnabled(false);
        campoNomeCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoNomeClienteActionPerformed(evt);
            }
        });

        campoEmailCliente.setEnabled(false);
        campoEmailCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoEmailClienteFocusLost(evt);
            }
        });

        campoEnderecoCliente.setEnabled(false);

        campoBairroCliente.setEnabled(false);

        comboCidadeCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
        comboCidadeCliente.setEnabled(false);
        comboCidadeCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCidadeClienteActionPerformed(evt);
            }
        });

        botaoSalvarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/document_save_all.png"))); // NOI18N
        botaoSalvarCliente.setText("Salvar");
        botaoSalvarCliente.setEnabled(false);
        botaoSalvarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSalvarClienteActionPerformed(evt);
            }
        });

        botaoNovoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/list_add.png"))); // NOI18N
        botaoNovoCliente.setText("Novo");
        botaoNovoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoNovoClienteActionPerformed(evt);
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

        campoFoneCliente.setEnabled(false);
        campoFoneCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoFoneClienteActionPerformed(evt);
            }
        });
        campoFoneCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoFoneClienteFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jpCliente1Layout = new javax.swing.GroupLayout(jpCliente1);
        jpCliente1.setLayout(jpCliente1Layout);
        jpCliente1Layout.setHorizontalGroup(
            jpCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCliente1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpCliente1Layout.createSequentialGroup()
                        .addGroup(jpCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbNome)
                            .addComponent(jbBairro)
                            .addComponent(jbEmail)
                            .addComponent(campoNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpCliente1Layout.createSequentialGroup()
                                .addGroup(jpCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(campoBairroCliente)
                                    .addComponent(campoEnderecoCliente)
                                    .addComponent(campoEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jpCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jpCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(campoFoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jbCidade)
                                    .addComponent(comboCidadeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jbFone)))
                            .addComponent(jbEndereco)))
                    .addComponent(jLabel4)
                    .addGroup(jpCliente1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(campoCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(168, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpCliente1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botaoNovoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoSalvarCliente)
                .addContainerGap())
        );
        jpCliente1Layout.setVerticalGroup(
            jpCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCliente1Layout.createSequentialGroup()
                .addGroup(jpCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(campoCodCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jpCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(campoEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoFoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbFone, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboCidadeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(campoBairroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbBairro)
                    .addComponent(jbCidade))
                .addGroup(jpCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoEnderecoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbEndereco)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoNovoCliente)
                    .addComponent(botaoSalvarCliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpCliente2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        buscarcli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/document_preview.png"))); // NOI18N
        buscarcli.setText("Buscar");
        buscarcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarcliActionPerformed(evt);
            }
        });

        tabelaCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Cod.", "Nome", "Fone", "Email", "Endereço", "Bairro", "Cidade"
            }
        ));
        tabelaCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tabelaClienteFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaCliente);
        if (tabelaCliente.getColumnModel().getColumnCount() > 0) {
            tabelaCliente.getColumnModel().getColumn(0).setMinWidth(40);
            tabelaCliente.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabelaCliente.getColumnModel().getColumn(0).setMaxWidth(40);
            tabelaCliente.getColumnModel().getColumn(1).setMinWidth(250);
            tabelaCliente.getColumnModel().getColumn(1).setPreferredWidth(250);
            tabelaCliente.getColumnModel().getColumn(1).setMaxWidth(250);
            tabelaCliente.getColumnModel().getColumn(2).setMinWidth(150);
            tabelaCliente.getColumnModel().getColumn(2).setPreferredWidth(150);
            tabelaCliente.getColumnModel().getColumn(2).setMaxWidth(150);
            tabelaCliente.getColumnModel().getColumn(3).setMinWidth(250);
            tabelaCliente.getColumnModel().getColumn(3).setPreferredWidth(250);
            tabelaCliente.getColumnModel().getColumn(3).setMaxWidth(250);
            tabelaCliente.getColumnModel().getColumn(4).setMinWidth(0);
            tabelaCliente.getColumnModel().getColumn(4).setPreferredWidth(0);
            tabelaCliente.getColumnModel().getColumn(4).setMaxWidth(0);
            tabelaCliente.getColumnModel().getColumn(5).setMinWidth(0);
            tabelaCliente.getColumnModel().getColumn(5).setPreferredWidth(0);
            tabelaCliente.getColumnModel().getColumn(5).setMaxWidth(0);
            tabelaCliente.getColumnModel().getColumn(6).setMinWidth(0);
            tabelaCliente.getColumnModel().getColumn(6).setPreferredWidth(0);
            tabelaCliente.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        botaoEditarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit2.png"))); // NOI18N
        botaoEditarCliente.setText("Editar");
        botaoEditarCliente.setEnabled(false);
        botaoEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarClienteActionPerformed(evt);
            }
        });

        botaoExcluirCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/list_remove.png"))); // NOI18N
        botaoExcluirCliente.setText("Excluir");
        botaoExcluirCliente.setEnabled(false);
        botaoExcluirCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoExcluirClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpCliente2Layout = new javax.swing.GroupLayout(jpCliente2);
        jpCliente2.setLayout(jpCliente2Layout);
        jpCliente2Layout.setHorizontalGroup(
            jpCliente2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCliente2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCliente2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpCliente2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botaoEditarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoExcluirCliente))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpCliente2Layout.createSequentialGroup()
                        .addComponent(campoPesquisaCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buscarcli)))
                .addContainerGap())
        );
        jpCliente2Layout.setVerticalGroup(
            jpCliente2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCliente2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpCliente2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoPesquisaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buscarcli))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCliente2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoEditarCliente)
                    .addComponent(botaoExcluirCliente)))
        );

        botaoFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/dialog_close.png"))); // NOI18N
        botaoFechar.setText("Fechar");
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
                    .addComponent(jpCliente2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpCliente1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botaoFechar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpCliente2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoFechar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void campoCodClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoCodClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoCodClienteActionPerformed

    private void campoNomeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoNomeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoNomeClienteActionPerformed

    private void botaoNovoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoNovoClienteActionPerformed

        habilitarCampos();
        habilitarSalvar();
        zerarCampos();
        LOG.info("Inserção de cliente");
        // TODO add your handling code here:
    }//GEN-LAST:event_botaoNovoClienteActionPerformed

    private void botaoSalvarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarClienteActionPerformed

        try {
            salvarCliente();

            // TODO add your handling code here:
        } catch (EmailException ex) {
            Logger.getLogger(TelaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_botaoSalvarClienteActionPerformed

    private void comboCidadeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCidadeClienteActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_comboCidadeClienteActionPerformed

    private void botaoExcluirClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoExcluirClienteActionPerformed

        excluirCliente();

    }//GEN-LAST:event_botaoExcluirClienteActionPerformed

    private void tabelaClienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabelaClienteFocusGained

        NivelDAO nd = new NivelDAO();
        if (nd.buscar() == 2) {

            this.tabelaCliente.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                    //altera os botoes para ativados somente se houver linha selecionada
                    //botaoExcluirCliente.setEnabled(!lsm.isSelectionEmpty());
                    botaoEditarCliente.setEnabled(!lsm.isSelectionEmpty());
                }
            });

        } else {
            this.tabelaCliente.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                    //altera os botoes para ativados somente se houver linha selecionada
                    botaoExcluirCliente.setEnabled(!lsm.isSelectionEmpty());
                    botaoEditarCliente.setEnabled(!lsm.isSelectionEmpty());
                }
            });
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaClienteFocusGained

    private void botaoEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditarClienteActionPerformed

        botaoNovoCliente.setEnabled(false);
        botaoSalvarCliente.setEnabled(true);
        //botaoCancelarCliente.setEnabled(true);

        int row = tabelaCliente.getSelectedRow();

        Object id = tabelaCliente.getValueAt(row, 0);
        Object nome = tabelaCliente.getValueAt(row, 1);
        Object fone = tabelaCliente.getValueAt(row, 2);
        Object email = tabelaCliente.getValueAt(row, 3);
        Object endereco = tabelaCliente.getValueAt(row, 4);
        Object bairro = tabelaCliente.getValueAt(row, 5);

        Cidade cid = new Cidade();
        //cid.setNome((String) tabelaCliente.getValueAt(row, 6));
        cid.setIdCidade((Integer) tabelaCliente.getValueAt(row, 6));

        Cliente cli = new Cliente();

        cli.setIdCliente(Integer.parseInt(id.toString()));
        cli.setNome(nome.toString());
        cli.setFone(fone.toString());
        cli.setEmail(email.toString());
        cli.setEndereco(endereco.toString());
        cli.setBairro(bairro.toString());
        cli.setCidade(cid);

        campoCodCliente.setText(cli.getIdCliente().toString());
        campoNomeCliente.setText(cli.getNome());
        campoFoneCliente.setText(cli.getFone());
        campoEmailCliente.setText(cli.getEmail());
        campoEnderecoCliente.setText(cli.getEndereco());
        campoBairroCliente.setText(cli.getBairro());
        //comboCidadeCliente.setSelectedItem(cid);
        comboCidadeCliente.setSelectedIndex(cid.getIdCidade());

        campoNomeCliente.setEnabled(true);
        campoFoneCliente.setEnabled(true);
        campoEmailCliente.setEnabled(true);
        campoEnderecoCliente.setEnabled(true);
        campoBairroCliente.setEnabled(true);
        comboCidadeCliente.setEnabled(true);

        LOG.info("Edição do registro de id " + cli.getIdCliente());


    }//GEN-LAST:event_botaoEditarClienteActionPerformed

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed

        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void campoEmailClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoEmailClienteFocusLost

        if (campoEmailCliente.isEnabled()) {

            if ((campoEmailCliente.getText().contains("@"))
                    && (campoEmailCliente.getText().contains("."))
                    && (!campoEmailCliente.getText().contains(" "))) {

                String usuario = new String(campoEmailCliente.getText().substring(0,
                        campoEmailCliente.getText().lastIndexOf('@')));

                String dominio = new String(campoEmailCliente.getText().substring(campoEmailCliente.getText().lastIndexOf('@') + 1, campoEmailCliente.getText().length()));

                if ((usuario.length() >= 1) && (!usuario.contains("@"))
                        && (dominio.contains(".")) && (!dominio.contains("@")) && (dominio.indexOf(".")
                        >= 1) && (dominio.lastIndexOf(".") < dominio.length() - 1)) {

                    jbEmail.setText("");

                } else {

                    jbEmail.setText("E-mail Inválido");

                    campoEmailCliente.requestFocus();

                }

            } else {

                jbEmail.setText("E-mail Inválido");

                campoEmailCliente.requestFocus();

            }

        } else {
            jbEmail.setText("");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_campoEmailClienteFocusLost

    private void jbEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jbEmailFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jbEmailFocusLost

    private void campoFoneClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoFoneClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoFoneClienteActionPerformed

    private void campoFoneClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoFoneClienteFocusLost

        ValidaNumero(campoFoneCliente);
        // TODO add your handling code here:
    }//GEN-LAST:event_campoFoneClienteFocusLost

    private void buscarcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarcliActionPerformed

        buscarCliente();

        // TODO add your handling code here:
    }//GEN-LAST:event_buscarcliActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoEditarCliente;
    private javax.swing.JButton botaoExcluirCliente;
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton botaoNovoCliente;
    private javax.swing.JButton botaoSalvarCliente;
    private javax.swing.JButton buscarcli;
    private javax.swing.JTextField campoBairroCliente;
    private javax.swing.JTextField campoCodCliente;
    private javax.swing.JTextField campoEmailCliente;
    private javax.swing.JTextField campoEnderecoCliente;
    private javax.swing.JTextField campoFoneCliente;
    private javax.swing.JTextField campoNomeCliente;
    private javax.swing.JTextField campoPesquisaCliente;
    private javax.swing.JComboBox<String> comboCidadeCliente;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jbBairro;
    private javax.swing.JLabel jbCidade;
    private javax.swing.JLabel jbEmail;
    private javax.swing.JLabel jbEndereco;
    private javax.swing.JLabel jbFone;
    private javax.swing.JLabel jbNome;
    private javax.swing.JPanel jpCliente1;
    private javax.swing.JPanel jpCliente2;
    private javax.swing.JTable tabelaCliente;
    // End of variables declaration//GEN-END:variables
}
