package co.edu.unbosque.socketswiththreads;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CapitalizeClient {

	public static void main(String[] args) throws Exception {

		try (var socket = new Socket("127.0.0.1", 59897)) {

			System.out.println("Connected: " + socket);
			System.out.println("Enter the message to be capitalized...");
			System.out.println("Ciudadanos de 4 Patas");
			System.out.println("(1) Crear caso.\r\n" + "(2) Hablar con un agente.");

			var scanner = new Scanner(System.in);
			var in = new Scanner(socket.getInputStream());
			var out = new PrintWriter(socket.getOutputStream(), true);

			while (scanner.hasNextLine()) {
				out.println(scanner.nextLine());

				switch (in.nextLine()) {

				case "1":

					System.out.println("(1) Pérdida.\r\n" + "(2) Robo.\r\n" + "(3) Abandono.\r\n"
							+ "(4) Animal peligroso.\r\n" + "(5) Manejo indebido en vía pública.\r\n");

					out.println(scanner.nextLine());

					var desicion = in.nextLine();

					if (desicion.equals("1") || desicion.equals("2") || desicion.equals("3") || desicion.equals("4")
							|| desicion.equals("5")) {

						System.out.println("Por favor ingrese los siguientes datos:");
						System.out.println("Especie");
						out.println(scanner.nextLine());
						var especie = in.nextLine();
						System.out.println("Tamaño");
						out.println(scanner.nextLine());
						var tamanio = in.nextLine();
						System.out.println(tamanio);
						System.out.println("Localidad");
						out.println(scanner.nextLine());
						var localidad = in.nextLine();
						System.out.println("Dirección");
						out.println(scanner.nextLine());
						var direccion = in.nextLine();
						System.out.println("Nombre completo de la persona que reporta");
						out.println(scanner.nextLine());
						var nombre = in.nextLine();
						System.out.println("Teléfono de la persona que reporta");
						out.println(scanner.nextLine());
						var telefono = in.nextLine();
						System.out.println("Email de la persona que reporta");
						out.println(scanner.nextLine());
						var email = in.nextLine();
						;
						System.out.println("Comentarios generales");
						out.println(scanner.nextLine());
						var comentario = in.nextLine();

						var id = Math.floor(Math.random() * (1000 - 1)) + 1;

						System.out.println("Su caso ha sido creado");

						System.out.println("Id: " + id + "   " + especie + tamanio + localidad + direccion + nombre
								+ telefono + email + comentario);
					}

					break;

				case "2":

					System.out.println("Por favor espere a que un agente lo pueda atender....\n No se desconecte");

					var paqueteRecibido = in.nextInt();

					if (paqueteRecibido != 8) {

						while (true) {

							var mensajeAsesor = in.nextLine();

							if (!mensajeAsesor.equals("")) {

								System.out.println(mensajeAsesor);

								out.println(scanner.nextLine());

							}

						}

					} else {

						System.out.println("En este momento no podemos comunicarlo con ningun asesor.");
					}

					break;

				default:

					System.out.println("Por favor ingrese una de los opciones(1 o 2).");

				}

				System.out.println(in.nextLine());
				System.out.println(in.nextLine());
			}

		}

	}

}