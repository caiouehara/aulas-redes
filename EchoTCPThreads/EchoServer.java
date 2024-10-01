/*
 * EchoServer.java
 */
import java.net.*;
import java.io.*;

public class EchoServer {
    
    public static void main(String[] args) {
        // declaracao das variaveis
        ServerSocket serverSocket;      // Socket TCP servidor (conexao)
        Socket clientSocket;            // Socket TCP de comunicacao com o cliente
        Boolean isRunning = true;

        try {
            // abre socket TCP servidor
            serverSocket = new ServerSocket(1050);

            System.out.println("Servidor pronto...");

        while(isRunning){
            // espera por requisicao de conexao enviada pelo socket cliente
            clientSocket = serverSocket.accept();	// cria socket TCP de comunicacao com o cliente
            new Thread(new ClientHandler(clientSocket)).start();
        }

        serverSocket.close();

        } catch (IOException e) {
            System.err.println("Erro: " + e);
        }             
    }
}
