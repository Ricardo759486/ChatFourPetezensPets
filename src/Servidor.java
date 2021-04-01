import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Servidor  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}	
}

class MarcoServidor extends JFrame implements Runnable{
	
	public MarcoServidor(){
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);
		
		Thread mihilo = new Thread(this);

		mihilo.start();
		
		}
	

	@Override
	public void run() {
		
		try {
			    ServerSocket servidorCliente = new ServerSocket(9999);
			    ServerSocket servidorAgente = new ServerSocket(6666);
			    
			    String nick, ip, mensaje;
			    String nick2, ip2, mensaje2;

				paqueteEnvio paquete_recibido1;
				paqueteEnvio paquete_recibido2;
			    
			    while(true) {
			    
			    	Socket misocketCliente = servidorCliente.accept();
			    	Socket misocketAgente = servidorAgente.accept();

					ObjectInputStream paquete_datosCliente = new ObjectInputStream(misocketCliente.getInputStream());
					ObjectInputStream paquete_datosAgente = new ObjectInputStream(misocketAgente.getInputStream());

					paquete_recibido1 = (paqueteEnvio) paquete_datosCliente.readObject();
					paquete_recibido2 = (paqueteEnvio) paquete_datosAgente.readObject();

					nick = paquete_recibido1.getNick();
					ip = paquete_recibido1.getIp();
					mensaje = paquete_recibido1.getMensaje();
					
					nick2 = paquete_recibido2.getNick();
					ip2 = paquete_recibido2.getIp();
					mensaje2 = paquete_recibido2.getMensaje();

					/*DataInputStream flujo_entrada = new DataInputStream(misocket.getInputStream());

					String mensaje_texto = flujo_entrada.readUTF();

					areatexto.append("\n" + mensaje_texto);*/

					areatexto.append("\n" + nick + ": " + mensaje + " para " + ip);
					areatexto.append("\n" + nick2 + ": " + mensaje2 + " para " + ip2);

					Socket enviaDestinatarioCliente = new Socket(ip, 9090);
					Socket enviaDestinatarioAgente = new Socket(ip, 9089);

					ObjectOutputStream paqueteReenvioCliente = new ObjectOutputStream(enviaDestinatarioCliente.getOutputStream());
					ObjectOutputStream paqueteReenvioAgente = new ObjectOutputStream(enviaDestinatarioAgente.getOutputStream());

					paqueteReenvioCliente.writeObject(paquete_recibido1);
					paqueteReenvioAgente.writeObject(paquete_recibido2);

					paqueteReenvioCliente.close();
					paqueteReenvioAgente.close();

					enviaDestinatarioCliente.close();
					enviaDestinatarioAgente.close();

					misocketCliente.close();
					misocketAgente.close();
				
			    }

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		
		
	}
	
	private	JTextArea areatexto;
}
