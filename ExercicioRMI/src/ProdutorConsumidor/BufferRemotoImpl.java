/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProdutorConsumidor;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class BufferRemotoImpl implements BufferRemoto {
    private final List<String> buffer = new ArrayList<>();
    int limite = 5;
    
    @Override
    public boolean inserir(String item) throws RemoteException {
        if (buffer.size() < limite) {
            buffer.add(item);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public String remover() throws RemoteException {
        if (buffer.size() >= 1) {
            return buffer.remove(0);
        }
        else {
            return "ERRO! Buffer vazio!";
        }
    }
    
}