/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servidorweb.lab6servidorweb;
import java.net.*;
import java.io.*;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author 2087559
 */
public class ServidorWeb {
    public ServidorWeb(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
//        while(true){
//            //ACA iban los hilos
//        }  
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    
}