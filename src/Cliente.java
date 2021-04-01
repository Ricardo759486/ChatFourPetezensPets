import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MarcoCliente mimarco = new MarcoCliente();

		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}

class MarcoCliente extends JFrame {

	public MarcoCliente() {

		setBounds(600, 300, 280, 350);

		LaminaMarcoCliente milamina = new LaminaMarcoCliente();

		add(milamina);

		setVisible(true);
	}

}

class LaminaMarcoCliente extends JPanel implements Runnable {

	public LaminaMarcoCliente() {

		nick = new JTextField(5);

		add(nick);

		JLabel texto = new JLabel("-CHAT-");

		add(texto);

		ip = new JTextField(8);

		add(ip);

		campochat = new JTextArea(12, 20);

		add(campochat);

		campo1 = new JTextField(20);

		add(campo1);

		miboton = new JButton("Enviar");

		enviarTexto mievento = new enviarTexto();

		miboton.addActionListener(mievento);

		add(miboton);
		
		Thread mihilo = new Thread(this);

		mihilo.start();
	}

	public class enviarTexto implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				Socket misocket = new Socket("192.168.10.13", 9999);

				paqueteEnvio datos = new paqueteEnvio();

				datos.setNick(nick.getText());
				datos.setIp(ip.getText());
				datos.setMensaje(campo1.getText());

				ObjectOutputStream paquete_datos = new ObjectOutputStream(misocket.getOutputStream());

				paquete_datos.writeObject(datos);

				misocket.close();

			} catch (IOException ioException) {
				ioException.printStackTrace();
			}

		}
	}

	private JTextField campo1, nick, ip;

	private JTextArea campochat;

	private JButton miboton;

	@Override
	public void run() {
		try {
			ServerSocket servidor_cliente = new ServerSocket(9090);

			Socket cliente;

			paqueteEnvio paqueteRecibido;

			while (true) {
				 
				cliente = servidor_cliente.accept();

				ObjectInputStream flujoentrada = new ObjectInputStream(cliente.getInputStream());

				paqueteRecibido = (paqueteEnvio) flujoentrada.readObject();

				campochat.append("\n" + paqueteRecibido.getNick() + ": " + paqueteRecibido.getMensaje());

			}

		} catch (Exception e) {

		}

	}

}

class paqueteEnvio implements Serializable {

	private String nick, ip, mensaje;

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}