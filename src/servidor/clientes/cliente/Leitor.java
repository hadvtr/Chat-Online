package servidor.clientes.cliente;

import java.io.*;
import java.net.*;
import javax.swing.SwingUtilities;
import javax.swing.JTextArea;

public class Leitor implements Runnable {
    
    private Socket socket;
    private JTextArea chatArea; // Referência para a área de chat na interface gráfica
    private static String msg = "eu";
    

	public static String getMsg() {
		return msg;
	}

	public static void setMsg(String msg) {
		Leitor.msg = msg;
	}
	
	public Leitor() {
		
	}
	
	
	//Leitor que pega as mensagens do servidor e leva para a interface grafica
	public Leitor(Socket socket, JTextArea chatArea) {
        this.socket = socket;
        this.chatArea = chatArea;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String mensagem;

            while ((mensagem = in.readLine()) != null) {
                System.out.println("Recebido do servidor: " + mensagem+"\n\n");
                setMsg(mensagem);
                // Atualize a interface gráfica na thread de eventos do Swing
                SwingUtilities.invokeLater(() -> {
                	if(chatArea != null) {
                		chatArea.append(getMsg());
                	}
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
}

