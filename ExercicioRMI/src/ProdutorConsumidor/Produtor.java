/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProdutorConsumidor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
public class Produtor {
    public static void main(String[] args) throws InterruptedException{
        try {
                Registry registry = LocateRegistry.getRegistry("localhost", 1234);
                BufferRemoto stub = (BufferRemoto) registry.lookup("ProdConsu");
                Random gerador = new Random();
                int ID = gerador.nextInt(100);
                String aux = "";
                while(true){
                    aux = String.valueOf(gerador.nextInt(1000));
                    if(stub.inserir(aux)){
                        System.out.println("Item " + aux + " inserido por: " + ID);
                    }else{
                        System.out.println("BUFFER CHEIO");
                    }
                    Thread.sleep(gerador.nextInt((5000 - 1000) + 1) + 1000);
                }
               
            } catch (Exception e) {
                System.err.println("Produtor exception: " + e.toString());
                e.printStackTrace();
            }
    }
}
