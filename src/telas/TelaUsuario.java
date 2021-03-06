/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import config.HibernateUtil;
import dao.ClienteDAO;
import dao.NivelDAO;
import dao.UsuarioDAO;
import entidades.Cargo;
import entidades.Cidade;
import entidades.Cliente;
import entidades.Usuario;
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
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Eduardo
 */
public class TelaUsuario extends javax.swing.JInternalFrame {
    
    private static final Logger LOG = Logger.getLogger(TelaUsuario.class.getName());

    /**
     * Creates new form TelaUsuario
     */
    public TelaUsuario() {
        
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
        
        LOG.info("Abertura da tela de usuários");
        
        initComponents();
        NivelDAO nd = new NivelDAO();
        if (nd.buscar() == 2 || nd.buscar()==3) {
            botaoNovoUsuario.setEnabled(false);
            jbBuscarUsuario.setEnabled(false);
            tabelaUsuario.setEnabled(false);
        }
        
        popularCombo();
        setarLabels();

    }

    public Integer nu(int a) {

        return null;
    }
    int nivelusuario = 0;

    public void setarLabels() {

        jbNome.setText("");
        jbNome.setForeground(Color.red);

        jbSenha.setText("");
        jbSenha.setForeground(Color.red);

        jbLogin.setText("");
        jbLogin.setForeground(Color.red);

        jbCargo.setText("");
        jbCargo.setForeground(Color.red);

    }

    public void habilitarCampos() {

        tabelaUsuario.clearSelection();
        campoNomeUsuario.requestFocus();

        campoNomeUsuario.setEnabled(true);
        campoLoginUsuario.setEnabled(true);
        campoSenhaUsuario.setEnabled(true);
        comboCargo.setEnabled(true);

        botaoEditarUsuario.setEnabled(false);
        botaoExcluirUsuario.setEnabled(false);
        botaoNovoUsuario.setEnabled(false);
    }

    public void bloquearCampos() {

        campoNomeUsuario.setEnabled(false);
        campoLoginUsuario.setEnabled(false);
        campoSenhaUsuario.setEnabled(false);
        comboCargo.setEnabled(false);

        botaoSalvarUsuario.setEnabled(false);
        botaoNovoUsuario.setEnabled(true);
        //botaoCancelarUsuario.setEnabled(false);
    }

    public void zerarCampos() {
        campoCodUsuario.setText("");
        campoNomeUsuario.setText("");
        campoLoginUsuario.setText("");
        campoSenhaUsuario.setText("");
        comboCargo.setSelectedIndex(0);

    }

    // método habilitarSalvar()
    public void habilitarSalvar() {
        botaoSalvarUsuario.setEnabled(true);
        //botaoCancelarUsuario.setEnabled(true);

    }

    public void usunivelamento() {
        if (nivelusuario == 2) {
            botaoNovoUsuario.setEnabled(false);
            jbBuscarUsuario.setEnabled(false);
            tabelaUsuario.setEnabled(false);
            campoPesquisaUsuario.setEditable(false);
        }
    }

    public void popularCombo() {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Cargo");
        List<Cargo> dados_cargo = query.list();
        Cargo car = new Cargo();

        for (Cargo cargorow : dados_cargo) {

            car.setIdCargo(cargorow.getIdCargo());
            car.setNome(cargorow.getNome());
            comboCargo.addItem(cargorow.getNome());
        }

        session.getTransaction().commit();
    }

    public void salvarUsuario() {

        Usuario usu = new Usuario();
        Cargo car = new Cargo();

        String nome = campoNomeUsuario.getText().trim();
        String login = campoLoginUsuario.getText().trim();
        String senha = campoSenhaUsuario.getText().trim();

        if (nome == null || nome.isEmpty()) {
            jbNome.setText("Digite o Nome");
        } else {
            jbNome.setText("");
            if (login == null || login.isEmpty()) {
                jbLogin.setText("Digite o Login");
            } else {
                jbLogin.setText("");
                if (senha == null || senha.isEmpty()) {
                    jbSenha.setText("Digite uma senha");
                } else {
                    jbSenha.setText("");

                    usu.setNome(campoNomeUsuario.getText());
                    usu.setLogin(campoLoginUsuario.getText());
                    usu.setSenha(campoSenhaUsuario.getText());
                    car.setIdCargo(comboCargo.getSelectedIndex());

                    if (comboCargo.getSelectedIndex() == 0) {
                        jbCargo.setText("Selecione o Cargo");
                    } else {
                        jbCargo.setText("");

                        if ("".equals(campoCodUsuario.getText())) {

                            usu.setCargo(car);

                            String retorno = new UsuarioDAO().salvar(usu);

                            if (retorno == null) {
                                JOptionPane.showMessageDialog(null, "Usuário Adicionado!");
                                zerarCampos();
                                bloquearCampos();
                            } else {
                                JOptionPane.showMessageDialog(null, "Erro ao adicionar usuário!");
                            }

                        } else {

                            usu.setIdUsuario(Integer.parseInt(campoCodUsuario.getText()));

                            usu.setCargo(car);

                            String retorno = new UsuarioDAO().salvar(usu);

                            if (retorno == null) {
                                LOG.info("Usuário salvo");
                                JOptionPane.showMessageDialog(null, "Usuário Adicionado!");
                                zerarCampos();
                                bloquearCampos();
                            } else {
                                LOG.severe("Erro ao salvar o usuário");
                                JOptionPane.showMessageDialog(null, "Erro ao adicionar usuário!");
                            }

                        }

                    }
                }
            }
        }
    }

    public void buscarUsuario() {

        UsuarioDAO pesquisa = new UsuarioDAO();
        Usuario usu = new Usuario();

        String campoPesquisa = campoPesquisaUsuario.getText();

        usu.setCampoPesquisaUsuario(campoPesquisa);

        pesquisa.listarUsuario(tabelaUsuario, usu);

    }

    public void excluirUsuario() {

        if (JOptionPane.showConfirmDialog(null, "Deseja mesmo remover o registro?", "Cuidado", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Cargo car = new Cargo();
            Usuario usu = new Usuario();

            car.setIdCargo((int) tabelaUsuario.getValueAt(tabelaUsuario.getSelectedRow(), 4));
            usu.setIdUsuario((int) tabelaUsuario.getValueAt(tabelaUsuario.getSelectedRow(), 0));
            usu.setCargo(car);
            usu.setNome((String) tabelaUsuario.getValueAt(tabelaUsuario.getSelectedRow(), 1));

            String retorno = new UsuarioDAO().deletar(usu);

            buscarUsuario();

            if (retorno == null) {
                LOG.info("Usuário excluído");
                System.out.println("Cliente Excluído");
            } else {
                LOG.severe("Erro ao excluir o usuário");
                System.out.println("ERRO na EXCLUSÃO");
            }

        } else {

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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        campoNomeUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        campoCodUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        campoLoginUsuario = new javax.swing.JTextField();
        campoSenhaUsuario = new javax.swing.JTextField();
        botaoSalvarUsuario = new javax.swing.JButton();
        botaoNovoUsuario = new javax.swing.JButton();
        jbNome = new javax.swing.JLabel();
        jbLogin = new javax.swing.JLabel();
        jbSenha = new javax.swing.JLabel();
        jbCargo = new javax.swing.JLabel();
        comboCargo = new javax.swing.JComboBox<String>();
        jLabel5 = new javax.swing.JLabel();
        jpUsuario = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaUsuario = new javax.swing.JTable();
        botaoEditarUsuario = new javax.swing.JButton();
        botaoExcluirUsuario = new javax.swing.JButton();
        campoPesquisaUsuario = new javax.swing.JTextField();
        jbBuscarUsuario = new javax.swing.JButton();
        botaoFechar = new javax.swing.JButton();

        setTitle("Usuário");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        campoNomeUsuario.setEnabled(false);
        campoNomeUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoNomeUsuarioActionPerformed(evt);
            }
        });

        jLabel2.setText("Nome:");

        jLabel1.setText("Código:");

        campoCodUsuario.setEnabled(false);
        campoCodUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoCodUsuarioActionPerformed(evt);
            }
        });

        jLabel3.setText("Login");

        jLabel4.setText("Senha:");

        campoLoginUsuario.setEnabled(false);

        campoSenhaUsuario.setText(" ");
        campoSenhaUsuario.setEnabled(false);
        campoSenhaUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoSenhaUsuarioActionPerformed(evt);
            }
        });

        botaoSalvarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/document_save_all.png"))); // NOI18N
        botaoSalvarUsuario.setText("Salvar");
        botaoSalvarUsuario.setEnabled(false);
        botaoSalvarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSalvarUsuarioActionPerformed(evt);
            }
        });

        botaoNovoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/list_add.png"))); // NOI18N
        botaoNovoUsuario.setText("Novo");
        botaoNovoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoNovoUsuarioActionPerformed(evt);
            }
        });

        jbNome.setText("nome invalido");

        jbLogin.setText("login inválido");

        jbSenha.setText("senha inválida");

        jbCargo.setText("cargo invalido");

        comboCargo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
        comboCargo.setEnabled(false);
        comboCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCargoActionPerformed(evt);
            }
        });

        jLabel5.setText("Cargo:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jbLogin)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(campoLoginUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(campoSenhaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jbSenha)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(botaoNovoUsuario)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(botaoSalvarUsuario))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(campoNomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel5))
                                        .addComponent(jbNome))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jbCargo)
                                        .addComponent(comboCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(campoCodUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(campoCodUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(campoNomeUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbNome)
                    .addComponent(jbCargo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(campoLoginUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(campoSenhaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbLogin)
                    .addComponent(jbSenha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoNovoUsuario)
                    .addComponent(botaoSalvarUsuario))
                .addContainerGap())
        );

        jpUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tabelaUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Cod.", "Nome", "Login", "Senha", "Cargo"
            }
        ));
        tabelaUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tabelaUsuarioFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaUsuario);
        if (tabelaUsuario.getColumnModel().getColumnCount() > 0) {
            tabelaUsuario.getColumnModel().getColumn(0).setMinWidth(50);
            tabelaUsuario.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabelaUsuario.getColumnModel().getColumn(0).setMaxWidth(50);
            tabelaUsuario.getColumnModel().getColumn(1).setMinWidth(200);
            tabelaUsuario.getColumnModel().getColumn(1).setPreferredWidth(200);
            tabelaUsuario.getColumnModel().getColumn(1).setMaxWidth(200);
            tabelaUsuario.getColumnModel().getColumn(2).setMinWidth(150);
            tabelaUsuario.getColumnModel().getColumn(2).setPreferredWidth(150);
            tabelaUsuario.getColumnModel().getColumn(2).setMaxWidth(150);
            tabelaUsuario.getColumnModel().getColumn(3).setMinWidth(0);
            tabelaUsuario.getColumnModel().getColumn(3).setPreferredWidth(0);
            tabelaUsuario.getColumnModel().getColumn(3).setMaxWidth(0);
            tabelaUsuario.getColumnModel().getColumn(4).setMinWidth(100);
            tabelaUsuario.getColumnModel().getColumn(4).setPreferredWidth(100);
            tabelaUsuario.getColumnModel().getColumn(4).setMaxWidth(100);
        }

        botaoEditarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit2.png"))); // NOI18N
        botaoEditarUsuario.setText("Editar");
        botaoEditarUsuario.setEnabled(false);
        botaoEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEditarUsuarioActionPerformed(evt);
            }
        });

        botaoExcluirUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/list_remove.png"))); // NOI18N
        botaoExcluirUsuario.setText("Excluir");
        botaoExcluirUsuario.setEnabled(false);
        botaoExcluirUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoExcluirUsuarioActionPerformed(evt);
            }
        });

        jbBuscarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/document_preview.png"))); // NOI18N
        jbBuscarUsuario.setText("Buscar");
        jbBuscarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpUsuarioLayout = new javax.swing.GroupLayout(jpUsuario);
        jpUsuario.setLayout(jpUsuarioLayout);
        jpUsuarioLayout.setHorizontalGroup(
            jpUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpUsuarioLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botaoEditarUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoExcluirUsuario))
                    .addGroup(jpUsuarioLayout.createSequentialGroup()
                        .addComponent(campoPesquisaUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbBuscarUsuario)))
                .addContainerGap())
        );
        jpUsuarioLayout.setVerticalGroup(
            jpUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoPesquisaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscarUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoEditarUsuario)
                    .addComponent(botaoExcluirUsuario))
                .addContainerGap())
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
                    .addComponent(jpUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botaoFechar))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoFechar)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabelaUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabelaUsuarioFocusGained

        this.tabelaUsuario.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                //altera os botoes para ativados somente se houver linha selecionada
                botaoExcluirUsuario.setEnabled(!lsm.isSelectionEmpty());
                botaoEditarUsuario.setEnabled(!lsm.isSelectionEmpty());
            }
        });

        // TODO add your handling code here:
    }//GEN-LAST:event_tabelaUsuarioFocusGained

    private void botaoEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEditarUsuarioActionPerformed

        botaoNovoUsuario.setEnabled(false);
        botaoSalvarUsuario.setEnabled(true);
        //botaoCancelarUsuario.setEnabled(true);

        int row = tabelaUsuario.getSelectedRow();

        Object id = tabelaUsuario.getValueAt(row, 0);
        Object nome = tabelaUsuario.getValueAt(row, 1);
        Object login = tabelaUsuario.getValueAt(row, 2);
        Object senha = tabelaUsuario.getValueAt(row, 3);
        Object cargo = tabelaUsuario.getValueAt(row, 4);

        Usuario usu = new Usuario();

        Cargo car = new Cargo();
        car.setIdCargo((Integer) tabelaUsuario.getValueAt(row, 4));
        //cid.setIdCidade((Integer) tabelaCliente.getValueAt(row, 6));

        usu.setIdUsuario(Integer.parseInt(id.toString()));
        usu.setNome(nome.toString());
        usu.setLogin(login.toString());
        usu.setSenha(senha.toString());
        usu.setCargo(car);

        campoCodUsuario.setText(usu.getIdUsuario().toString());
        campoNomeUsuario.setText(usu.getNome());
        campoLoginUsuario.setText(usu.getLogin());
        campoSenhaUsuario.setText(usu.getSenha());
        comboCargo.setSelectedIndex(car.getIdCargo());
        //comboCargo.setSelectedItem(car); ( Quando for utilizar o nome em vez de número na tabela

        campoNomeUsuario.setEnabled(true);
        campoLoginUsuario.setEnabled(true);
        campoSenhaUsuario.setEnabled(true);
        comboCargo.setEnabled(true);

    }//GEN-LAST:event_botaoEditarUsuarioActionPerformed

    private void botaoExcluirUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoExcluirUsuarioActionPerformed

        excluirUsuario();
    }//GEN-LAST:event_botaoExcluirUsuarioActionPerformed

    private void jbBuscarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarUsuarioActionPerformed

        buscarUsuario();
        // TODO add your handling code here:
    }//GEN-LAST:event_jbBuscarUsuarioActionPerformed

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed

        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void botaoNovoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoNovoUsuarioActionPerformed

        habilitarCampos();
        habilitarSalvar();
        zerarCampos();
        // TODO add your handling code here:
    }//GEN-LAST:event_botaoNovoUsuarioActionPerformed

    private void botaoSalvarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarUsuarioActionPerformed

        salvarUsuario();

        // TODO add your handling code here:
    }//GEN-LAST:event_botaoSalvarUsuarioActionPerformed

    private void campoSenhaUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoSenhaUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoSenhaUsuarioActionPerformed

    private void campoCodUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoCodUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoCodUsuarioActionPerformed

    private void campoNomeUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoNomeUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoNomeUsuarioActionPerformed

    private void comboCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCargoActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_comboCargoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoEditarUsuario;
    private javax.swing.JButton botaoExcluirUsuario;
    private javax.swing.JButton botaoFechar;
    private javax.swing.JButton botaoNovoUsuario;
    private javax.swing.JButton botaoSalvarUsuario;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JTextField campoCodUsuario;
    private javax.swing.JTextField campoLoginUsuario;
    private javax.swing.JTextField campoNomeUsuario;
    private javax.swing.JTextField campoPesquisaUsuario;
    private javax.swing.JTextField campoSenhaUsuario;
    private javax.swing.JComboBox<String> comboCargo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbBuscarUsuario;
    private javax.swing.JLabel jbCargo;
    private javax.swing.JLabel jbLogin;
    private javax.swing.JLabel jbNome;
    private javax.swing.JLabel jbSenha;
    private javax.swing.JPanel jpUsuario;
    private javax.swing.JTable tabelaUsuario;
    // End of variables declaration//GEN-END:variables
}
