/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.HibernateUtil;
import entidades.Usuario;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Eduardo
 */
public class UsuarioDAO {

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

    public void listarUsuario(JTable tabelaUsuario, Usuario usu) {

        String campoPesquisa = usu.getCampoPesquisaUsuario();

        DefaultTableModel modelTable = (DefaultTableModel) tabelaUsuario.getModel();
        modelTable.setNumRows(0);

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        String sql = "";

        sql = "FROM Usuario as usuario WHERE usuario.nome LIKE '%" + campoPesquisa + "%'";

        Query query = session.createQuery(sql);

        List<Usuario> dados_usuario = query.list();

        for (Usuario usuariorow : dados_usuario) {

            modelTable.addRow(new Object[]{
                usuariorow.getIdUsuario(),
                usuariorow.getNome(),
                usuariorow.getLogin(),
                usuariorow.getSenha(),
                usuariorow.getCargo().getIdCargo()

            });
        }
        session.getTransaction().commit();
        session.close();

    }

    public Usuario getUsuarioLoginSenha(String login, String senha) {

        Session sessao = HibernateUtil.getSessionFactory().openSession();
        sessao.getTransaction();

        // AQUI VAI ME RETORNAR UM OBJETO DA CLASSE USUÁRIO
        return (Usuario) sessao.createCriteria(Usuario.class)
                // AQUI DIGO QUE DEVE TER O LOGIN IGUAL AO PASSADO POR PARAMETRO 
                .add(Restrictions.eq("login", login))
                // AQUI DIGO QUE DEVE TER O SENHA IGUAL AO PASSADO POR PARAMETRO
                .add(Restrictions.eq("senha", senha))
                // AQUI SETO PARA ME RETORNAR APENAS 1 RESULTADO
                // AFINAL LOGIN E SENHA É UNICO NO SISTEMA

                .uniqueResult();
    }

    // Método para descobrir Id do usuário através do nome do usuário
    public int descobrirId(Usuario usu) {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        String sql = "";
        sql = "SELECT idUsuario FROM Usuario as usuario WHERE usuario.nome = '" + usu.getNome() + "'";
        Query query = session.createQuery(sql);
        List<Integer> dado = query.list();
        session.getTransaction().commit();
        session.close();

        return dado.get(0); // Retorna o Id o Cliente

    }

}
