/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.HibernateUtil;
import entidades.Cliente;
import entidades.ItensPedido;
import entidades.Pedido;
import entidades.Produto;
import entidades.Usuario;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author giacomin
 */
public class VendaDAO {

    public void listarVenda(JTable tabelaVenda, Pedido ped, boolean status, String dataIni, String dataFin, Cliente cli, Usuario usu) {

        /*
         Status:
         0 ou null = Vendas ativas
         1 = Vendas canceladas
         */
               
        int idCliente = cli.getIdCliente();
        int idUsuario = usu.getIdUsuario();

        DefaultTableModel modelTable = (DefaultTableModel) tabelaVenda.getModel();
        modelTable.setNumRows(0);

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        String sql = "";

        if (status == false) {

            if ((cli.getIdCliente() != null) && (usu.getIdUsuario() != null)) {

                sql = "FROM Pedido WHERE status = 0 AND data BETWEEN '" + dataIni + "' AND '" + dataFin + "' AND id_cliente = " + idCliente + " AND  id_usuario = " + idUsuario + "";
            }

            if ((cli.getIdCliente() != null) && (usu.getIdUsuario() == null)) {

                sql = "FROM Pedido WHERE status = 0 AND data BETWEEN '" + dataIni + "' AND '" + dataFin + "' AND id_cliente = " + idCliente + "";
            }

            if ((cli.getIdCliente() == null) && (usu.getIdUsuario() != null)) {

                sql = "FROM Pedido WHERE status = 0 AND data BETWEEN '" + dataIni + "' AND '" + dataFin + "' AND id_usuario = " + idUsuario + "";
            } 
            
            if ((cli.getIdCliente() == null) && (usu.getIdUsuario() == null)) {
                
                sql = "FROM Pedido WHERE status = 0 AND data BETWEEN '" + dataIni + "' AND '" + dataFin + "";
            }

        } else {

            if ((cli.getIdCliente() != null) && (usu.getIdUsuario() != null)) {

                sql = "FROM Pedido WHERE status = 0 AND data BETWEEN '" + dataIni + "' AND '" + dataFin + "' AND id_cliente = " + idCliente + " AND  id_usuario = " + idUsuario + " AND status =  1";
            }

            if ((cli.getIdCliente() != null) && (usu.getIdUsuario() == null)) {

                sql = "FROM Pedido WHERE status = 0 AND data BETWEEN '" + dataIni + "' AND '" + dataFin + "' AND id_cliente = " + idCliente + " AND status =  1";
            }

            if ((cli.getIdCliente() == null) && (usu.getIdUsuario() != null)) {

                sql = "FROM Pedido WHERE status = 0 AND data BETWEEN '" + dataIni + "' AND '" + dataFin + "' AND id_usuario = " + idUsuario + " AND status =  1";
            } 
            
            if ((cli.getIdCliente() == null) && (usu.getIdUsuario() == null)) {
                sql = "FROM Pedido WHERE status = 0 AND data BETWEEN '" + dataIni + "' AND '" + dataFin + " AND status =  1";
            }

        }

        Query query = session.createQuery(sql);

        List<Pedido> dados_pedido = query.list();

        for (Pedido pedidorow : dados_pedido) {

            modelTable.addRow(new Object[]{
                pedidorow.getIdPedido(),
                pedidorow.getCliente().getNome(),
                pedidorow.getUsuario().getNome(),
                pedidorow.getData(),
                pedidorow.getValorTotal(),});
        }
        session.getTransaction().commit();
        session.close();

    }

    public void listarItensVenda(JTable tabelaVenda, Pedido ped) {

        DefaultTableModel modelTable = (DefaultTableModel) tabelaVenda.getModel();
        modelTable.setNumRows(0);

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        // Listagem na tabela necessita de informações de duas tabelas. Para isso foi preciso fazer em duas etapas:
        // Primeiro: Consulta dos itens de pedido (ajuster para pergar o pedido da tabela selecionada)
        String sqlItens = "FROM ItensPedido WHERE id_pedido = " + ped.getIdPedido() + "";

        Query queryItens = session.createQuery(sqlItens);

        List<ItensPedido> dados_itens = queryItens.list();

        for (ItensPedido itensrow : dados_itens) {

            String nome = itensrow.getProduto().toString();

            // Segundo: Consulta do produto passando o nome do produto da linha do item
            String sqlProduto = "FROM Produto WHERE nome = '" + nome + "'";
            Query queryProduto = session.createQuery(sqlProduto);
            List<Produto> dados_produto = queryProduto.list();

            for (Produto produtorow : dados_produto) {

                // Informações setadas para cada coluna
                modelTable.addRow(new Object[]{
                    produtorow.getIdProduto(),
                    itensrow.getProduto().toString(),
                    itensrow.getQuantidade(),
                    produtorow.getValorUnit(),
                    itensrow.getQuantidade() * produtorow.getValorUnit(),}); // Subtotal

            }
        }
        session.getTransaction().commit();
        session.close();

    }

}
