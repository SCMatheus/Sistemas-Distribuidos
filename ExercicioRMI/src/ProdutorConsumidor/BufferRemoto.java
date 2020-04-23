/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProdutorConsumidor;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BufferRemoto extends Remote {
    public boolean inserir(String item) throws RemoteException;
    public String remover() throws RemoteException;
}