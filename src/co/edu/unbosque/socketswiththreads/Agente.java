package co.edu.unbosque.socketswiththreads;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Agente {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		try (var socket = new Socket("127.0.0.1", 9999)) {

			System.out.println("Connected: " + socket);

			var scannerA = new Scanner(System.in);
			var inA = new Scanner(socket.getInputStream());
			var outA = new PrintWriter(socket.getOutputStream(), true);

			var paqueteRecibido = inA.nextLine();

			String[] separados = paqueteRecibido.split(" ");

			System.out.println("" + separados[0] + " " + separados[1] + "\n" + separados[2] + " " + separados[3]);
			
			
			int aceptacion = scannerA.nextInt();
			outA.println(aceptacion);
			
			if (aceptacion == 1) {
					System.out.print("xd");
					while(inA.hasNextLine()) {
					outA.println(scannerA.nextLine());
					System.out.println(inA.nextLine());
					}
			}

//			while (scanner.hasNextLine()) {
//
//				out.println();
//				
//				var mensajeCliente = in.nextLine();
//				System.out.println(mensajeCliente);
//				out.println(scanner.nextLine());
//
//				out.println(scanner.nextLine());
//				
//				System.out.println(in.nextLine());
//				System.out.println(in.nextLine());
//			}

		}

	}

}