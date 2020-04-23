/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProdutorConsumidor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

/**
 *
 * @author mathe
 */
public class Consumidor {
        public static void main(String[] args) throws InterruptedException {
        try {
            Random gerador = new Random();
            int ID = gerador.nextInt(100);
            Registry registry = LocateRegistry.getRegistry("localhost", 1234);
            BufferRemoto stub = (BufferRemoto) registry.lookup("ProdConsu");
            while(true){
                System.out.println(stub.remover() + " por: " + ID);
            
                Thread.sleep(gerador.nextInt((5000 - 1000) + 1) + 1000);
            }
            }
            catch (Exception e) {
                    System.err.println("Consumidor exception: " + e.toString());
            }
    }
}
