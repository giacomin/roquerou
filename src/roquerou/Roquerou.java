/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roquerou;

import config.HibernateUtil;
import entidades.Cidade;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import telas.TelaPrincipal;

/**
 *
 * @author giacomin
 */
public class Roquerou {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                
        new TelaPrincipal().setVisible(true);
        


        // Criar ou atualizar
        /*
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
                
        Cidade c = new Cidade();
        //c.setIdCidade(1);
        c.setNome("Teutônia");
        c.setUf("RS");
        c.setCep(95890000);
        
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(c);
        tx.commit();
        
        session.flush();
        session.close();
        sf.close();
        */       
              
        // Deletar
        /*
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
                
        Cidade c = new Cidade();
        //c.setIdCidade(1);
        
        Transaction tx = session.beginTransaction();
        session.delete(p);
        tx.commit();
       
        session.flush();
        session.close();
        sf.close();
        */
        
        
    }
    
}
