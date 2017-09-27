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
        try {
            ServerSocket serverSocket = null;
            Socket clientSocket = null;
            PrintWriter out = null;
            
            BufferedReader in  = null;
            while(true){
                serverSocket = null;
                try {
                    serverSocket = new ServerSocket(35000);
                } catch (IOException e) {
                    System.err.println("Could not listen on port: 35000.");
                    System.exit(1);
                }
                clientSocket = null;
                try {
                    System.out.println("Listo para recibir ...");
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }
                
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                int i=0;
                while ((inputLine = in.readLine()) != null) {
                    if(i==0){
                        StringTokenizer st = new StringTokenizer(inputLine);
                        if ((st.countTokens() >= 2) && st.nextToken().equals("GET")){
                            if(inputLine.contains("html")){
                                ImprimirHtml.imprimirHtml(st.nextToken(), out) ; 
                                
                            }
                            else{
                                ImprimirImagen.imprimirImagen(st.nextToken(), clientSocket, inputLine) ;  
                            }
                        }
                        else{
                            out.println("400 Peticion Erronea") ;
                        }
                    }
                    i++;
                    System.out.println("Received: " + inputLine);
                    if (!in.ready()) {
                        break;
                    }
                }
                
                out.close();
                in.close();
                clientSocket.close();
                serverSocket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorWeb.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    
    
    
}