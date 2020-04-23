/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserviceadmin;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 *
 * @author mathe
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface AdminServer {
  @WebMethod String listarIten(int id);
  @WebMethod String listarCompras();
  @WebMethod String listarTodosOsItens();
  @WebMethod boolean excluirIten(int id);
  @WebMethod boolean alterarNomeIten(int id, String nome );
  @WebMethod boolean alterarQuantidadeIten(int id, int quantidade );
  @WebMethod boolean alterarVendasIten(int id, int vendas );
  @WebMethod boolean inserirIten(int id, String nome, int quantidade, int vendas);
}
