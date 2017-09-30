/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laboratorioHeroku;
import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author 2087559
 */
public class ServidorWeb {
    public ServidorWeb(){
            File miDir = new File (".");
            try {
              System.out.println ("Directorio actual: " + miDir.getCanonicalPath());
              
              }
            catch(Exception e) {
              e.printStackTrace();
              }
            
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        boolean fin = false;    
        
        try {
            serverSocket = new ServerSocket(new Integer(System.getenv("PORT")));
        } catch (IOException e) {
            System.err.println("Could not listen on port.");
            System.exit(1);
        }
        ExecutorService exec = Executors.newFixedThreadPool(8);
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
