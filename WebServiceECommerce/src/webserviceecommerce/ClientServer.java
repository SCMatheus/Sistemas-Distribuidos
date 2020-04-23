/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserviceecommerce;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
 
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ClientServer {
  @WebMethod String listarIten(int id);
  @WebMethod String listarTodosOsItens();
  @WebMethod boolean comprarIten(int id, int qnt);
}
