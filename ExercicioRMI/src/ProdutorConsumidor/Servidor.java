/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProdutorConsumidor;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
public class Servidor implements Remote{
    public static void main(String[] args) {
        try {

            BufferRemotoImpl objeto = new BufferRemotoImpl();
            BufferRemoto stub = (BufferRemoto) UnicastRemoteObject.exportObject(objeto, 0);
            Naming.rebind("rmi://localhost:1234/ProdConsu",stub);

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}