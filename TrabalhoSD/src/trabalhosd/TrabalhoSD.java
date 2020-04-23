/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhosd;


import java.util.Random;
import java.util.Stack;

class WorkerThread extends TrabalhoSD implements Runnable {
    String nome;
    final int bufferSize;
    Stack stack;
    public WorkerThread(String nome,Stack stack, final int bufferSize) {
        this.nome = nome;
        this.stack = stack;
        this.bufferSize = bufferSize;
    }
    
    @Override
    public synchronized void run() {
        Random gerador = new Random();
        while (true) {
            if(stack.size() >= bufferSize){
                System.out.println(nome + " está esperando! Buffer: " + stack.size());
            }else{
                stack.push(gerador.nextInt(1000));
                System.out.println(nome + " está produzindo! Buffer: " + stack.size());
            }
            try {
                Thread.sleep(gerador.nextInt((5000 - 1000) + 1) + 1000);
            }
            catch (Exception erro) {
                System.out.println("Deu pau no Worker");
            }
        }
    }
}

class ConsumerThread extends TrabalhoSD implements Runnable {
    String nome;
    Stack stack;
    public ConsumerThread(String nome, Stack stack) {
        this.nome = nome;
        this.stack = stack;
    }
    
    @Override
    public synchronized void run() {
        Random gerador = new Random();
        while (true) {
            if(stack.empty()){
                System.out.println(nome + " está esperando! Buffer: " + stack.size());
            }else{
                System.out.println(nome + " está consumindo! Buffer: " + stack.size() );
                stack.pop();
            }
            try {
                Thread.sleep(gerador.nextInt((5000 - 1000) + 1) + 1000);
            }
            catch (Exception erro) {
                System.out.println("Deu pau no Consumer");
            }
        }
    }
}

public class TrabalhoSD {
    public static void main(String[] args) {
        final int bufferSize = 10;

        Stack stack = new Stack();
        
        WorkerThread Worker = new WorkerThread("Worker", stack, bufferSize);
        WorkerThread Worker_2 = new WorkerThread("Worker_2", stack, bufferSize);
        
        ConsumerThread Consumer = new ConsumerThread("Consumer", stack);
        ConsumerThread Consumer_2 = new ConsumerThread("Consumer_2", stack);
        
        Thread tWorker = new Thread(Worker);
        Thread tWorker_2 = new Thread(Worker_2);
        
        Thread tConsumer = new Thread(Consumer);
        Thread tConsumer_2 = new Thread(Consumer_2);
        
        tWorker.start();
        tWorker_2.start();
        
        tConsumer.start();
        tConsumer_2.start();
        
    }
}
