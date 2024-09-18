package servidor.clientes.cliente;

import java.awt.BorderLayout;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Cliente {
	
    private Socket socket;
    private PrintWriter out;
    private String nome;
    private static Leitor leitor;
    
    //getters and setters
   
    public PrintWriter getOut() {
    	return out;
    }
    
    public Socket getSocket() {
    	return socket;
    }
    //and getters and setters

	//construtor que será utilizado pelos clientes
	
	//construtor utilizado pela classe SalaChat, para iniciar a conexão dos clientes com o servidor
	public Cliente(String nome) throws IOException {
		this.nome = nome;
        socket = new Socket("localhost", 8000); // Conecta ao servidor
        out = new PrintWriter(socket.getOutputStream(), true);
       // leitor = new Leitor();
        
        // Cria uma thread separada para escutar mensagens do servidor

    }

    // Método para enviar mensagem ao servidor
    public void enviarMensagem(String mensagem) {
        out.println(nome+":"+mensagem);
    }
    
    //fecha a comunicação com o servidor em leitor e no cliente
    public void fecharComunicacao() throws IOException {
    	socket.close();
    }
    
 
   
}