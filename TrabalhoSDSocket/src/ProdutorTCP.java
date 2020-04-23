import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


public class ProdutorTCP 
{
    public static void main(String[] args) throws IOException 
    {
        try
        {
            int id = new java.util.Random().nextInt(100);
            System.out.println("Conectando ao servidor...");
            Socket conexaoServidor = new Socket("127.0.0.1", 7777);
            System.out.println("Conexão estabelecida com sucesso!");
            OutputStream saida = conexaoServidor.getOutputStream();
            BufferedReader resposta = new BufferedReader(new InputStreamReader(conexaoServidor.getInputStream()));
            String mensagem = "";

            while(true) 
            {
                int dado = new java.util.Random().nextInt(1000);
                String mensagemProtocol = id + ";"; //ID
                mensagemProtocol += "1;";                        //Tipo cliente worker
                mensagemProtocol += "0;";                        //Tipo operação "armazenar"
                mensagemProtocol += dado;     //Item produzido
                byte[] dadosEnviados = mensagemProtocol.getBytes();

                saida.write(dadosEnviados);
                saida.flush();
                System.out.println("\nRequisição do produtor: " + id + " enviada com sucesso");

                mensagem = resposta.readLine();
                String[] in = mensagem.split(";");
                int servico = Integer.valueOf(in[2]);

                switch(servico)
                {
                    case 1: System.out.println("Item produzido com sucesso!");
                              System.out.println("Resposta do servidor:\n " + in[3]);
                        break;
                    case 2: System.out.println("Erro!");
                            System.out.println("Resposta do servidor:\n " + in[3]);
                        break;
                    default: System.out.println("Erro inesperado");
                }
            }
        }
        catch(java.net.SocketException e)
        {
            System.out.println("Conexão perdida!");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
