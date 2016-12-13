/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import config.HibernateUtil;
import entidades.Cliente;
import entidades.Pedido;
import entidades.Usuario;
import java.util.List;
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

    // Método para cancelar um pedido
    public String cancelar(Object o) {

        Pedido ped = new Pedido();
        ped = (Pedido) o;

        // Descobre o id do Cliente através do Nome
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cli = new Cliente();
        cli.setNome(ped.getCliente().toString());
        int idCliente = clienteDAO.descobrirId(cli);
        cli.setIdCliente(idCliente);
        ped.setCliente(cli);

        // Descobre o id do Usuário através do Nome
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usu = new Usuario();
        usu.setNome(ped.getUsuario().toString());
        int idUsuario = usuarioDAO.descobrirId(usu);
        usu.setIdUsuario(idUsuario);
        ped.setUsuario(usu);

        Session sessao = null;
        try {
            sessao = HibernateUtil.getSessionFactory().openSession();
            Transaction t = sessao.beginTransaction();

            sessao.update(ped);
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

}
