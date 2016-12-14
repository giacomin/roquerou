/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.HibernateUtil;
import entidades.Cidade;
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
public class CidadeDAO implements IDAO {

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
    
    public void listarCidade(JTable tabelaCidade, Cidade cid) {
        
        String comboPesquisa = cid.getComboPesquisaCidade();
        String campoPesquisa = cid.getCampoPesquisaCidade();

        DefaultTableModel modelTable = (DefaultTableModel) tabelaCidade.getModel();
        modelTable.setNumRows(0);
        
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        

        String sql = "";
        
        if ("Nome".equals(comboPesquisa)) {
            sql = "FROM Cidade as cidade WHERE cidade.nome LIKE '%" + campoPesquisa + "%'";
        }
        if ("UF".equals(comboPesquisa)) {
            sql = "FROM Cidade as cidade WHERE cidade.uf LIKE '%" + campoPesquisa + "%'";
        }
        Query query = session.createQuery(sql);
               
        List <Cidade> dados_cidade = query.list();
        
        for (Cidade cidaderow : dados_cidade) {
            
            modelTable.addRow(new Object[] {
                cidaderow.getIdCidade(),
                cidaderow.getNome(),
                cidaderow.getCep(),
                cidaderow.getUf(),
            });
        }
        session.getTransaction().commit();
        session.close();
        
        
    }
    
}
