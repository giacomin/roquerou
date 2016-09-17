/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.HibernateUtil;
import entidades.Produto;
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
public class ProdutoDAO implements IDAO {

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

    public void listarProduto(JTable tabelaProduto, Produto prod) {

        String comboPesquisa = prod.getComboPesquisaProduto();
        String campoPesquisa = prod.getCampoPesquisaProduto();

        DefaultTableModel modelTable = (DefaultTableModel) tabelaProduto.getModel();
        modelTable.setNumRows(0);

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        String sql = "";

        if (comboPesquisa == "Nome") {
            sql = "FROM Produto as produto WHERE produto.nome LIKE '%" + campoPesquisa + "%'";
        }
        
        Query query = session.createQuery(sql);

        List<Produto> dados_produto = query.list();

        for (Produto produtorow : dados_produto) {

            modelTable.addRow(new Object[]{
                produtorow.getIdProduto(),
                produtorow.getNome(),
                produtorow.getDescricao(),
                produtorow.getUnidade(),
                produtorow.getValorUnit(),
                produtorow.getEstoque(),});
        }
        session.getTransaction().commit();
        session.close();

    }

}
