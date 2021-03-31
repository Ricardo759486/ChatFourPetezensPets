import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoCliente mimarco=new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}


class MarcoCliente extends JFrame{
	
	public MarcoCliente(){
		
		setBounds(600,300,280,350);
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);

		addWindowListener(new envioOnline());
		}	
	
}
//---------------------------------------------ENVIO SEÑAL ONLINE------------------------------
class envioOnline extends  WindowAdapter{

    public void windowOpened(WindowEvent e){

        try{
            Socket misocket = new Socket("192.168.10.18", 9999);

            paqueteEnvio datos = new paqueteEnvio();

            datos.setMensaje(" online");

            ObjectOutputStream paquete_datos = new ObjectOutputStream(misocket.getOutputStream());

            paquete_datos.writeObject(datos);

            misocket.close();

        }catch (Exception e2){

        }
    }
}
//---------------------------------------------------------------------------------------------------------

class LaminaMarcoCliente extends JPanel implements Runnable{
	
	public LaminaMarcoCliente(){

	    String nick_Usuario = JOptionPane.showInputDialog("Nick: ");

	    JLabel n_nick = new JLabel("Nick: ");

	    add(n_nick);

	    nick = new JLabel();

	    nick.setText(nick_Usuario);

	    add(nick);
	
		JLabel texto=new JLabel("Online: ");
		
		add(texto);

		ip = new JComboBox();

		add(ip);

		campochat = new JTextArea(12,20);

		add(campochat);
	
		campo1=new JTextField(20);
	
		add(campo1);		
	
		miboton=new JButton("Enviar");

		enviarTexto mievento=new enviarTexto();

		miboton.addActionListener(mievento);
		
		add(miboton);

		Thread mihilo = new Thread(this);

		mihilo.start();
	}

    @Override
    public void run() {
	    try{
	        ServerSocket servidor_cliente = new ServerSocket(9090);

	        Socket cliente;

	        paqueteEnvio paqueteRecibido;

	        while(true){
	            cliente = servidor_cliente.accept();

                ObjectInputStream flujoentrada = new ObjectInputStream(cliente.getInputStream());

                paqueteRecibido = (paqueteEnvio) flujoentrada.readObject();

                if(!paqueteRecibido.getMensaje().equals(" online")){

					campochat.append("\n" + paqueteRecibido.getNick() + ": " + paqueteRecibido.getMensaje());

				}else{
					//campochat.append("\n" + paqueteRecibido.getIps());
					ArrayList<String> ipsMenu = new ArrayList<String>();
					ipsMenu = paqueteRecibido.getIps();
					ip.removeAllItems();

					for (String z:ipsMenu){
						ip.addItem(z);
					}
				}
            }

        }catch (Exception e){

        }
    }

    public class enviarTexto implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

		    campochat.append("\n" + campo1.getText());

			try {
				Socket misocket = new Socket("192.168.10.18",9999);

				paqueteEnvio datos = new paqueteEnvio();

				datos.setNick(nick.getText());
				datos.setIp(ip.getSelectedItem().toString());
				datos.setMensaje(campo1.getText());

                ObjectOutputStream paquete_datos=new ObjectOutputStream(misocket.getOutputStream());

                paquete_datos.writeObject(datos);

                misocket.close();

				//DataOutputStream flujo_salida = new DataOutputStream(misocket.getOutputStream());

				//flujo_salida.writeUTF(campo1.getText());

				//flujo_salida.close();

			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}


	private JTextField campo1;

	private JComboBox ip;

	private JLabel nick;

	private JTextArea campochat;
	
	private JButton miboton;
}

class paqueteEnvio implements Serializable {

    private String  nick, ip, mensaje;

    private ArrayList<String> ips;

	public ArrayList<String> getIps() {
		return ips;
	}

	public void setIps(ArrayList<String> ips) {
		this.ips = ips;
	}

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