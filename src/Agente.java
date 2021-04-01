import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Agente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MarcoAgente mimarco2 = new MarcoAgente();

		mimarco2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}

class MarcoAgente extends JFrame {

	public MarcoAgente() {

		setBounds(600, 300, 280, 350);

		LaminaMarcoAgente milamina2 = new LaminaMarcoAgente();

		add(milamina2);

		setVisible(true);
	}

}

class LaminaMarcoAgente extends JPanel implements Runnable {

	public LaminaMarcoAgente() {

		nick2 = new JTextField(5);

		add(nick2);

		JLabel texto2 = new JLabel("-CHAT-");

		add(texto2);

		ip2 = new JTextField(8);

		add(ip2);

		campochat2 = new JTextArea(12, 20);

		add(campochat2);

		campo2 = new JTextField(20);

		add(campo2);

		miboton2 = new JButton("Enviar");

		enviarTexto2 mievento2 = new enviarTexto2();

		miboton2.addActionListener(mievento2);

		add(miboton2);
		
		Thread mihilo2 = new Thread(this);

		mihilo2.start();
	}

	public class enviarTexto2 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				Socket misocket2 = new Socket("192.168.10.13", 6666);

				paqueteEnvio datos2 = new paqueteEnvio();

				datos2.setNick(nick2.getText());
				datos2.setIp(ip2.getText());
				datos2.setMensaje(campo2.getText());

				ObjectOutputStream paquete_datos2 = new ObjectOutputStream(misocket2.getOutputStream());

				paquete_datos2.writeObject(datos2);

				misocket2.close();

			} catch (IOException ioException) {
				ioException.printStackTrace();
			}

		}
	}

	private JTextField campo2, nick2, ip2;

	private JTextArea campochat2;

	private JButton miboton2;

	@Override
	public void run() {
		try {
			ServerSocket servidor_cliente2 = new ServerSocket(9089);

			Socket cliente2;

			paqueteEnvio paqueteRecibido2;

			while (true) {
				 
				cliente2 = servidor_cliente2.accept();

				ObjectInputStream flujoentrada2 = new ObjectInputStream(cliente2.getInputStream());

				paqueteRecibido2 = (paqueteEnvio) flujoentrada2.readObject();

				campochat2.append("\n" + paqueteRecibido2.getNick() + ": " + paqueteRecibido2.getMensaje());

			}

		} catch (Exception e) {

		}

	}

}

//class paqueteEnvio2 implements Serializable {
//
//	private String nick, ip, mensaje;
//
//	public String getNick() {
//		return nick;
//	}
//
//	public void setNick(String nick) {
//		this.nick = nick;
//	}
//
//	public String getIp() {
//		return ip;
//	}
//
//	public void setIp(String ip) {
//		this.ip = ip;
//	}
//
//	public String getMensaje() {
//		return mensaje;
//	}
//
//	public void setMensaje(String mensaje) {
//		this.mensaje = mensaje;
//	}
//}
