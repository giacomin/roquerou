/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import entidades.Cidade;
import java.io.IOException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author Eduardo
 */
public class Email {

    public void sendEmail(String nome, String fone, String Email) throws EmailException {

        
        
        SimpleEmail email = new SimpleEmail();
        //Utilize o hostname do seu provedor de email
        System.out.println("alterando hostname...");
        email.setHostName("smtp.gmail.com");
        //Quando a porta utilizada não é a padrão (gmail = 465)
        email.setSmtpPort(578);
        //Adicione os destinatários
        email.addTo("eduardo.r.g.d@gmail.com", "Jose");
        //email.addTo("giacomin@universo.univates.br", "André");
        //Configure o seu email do qual enviará
        email.setFrom("egomes2@universo.univates.br", "Eduardo");
        //Adicione um assunto
        email.setSubject("Cliente Adicionado");
        //Adicione a mensagem do email
        email.setMsg("Novo Cliente \n"
                   + "Nome : "+nome+"\n"+
                     "Fone: "+fone+"\n"+
                     "Email: "+Email);
        //Para autenticar no servidor é necessário chamar os dois métodos abaixo
        System.out.println("autenticando...");
        email.setSSL(true);
        email.setAuthentication("egomes2@universo.univates.br", "92611831");
        System.out.println("enviando...");
        email.send();
        System.out.println("Email enviado!");
    }


}
