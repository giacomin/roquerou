/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.HibernateUtil;
import entidades.Cidade;
import entidades.Cliente;
import entidades.Fornecedor;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import telas.TelaFornecedor;

/**
 *
 * @author Eduardo
 */
public class FornecedorDAO implements IDAO {

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

    public void listarFornecedor(JTable tabelaForn, Fornecedor forn) {

        String campoPesquisa = forn.getCampoPesquisaFornecedor();

        DefaultTableModel modelTable = (DefaultTableModel) tabelaForn.getModel();
        modelTable.setNumRows(0);

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        String sql = "";

        sql = "FROM Fornecedor as fornecedor WHERE fornecedor.nome LIKE '%" + campoPesquisa + "%'";

        Query query = session.createQuery(sql);

        List<Fornecedor> dados_fornecedor = query.list();

        for (Fornecedor fornecedorrow : dados_fornecedor) {

            modelTable.addRow(new Object[]{
                fornecedorrow.getIdFornecedor(),
                fornecedorrow.getNome(),
                fornecedorrow.getFone(),
                fornecedorrow.getEmail(),
                fornecedorrow.getEndereco(),
                fornecedorrow.getBairro(),
                fornecedorrow.getCidade().getIdCidade(),
                fornecedorrow.getCnpj()
            });
        }
        session.getTransaction().commit();
        session.close();

    }

    // Pesquisar ID de um fornecedor através do Nome (necessário para registrar compra)
    public Integer getIdFromName(String nome) {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        String sql = "FROM Fornecedor as fornecedor WHERE fornecedor.nome = " + nome;

        Query query = session.createQuery(sql);

        session.getTransaction().commit();
        session.close();
        
        
        System.out.println("teste: " + query.getFirstResult());
        
        return query.getFirstResult();

    }

}
