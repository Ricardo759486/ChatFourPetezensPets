package co.edu.unbosque.socketswiththreads;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Capitalizer implements Runnable {

    private Socket socketCliente;
   // private Socket socketAgente;

    public Capitalizer(Socket socketCliente/*, Socket socketAgente*/) {
        this.socketCliente = socketCliente;
     //   this.socketAgente = socketAgente;
    }

    @Override
    public void run() {

        safePrintln("Connected: " + socketCliente);
       // safePrintln("Connected: " + socketAgente);

        try {

            var in = new Scanner(socketCliente.getInputStream());
            var out = new PrintWriter(socketCliente.getOutputStream(), true);
            int contador= 0;
            
//            var inA = new Scanner(socketAgente.getInputStream());
//            var outA = new PrintWriter(socketAgente.getOutputStream(), true);
//            
//            var validacion = 0;
//            var turno  = 1;

            while (in.hasNextLine()) {
            	
            	//if(validacion == 0) {
            		//var desicion = in.nextInt();
            	//	if(desicion == 2) {
            			
            	//		var mensajeAgente = "(1) Aceptar (2) Denegar";
            //			outA.println(mensajeAgente);
            	//		out.println(desicion);
            		//	var aceptacion = inA.nextInt();
            			
            	//		if(aceptacion == 1) {
            				
            	//			validacion = 3;
//            				out.println(9);
//            				
//            			}else{
//            				
//            			out.println(8);
//            				
//            			}
//            		}
//            		
//            	}
            	
//            	if(validacion == 3) {
//            		
//            		if(turno == 1) {
//            			var agente = inA.nextLine();
//            			out.println(agente + 1);
//            			turno = 2;
//            			break;
//            		}
//            		
//            		if(turno == 2) {
//            			var cliente = in.nextLine();
//            			outA.println(cliente +2);
//            			turno = 1;
//            		}
//            	}else if(validacion == 0){
            	
            	if(contador <= 2) {
            		var desicion = in.nextLine();
                    safePrintln("The message received is: " + desicion);
                    
                    //safePrintln("The message received is: " + message2);
                    //var newMessage = message2.toUpperCase();
                    safePrintln("The message to be returned is: " + desicion);
                    out.println(desicion);
                    contador++;
            		
            	}else {
            		
            	
            	in.nextLine();
            	var desicion = in.nextLine();
                safePrintln("The message received is: " + desicion);
                
                //safePrintln("The message received is: " + message2);
                //var newMessage = message2.toUpperCase();
                safePrintln("The message to be returned is: " + desicion);
                out.println(desicion);
                contador++;
            	
            	}	
            		
            		
            	//} 
            }

        } catch (Exception e) {
            safePrintln("Error:" + socketCliente);
            //safePrintln("Error:" + socketAgente);
        } finally {
            try {
                socketCliente.close();
                //socketAgente.close();
            } catch (IOException e) {
            }
            //safePrintln("Closed: " + socketAgente);
            safePrintln("Closed: " + socketCliente);
        }
    }

    public void safePrintln(String s) {
        synchronized (System.out) {
            System.out.println(s);
        }
    }

}