
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Stack;


class SyncrozinedBuffer extends Buffer implements Runnable {
    private final Stack<String> buffer;
    private final int bufferSize;
    Socket socket;
    InputStream entrada;
    DataOutputStream saida;
    byte[] dadosRecebidos;
    byte[] dadosEnviados;
    String resposta;
    
    public SyncrozinedBuffer(Socket ss,Stack stack, final int bufferSize) throws Exception {
        this.socket = ss;
        this.buffer = stack;
        this.bufferSize = bufferSize;
        this.saida = new DataOutputStream(socket.getOutputStream());
        this.entrada = socket.getInputStream();
        this.dadosRecebidos = new byte[500];
        this.dadosEnviados = new byte[500];
        resposta = "";
    }
    
    
    @Override
    public synchronized void run() {
        Random gerador = new Random();
        while (true) {
            try {
                entrada.read(dadosRecebidos);
                String pacote = new String(dadosRecebidos);
                String[] dados = pacote.split(";");
                int servico = Integer.valueOf(dados[2]);
                
                
                switch(servico){
                    case 0:
                        if(buffer.size() < bufferSize){
                            resposta = "0;";
                            resposta += "0;";
                            resposta += "1;";
                            resposta += "Item " + dados[3] + " foi colocado por: " + dados[0];
                            buffer.push(dados[3]);
                            resposta += " - Quantidade de itens do buffer: " + buffer.size()+"\n";
                            System.out.println("Item " + dados[3] + " foi colocado por: " + dados[0]);
                            System.out.println("\nQuantidade de itens do buffer: " + buffer.size());
                            
                            
                        }else{
                            resposta = "0;";
                            resposta += "0;";
                            resposta += "2;";
                            resposta += "Falha ao colocar item por:" + dados[0] + "Buffer Cheio!\n";
                            System.out.println("Falha ao colocar item por:" + dados[0] + " Buffer Cheio!\n");
                            System.out.println("\nQuantidade de itens do buffer: " + buffer.size());
                            
                        }
                        break;
                    case 3:
                        if(!buffer.empty()){                         
                            resposta = "0;";
                            resposta += "0;";
                            resposta += "4;";
                            resposta += "O item "+buffer.peek()+ " foi retirado por: " + dados[0]+"\n";
                            System.out.println("O item "+buffer.peek()+ " foi retirado por: " + dados[0]);
                            buffer.pop();
                        }else{
                            resposta = "0;";
                            resposta += "0;";
                            resposta += "5;";
                            resposta += "Falha ao retirar item por:" + dados[0] + " Buffer Vazio!\n";
                            System.out.println("Falha ao retirar item por: " + dados[0] + " Buffer Vazio!");
                        }
                        break;
                     default:
                        resposta =  "0;";
                        resposta += "0;";
                        resposta += "null;";
                        resposta += "Requisição Incorreta!\n";
                        
                }
                dadosEnviados = resposta.getBytes();
                saida.write(dadosEnviados);
                saida.flush();
                Thread.sleep(gerador.nextInt((5000 - 1000) + 1) + 1000);
            } catch (Exception erro) {
                System.out.println("ERRO: " + erro.getMessage());
            }
        }
    }
}



public class Buffer {
        public static void main(String[] args) throws Exception 
    {
        System.out.println("Inicializando servidor TCP...");
        ServerSocket server = new ServerSocket(7777);
        System.out.println("Aguardando conexão na porta 7777...");
        Stack buffer = new Stack<String>();
        int bufferSize = 10;
        Thread myThreads[] = new Thread[100];
        
        int contThread = 0;
        while(true)
        {
            try
            {
                Socket socket = server.accept();
                
                System.out.println("\nNOVA REQUISÃO CAPTADA: Estabelecendo conexão...");
                System.out.println("Conexão estabelecida !");
                

                myThreads[contThread] = new Thread(new SyncrozinedBuffer(socket, buffer, bufferSize));
                myThreads[contThread].start();
                contThread++;
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }
}
