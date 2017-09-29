/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laboratorioHeroku;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author aypc
 */
public class ImprimirImagen {
    public static void imprimirImagen(String sfichero, Socket clientSocket, String inputLine) throws IOException{
        try{
            // Ahora leemos el fichero y lo retornamos
	    File mifichero = new File(sfichero) ;
            PrintStream out2=new PrintStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            String tipo = "";
            if(inputLine.contains("png") || inputLine.contains("PNG")){
                tipo = "png";
            }
            else if(inputLine.contains("jpg") || inputLine.contains("JPG")){
                tipo = "jpg";
            }
            else{
                tipo = "jpeg";
            }
            InputStream f=new FileInputStream(mifichero);
            out2.print("HTTP/1.0 200 OK\r\n"+
            "Content-type: image/"+tipo+"\r\n\r\n");
            byte[] a=new byte[4096];
            int n;
            while ((n=f.read(a))>0)
              out2.write(a, 0, n);
            out2.close();
            
	}
	catch(IOException e){
		System.out.println("Error al retornar fichero");
	}
    }
}
