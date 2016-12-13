/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.HibernateUtil;
import entidades.Cliente;
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
 * @author Eduardo
 */
public class ClienteDAO implements IDAO {

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

    public String deletar(Object d) {

        Session sessao = null;
        try {
            sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction t = sessao.beginTransaction();

            sessao.delete(d);

            t.commit();

            return null;
        } catch (HibernateException he) {
            he.printStackTrace();
            return he.toString();
        } finally {
            sessao.close();
        }
    }

    public void listarCliente(JTable tabelaCliente, Cliente cli) {

        String campoPesquisa = cli.getCampoPesquisaCliente();

        DefaultTableModel modelTable = (DefaultTableModel) tabelaCliente.getModel();
        modelTable.setNumRows(0);

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        String sql = "";

        sql = "FROM Cliente as cliente WHERE cliente.nome LIKE '%" + campoPesquisa + "%'";

        Query query = session.createQuery(sql);

        List<Cliente> dados_cliente = query.list();

        for (Cliente clienterow : dados_cliente) {

            modelTable.addRow(new Object[]{
                clienterow.getIdCliente(),
                clienterow.getNome(),
                clienterow.getFone(),
                clienterow.getEmail(),
                clienterow.getEndereco(),
                clienterow.getBairro(),
                clienterow.getCidade().getIdCidade()
            });
        }
        session.getTransaction().commit();
        session.close();

    }

    // Método para descobrir o Id do cliente através do nome do Cliente
    public int descobrirId(Cliente cli) {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        String sql = "";
        sql = "SELECT idCliente FROM Cliente as cliente WHERE cliente.nome = '" + cli.getNome() + "'";
        Query query = session.createQuery(sql);
        List<Integer> dado = query.list();
        session.getTransaction().commit();
        session.close();

        return dado.get(0); // Retorna o Id o Cliente

    }

}
