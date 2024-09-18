package servidor.clientes.servidor;

import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {
    // Lista que vai armazenar as saídas de todos os clientes conectados
    private static List<PrintWriter> clientes = new ArrayList<>();
    
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Servidor iniciado na porta 8000...");
        
        while (true) {
            // Aceitando conexões de clientes
            Socket socket = serverSocket.accept();
            System.out.println("Novo cliente conectado: " + socket.getInetAddress().getHostAddress());
            
            // Criando um PrintWriter para enviar dados ao cliente
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            synchronized (clientes) {
                clientes.add(out); // Adiciona o cliente à lista
            }

            // Cria uma nova thread para lidar com a comunicação desse cliente
            new Thread(new ClienteHandler(socket)).start();
        }
    }

    // Método para enviar mensagens para todos os clientes
    public static void enviarParaTodos(String mensagem) {
        synchronized (clientes) {
            for (PrintWriter cliente : clientes) {
                cliente.println(mensagem);
            }
        }
    }
}
