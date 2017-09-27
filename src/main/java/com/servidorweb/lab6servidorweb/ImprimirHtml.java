/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servidorweb.lab6servidorweb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 *
 * @author aypc
 */
public class ImprimirHtml {
    public static void imprimirHtml(String sfichero, PrintWriter out){
	// comprobamos si tiene una barra al principio
	if (sfichero.startsWith("/")){
		sfichero = sfichero.substring(1) ;
	}

        try{
	    // Ahora leemos el fichero y lo retornamos
	    File mifichero = new File(sfichero) ;
	    if (mifichero.exists()){
                    out.println("HTTP/1.0 200 ok");
                    out.println("Server: Juan Pablo Arevalo/1.0");
                    out.println("Date: "+new Date());
                    out.println("Content-Type: text/html");
                    out.println("Content-Length: " + mifichero.length());
                    out.println("\n");

                    BufferedReader ficheroLocal = new BufferedReader(new FileReader(mifichero));
                    String linea = "";
                    do{
			linea = ficheroLocal.readLine();
                        if (linea != null ){
                            out.println(linea);
			}
                    }while (linea != null);
                    ficheroLocal.close();
            }  // fin de si el fiechero existe
            else{
		System.out.println("No encuentro el fichero " + mifichero.toString());
            }
	}
	catch(IOException e){
		System.out.println("Error al retornar fichero");
	}
    }
}
