/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servidorweb.lab6servidorweb;
import java.net.*;
import java.io.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import sun.misc.Queue;
/**
 *
 * @author 2087559
 */
public class ServidorWeb {
    public ServidorWeb(){
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        boolean fin = false;    
        
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        ExecutorService exec = Executors.newFixedThreadPool(2);
        while(true || !fin) {
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

        exec.execute(new ThreadServer(clientSocket));
        }
        exec.shutdown();


        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    
}