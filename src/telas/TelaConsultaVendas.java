/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import config.HibernateUtil;
import dao.PedidoDAO;
import dao.VendaDAO;
import entidades.Cliente;
import entidades.Pedido;
import entidades.Usuario;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

//Logs
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author giacomin
 */
public class TelaConsultaVendas extends javax.swing.JInternalFrame {

    private static final Logger LOG = Logger.getLogger(TelaConsultaVendas.class.getName());

    /**
     * Creates new form TelaConsultaVendas
     */
    public TelaConsultaVendas() {
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

        LOG.info("Abertura da Tela de Consulta de Vendas");

        //popularComboCliente();
        //popularComboVendedor();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        campoDataIni = new javax.swing.JTextField();
        campoDataFin = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaVenda = new javax.swing.JTable();
        botaoCancelar = new javax.swing.JButton();
        botaoDetalhes = new javax.swing.JButton();
        botaoBuscar = new javax.swing.JButton();
        checkCancelada = new javax.swing.JCheckBox();
        botaoFechar = new javax.swing.JButton();

        setClosable(true);
        setTitle("Consulta de Vendas");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("Datas entre");

        jLabel2.setText("e");

        tabelaVenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod.", "Cliente", "Vendedor", "Data", "Total"
            }
        ));
        jScrollPane1.setViewportView(tabelaVenda);
        if (tabelaVenda.getColumnModel().getColumnCount() > 0) {
            tabelaVenda.getColumnModel().getColumn(0).setMinWidth(20);
            tabelaVenda.getColumnModel().getColumn(1).setMinWidth(200);
            tabelaVenda.getColumnModel().getColumn(2).setMinWidth(200);
            tabelaVenda.getColumnModel().getColumn(3).setMinWidth(90);
            tabelaVenda.getColumnModel().getColumn(4).setMinWidth(80);
        }

        botaoCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/button_cancel.png"))); // NOI18N
        botaoCancelar.setText("Cancelar");
        botaoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoCancelarActionPerformed(evt);
            }
        });

        botaoDetalhes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/edit_redo.png"))); // NOI18N
        botaoDetalhes.setText("Detalhes");
        botaoDetalhes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoDetalhesActionPerformed(evt);
            }
        });

        botaoBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/document_preview.png"))); // NOI18N
        botaoBuscar.setText("Buscar");
        botaoBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoBuscarActionPerformed(evt);
            }
        });

        checkCancelada.setText("Canceladas");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoDataIni, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoDataFin, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(checkCancelada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoBuscar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botaoDetalhes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoCancelar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoBuscar)
                    .addComponent(checkCancelada)
                    .addComponent(jLabel1)
                    .addComponent(campoDataIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(campoDataFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoCancelar)
                    .addComponent(botaoDetalhes))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botaoFechar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoFechar)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoBuscarActionPerformed
        pesquisaVendas();
    }//GEN-LAST:event_botaoBuscarActionPerformed

    private void botaoDetalhesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoDetalhesActionPerformed
        TelaDetalheVenda telaDetalheVenda = new TelaDetalheVenda();
        TelaPrincipal.jDesktopPane1PRINCIPAL.add(telaDetalheVenda);
        telaDetalheVenda.setVisible(true);
        telaDetalheVenda.moveToFront();
    }//GEN-LAST:event_botaoDetalhesActionPerformed

    private void botaoFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoFecharActionPerformed
        dispose();
    }//GEN-LAST:event_botaoFecharActionPerformed

    private void botaoCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoCancelarActionPerformed
        cancelarPedido();
    }//GEN-LAST:event_botaoCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoBuscar;
    private javax.swing.JButton botaoCancelar;
    private javax.swing.JButton botaoDetalhes;
    private javax.swing.JButton botaoFechar;
    public static javax.swing.JTextField campoDataFin;
    public static javax.swing.JTextField campoDataIni;
    private javax.swing.JCheckBox checkCancelada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tabelaVenda;
    // End of variables declaration//GEN-END:variables

    private void pesquisaVendas() {

        LOG.info("Pesquisa por registros");

        Cliente cli = new Cliente();
        Usuario usu = new Usuario();

        boolean status = checkCancelada.isSelected();
        System.out.println("Cancelada? " + checkCancelada.isSelected());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        /* 
         Date data1 = null;
         Date data2 = null;
         try {
         data1 = sdf.parse(campoDataIni.getText());
         data2 = sdf.parse(campoDataFin.getText());
         } catch (ParseException ex) {
         Logger.getLogger(TelaConsultaVendas.class.getName()).log(Level.SEVERE, null, ex);
         }
         //System.out.println("Teste Data: " + data1.toString() + "");
         */
        String dataIni = campoDataIni.getText();
        String dataFin = campoDataFin.getText();

        // prod = (Produto) comboProduto.getSelectedItem(); // prod receberá todo o objeto Produto (com id e nome)
        // forn = (Fornecedor) comboFornecedor.getSelectedItem(); // forn receberá todo o objeto Fornecedor (com id e nome)
        //cli = (Cliente) comboCliente.getSelectedItem();
        //usu = (Usuario) comboVendedor.getSelectedItem();

        VendaDAO pesquisa = new VendaDAO();
        Pedido ped = new Pedido();

        pesquisa.listarVenda(tabelaVenda, ped, status, dataIni, dataFin);
        
        
        // NAO ESTA FUNCIONANDO QUANDO NAO SELECIONA CLIENTE OU USUARIO. VERIFICAR TAMBPEM A QUESTAO DA DATA

        /*
         Status:
         0 ou null = Vendas ativas
         1 = Vendas canceladas
         */
    }

    private void cancelarPedido() { // Não apaga, apenas altera o status para 1.

        LOG.info("Cancelamento de pedido");

        PedidoDAO pedDAO = new PedidoDAO();
        Pedido ped = new Pedido();
        Cliente cli = new Cliente();
        Usuario usu = new Usuario();

        int row = tabelaVenda.getSelectedRow(); // Pega o número da linha selecionada

        Object id = tabelaVenda.getValueAt(row, 0);
        Object nomeCliente = tabelaVenda.getValueAt(row, 1);
        Object nomeUsuario = tabelaVenda.getValueAt(row, 2);
        Object data = tabelaVenda.getValueAt(row, 3);
        Object total = tabelaVenda.getValueAt(row, 4);

        cli.setNome((String) nomeCliente);
        usu.setNome((String) nomeUsuario);

        ped.setIdPedido(Integer.parseInt(id.toString()));
        ped.setCliente(cli);
        ped.setUsuario(usu);
        ped.setData((Date) data);
        ped.setValorTotal((float) total);
        ped.setStatus(1); // Novo status (cancelado)

        String retorno = new PedidoDAO().cancelar(ped);

        if (retorno == null) {
            LOG.info("Pedido de id " + ped.getIdPedido() + " cancelado.");
            JOptionPane.showMessageDialog(null, "Pedido cancelado!");

        } else {
            LOG.severe("Erro ao cancelar o pedido.");
            JOptionPane.showMessageDialog(null, "Erro ao cancelar o pedido.");
        }

    }

    /*
    // *** Método (provisório) popularComboCliente() ***
    public void popularComboCliente() {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Cliente");
        List<Cliente> dados_cliente = query.list();
        Cliente cli = new Cliente();

        for (Cliente clienterow : dados_cliente) {

            cli.setIdCliente(clienterow.getIdCliente());
            cli.setNome(clienterow.getNome());
            comboCliente.addItem(clienterow); // Adiciona o objeto inteiro
        }

        session.getTransaction().commit();
    }

    // *** Método (provisório) popularComboVendedor() ***
    public void popularComboVendedor() {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        Query query = session.createQuery("from Usuario");
        List<Usuario> dados_usuario = query.list();
        Usuario usu = new Usuario();

        for (Usuario usuariorow : dados_usuario) {

            usu.setIdUsuario(usuariorow.getIdUsuario());
            usu.setNome(usuariorow.getNome());
            comboVendedor.addItem(usuariorow); // Adiciona o objeto inteiro
        }

        session.getTransaction().commit();
    }
    */

}
