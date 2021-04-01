package co.edu.unbosque.socketswiththreads;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Agente {

    public static void main(String[] args) throws Exception {

        try (var socket = new Socket("127.0.0.1", 9999)) {

            System.out.println("Connected: " + socket);
            System.out.println("Enter the message to be Cliente...");
            
            var scanner = new Scanner(System.in);
            var in = new Scanner(socket.getInputStream());
            var out = new PrintWriter(socket.getOutputStream(), true);
            
//            var paqueteRecibido = in.nextLine();
//            
//            String[] separados = paqueteRecibido.split(" ");
//            
//            System.out.println("" + separados[0] + " " + separados[1] + "\n" + separados[2] + " " + separados[3]);

            while (scanner.hasNextLine()) {
            	
//            	out.println(scanner.nextLine());
//            	
//            	var mensajeCliente = in.nextLine();
//      		  
//                System.out.println(mensajeCliente);
//                
//                out.println(scanner.nextLine());
                
//            	out.println(scanner.nextLine());
//            	System.out.println(in.nextLine());
            	//System.out.println(in.nextLine());
            }

        }

    }

}
