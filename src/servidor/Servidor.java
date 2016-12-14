/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Eduardo
 */
public class Servidor {
    public static void main(String[] args) throws Exeception, IOException{
        System.out.println("iniciando servidor.");
        
        ServerSocket server = new ServerSocket(2525);
        
        System.out.println("Aguardando conexão.");
        Socket socket = server.accept();
        
        System.out.println("Conexão estabelecida.");
        
        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();
        
        BufferedReader in = new BufferedReader (new InputStreamReader (input));
        PrintStream out = new PrintStream(output);
        
        while (true){
            String mensagem = in.readLine();
            
            System.out.println(
                    "Mensagem recebida do cliente [" +
                    socket.getInetAddress().getHostName() +
                    "]: " +
                    mensagem);
            if ("FIM".equals(mensagem)){
                break;
            }
            
            out.println(mensagem);
        }
        
        System.out.println("Encerrando conexão.");
        
        in.close();
        
        out.close();
        
        socket.close();
        
        System.out.println("Encerrando servidor.");
        
        server.close();
    }
}
