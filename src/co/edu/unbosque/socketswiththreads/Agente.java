package co.edu.unbosque.socketswiththreads;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Agente {

	public static void main(String[] args) throws Exception {

		try (var socket = new Socket("127.0.0.1", 9999)) {

			System.out.println("Connected: " + socket);
	

			var scanner = new Scanner(System.in);
			var in = new Scanner(socket.getInputStream());
			var out = new PrintWriter(socket.getOutputStream(), true);
			System.out.println(in.nextLine());
			
			out.println(scanner.nextLine());
			String mensaje = "";
            while (true) {
                mensaje = scanner.nextLine();
                out.println(mensaje);
                System.out.println(in.nextLine());
            }

		  }

		}

	}