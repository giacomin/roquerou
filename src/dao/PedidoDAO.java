/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.HibernateUtil;
import entidades.Pedido;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author giacomin
 */
public class PedidoDAO implements IDAO {

    @Override
    public String salvar(Object o) {

        Session sessao = null;
        try {
            sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction t = sessao.beginTransaction();

            sessao.saveOrUpdate(o);
            t.commit();

            return null;
        } catch (HibernateException he) {
            he.printStackTrace();
            return he.toString();
        } finally {
            sessao.close();
        }
    }

    // Método para saber o Id do último pedido registrado
    public int ultimoPedido() {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        String sql = "FROM Pedido ORDER BY id_pedido DESC";

        Query query = session.createQuery(sql).setMaxResults(1); // Como se fosse "LIMIT 1" da linguaegm SQL

        List<Pedido> dados_pedido = query.list();

        // FOR de apenas 1 linha
        int ultimo = 0;
        for (Pedido pedidorow : dados_pedido) {
            ultimo = pedidorow.getIdPedido();

        }
        session.getTransaction().commit();
        session.close();
        return ultimo;
    }

    /*
     Não há opção de remover pedidos
     */
    /*
     public String remover(Object o) {

     Session sessao = null;
     try {
     sessao = HibernateUtil.getSessionFactory().openSession();
     Transaction t = sessao.beginTransaction();

     sessao.delete(o);
     t.commit();

     return null;
     } catch (HibernateException he) {
     he.printStackTrace();
     return he.toString();
     } finally {
     sessao.close();

     }

     }
     */
    /* 
     A opção de listar pedidos (vendas) estará em outra tela. Ficará comentado temporariamente
     */
    /*
     public void listarCompra(JTable tabelaCompra, Compra comp) {

     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

     String comboPesquisa = comp.getComboPesquisaCompra();
     String campoPesquisa = comp.getCampoPesquisaCompra();

     DefaultTableModel modelTable = (DefaultTableModel) tabelaCompra.getModel();
     modelTable.setNumRows(0);

     SessionFactory sf = HibernateUtil.getSessionFactory();
     Session session = sf.openSession();
     session.beginTransaction();

     String sql = "";

     if ("Produto".equals(comboPesquisa)) {

     sql = "FROM Compra as compra WHERE produto.nome LIKE '%" + campoPesquisa + "%'";
     }
        
     if (comboPesquisa == "Fornecedor") {
     sql = "FROM Compra as compra WHERE fornecedor.nome LIKE '%" + campoPesquisa + "%'";
     }
        
     if (comboPesquisa == "Operador") {
     sql = "FROM Compra as compra WHERE usuario.nome LIKE '%" + campoPesquisa + "%'";
     }

     Query query = session.createQuery(sql);

     List<Compra> dados_compra = query.list();

     for (Compra comprarow : dados_compra) {

     modelTable.addRow(new Object[]{
     comprarow.getIdCompra(),
     comprarow.getProduto().getNome(),
     comprarow.getFornecedor().getNome(),
     comprarow.getQuantidade(),
     comprarow.getCustoUnit(),
     sdf.format(comprarow.getData()), // Data
     comprarow.getUsuario().getNome(),});

     }
     session.getTransaction().commit();
     session.close();

     }
     */
}
