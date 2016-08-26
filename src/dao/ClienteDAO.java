/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.HibernateUtil;
import entidades.Cliente;
import org.hibernate.HibernateException;
import org.hibernate.Session;
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

            sessao.save(o);
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

}
