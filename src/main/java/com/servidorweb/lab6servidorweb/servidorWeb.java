/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorWeb;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.net.*;
import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import sun.misc.IOUtils;
/**
 *
 * @author 2087559
 */
public class servidorWeb {
    public static void main(String[] args){
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
                                imprimirFichero(st.nextToken(), out) ; 
                                
                            }
                            else{
                                imprimirImagen(st.nextToken(), clientSocket, inputLine) ;  
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
            Logger.getLogger(servidorWeb.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
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
    
    public static void imprimirFichero(String sfichero, PrintWriter out){
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
