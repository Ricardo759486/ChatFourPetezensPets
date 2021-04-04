package co.edu.unbosque.socketswiththreads;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import com.opencsv.CSVWriter;

public class Capitalizer implements Runnable {

	private Socket socketCliente;
	private Socket socketAgente;

	public Capitalizer(Socket socketCliente, Socket socketAgente) {
		this.socketCliente = socketCliente;
		this.socketAgente = socketAgente;
	}

	@Override
	public void run() {
		
		safePrintln("Connected: " + socketCliente);
		safePrintln("Connected: " + socketAgente);
		
		
		try {
			
			var scanner = new Scanner(System.in);
			
			var in = new Scanner(socketCliente.getInputStream());
			var out = new PrintWriter(socketCliente.getOutputStream(), true);
			
			var in1 = new Scanner(socketAgente.getInputStream());
			var out1 = new PrintWriter(socketAgente.getOutputStream(), true);
			
			out.println("Bienvenido al chat de 4 patas - (1) Crear caso. (2) Hablar con un agente.");
			
			while (in.hasNextLine()) {
				var mensaje = in.nextLine();

				switch (mensaje) {

				case "1":
		
					out.println("(1) Pérdida. (2) Robo. (3) Abandono. (4) Animal peligroso. (5) Manejo indebido en vía pública.");
					

					var desicion = in.nextLine();
					
					if (desicion.equals("1") || desicion.equals("2") || desicion.equals("3") || desicion.equals("4")
							|| desicion.equals("5")) {

						out.println("Por favor ingrese los siguientes datos:");
						out.println("Especie");						
						var especie = in.nextLine();
						out.println("Tamaño");
						var tamanio = in.nextLine();
						out.println("Localidad");
						var localidad = in.nextLine();
						out.println("Dirección");
						var direccion = in.nextLine();
						out.println("Nombre completo de la persona que reporta");
						var nombre = in.nextLine();
						out.println("Teléfono de la persona que reporta");
						var telefono = in.nextLine();
						out.println("Email de la persona que reporta");
						var email = in.nextLine();
						out.println("Comentarios generales");
						var comentario = in.nextLine();

						var id = Math.floor(Math.random() * (1000 - 1)) + 1;

						out.println("Su caso ha sido creado");

						out.println("Id: " + id + "   " + especie + tamanio + localidad + direccion + nombre
								+ telefono + email + comentario);
						
						CSVWriter writer = new CSVWriter (new FileWriter("Data//Case.csv"));
						String [] archivo = {especie, tamanio, localidad, direccion, nombre, telefono, email, comentario};
						writer.writeNext(archivo);
						writer.close();
						
					}

					break;

				case "2":

					out.println("Por favor espere a que un agente lo pueda atender.... No se desconecte");
					out1.println("(1) Aceptar. (2) Denegar.");
					
				
					if (in1.nextLine().equals("1")) {
						
						while (true) {
							var agente = in1.nextLine();
							out.println(agente);
							var cliente = in.nextLine();
							out1.println(cliente);
							
							
						}
					} else {
						out.println("En este momento no podemos comunicarlo con ningun asesor.");
					}

					break;

				default:

						out.println("Por favor ingrese una de los opciones(1 o 2).");

					}
				}
		} catch (Exception e) {
			safePrintln("Error:" + socketCliente);
			safePrintln("Error:" + socketAgente);
		} finally {
			try {
				socketCliente.close();
				socketAgente.close();
			} catch (IOException e) {
			}
			safePrintln("Closed: " + socketAgente);
			safePrintln("Closed: " + socketCliente);
		}
	}

	public void safePrintln(String s) {
		synchronized (System.out) {
			System.out.println(s);
		}
	}

}
