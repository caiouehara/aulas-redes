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
        String message;               	// Mensagem enviada pelo cliente
		BufferedReader in;				// Entrada(recepcao) formatada de dados
		PrintWriter out;				// Saida (envio) formatado de dados
        Boolean isRunning = true;

        try {
            // abre socket TCP servidor
            serverSocket = new ServerSocket(1050);

            System.out.println("Servidor pronto...");

            // espera por requisicao de conexao enviada pelo socket cliente
            clientSocket = serverSocket.accept();	// cria socket TCP de comunicacao com o cliente

            // abre fluxos de entrada e saida de dados associados ao socket TCP de comunicacao com o cliente
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            
			// recebe mensagem de requisicao, escreve mensagem de requisicao e envia mensagem de resposta		
            System.out.println("TCP conectado...");

            while(isRunning){
                message = in.readLine();
                if (message == null) {
                   isRunning = false;
                   break;
                }

                String[] parseMessage = message.split(" ");

                if(parseMessage.length < 3) {
                    System.out.println("Mensagem com formatação incorreta");
                    out.println(0);
                    continue;
                };

                int number1 = Integer.parseInt(parseMessage[0]);
                int number2 = Integer.parseInt(parseMessage[2]);
                String operand = parseMessage[1];
                int result = 0;
                
                switch(operand) {
                    case "plus":
                        result = number1 + number2;
                        break;
                    case "div":
                        if(number2 != 0){
                            result = number1/number2;
                        }
                        break;
                    case "minus":
                        result = number1 - number2;
                        break;
                    case "times":
                        result = number1 * number2;
                        break;
                    default:
                        result = 0;
                        System.out.println("Mensagem com operação incorreta");
                        out.println(0);
                        continue;
                }

                System.out.println("Do cliente -> " + message + " resultando em " + String.valueOf(result));
                out.println(result);		// envia mensagem de volta para o cliente
            }
                
            // fecha fluxos de entrada e saida de dados
            out.close();
            in.close();

            // fecha sockets TCP de comunicacao com o cliente e TCP servidor
            clientSocket.close();
            serverSocket.close();      
        } catch (IOException e) {
            System.err.println("Erro: " + e);
        }             
    }
}
