import java.net.*;
import java.io.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private String message;               	// Mensagem enviada pelo cliente
	private BufferedReader in;				// Entrada(recepcao) formatada de dados
	private PrintWriter out;				// Saida (envio) formatado de dados
    private Boolean isRunning = true;

    ClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
			// recebe mensagem de requisicao, escreve mensagem de requisicao e envia mensagem de resposta		
            System.out.println("TCP conectado...");

            // abre fluxos de entrada e saida de dados associados ao socket TCP de comunicacao com o cliente
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
                
            while(isRunning){
                try{
                    this.message = this.in.readLine();
                    String[] parseMessage = message.split(" ");
                    if(parseMessage[0].equals("exit")){
                        this.isRunning = false;
                        break;
                    }
                    if(parseMessage.length < 3) {
                        System.out.println("Mensagem com formatacao incorreta");
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
                            this.out.println(0);
                            continue;
                    }
                    System.out.println("Do cliente -> " + message + " resultando em " + String.valueOf(result));
                    this.out.println(result);		// envia mensagem de volta para o cliente
                } catch (IOException e) {
                    System.err.println("Erro de comunicação com o cliente: " + e);
                    this.isRunning = false;
                } 
            }

            // fecha fluxos de entrada e saida de dados
            this.out.close();
            this.in.close();

            // fecha sockets TCP de comunicacao com o cliente e TCP servidor
            this.clientSocket.close();  
        } catch (IOException e) {
            System.err.println("Erro ao fechar o socket do cliente: " + e);
        }
    }
}