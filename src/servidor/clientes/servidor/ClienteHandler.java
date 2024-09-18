package servidor.clientes.servidor;

import java.io.*;
import java.net.*;

public class ClienteHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;

    public ClienteHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String mensagem;
            
            // Escuta as mensagens enviadas pelo cliente
            while ((mensagem = in.readLine()) != null) {
                System.out.println("Recebido: " + mensagem);
                // Envia para todos os clientes conectados
                Servidor.enviarParaTodos(mensagem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}