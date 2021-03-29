import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

//Class Server
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6666);
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                System.out.println("A new client is connected: " + socket);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.println("Assigning new thread for this client.");
                Thread threadNew = new ClientHandler(socket, in, out);
                threadNew.start();
            } catch (Exception e) {
                socket.close();
                e.printStackTrace();
            }
        }
    }
}

// Class ClientHandler
class ClientHandler extends Thread {
    DateFormat date = new SimpleDateFormat("yyy/MM/dd");
    DateFormat time = new SimpleDateFormat("hh:mm:ss");
    DataInputStream dataIn;
    DataOutputStream dataOut;
    Socket socket;

    public ClientHandler(Socket socket, DataInputStream dataIn, DataOutputStream dataOut) {
        this.socket = socket;
        this.dataIn = dataIn;
        this.dataOut = dataOut;
    }

    @Override
    public void run() {
        String received;
        String toReturn;
        while (true) {
            try {
                dataOut.writeUTF("What do you want? [Date or Time]\n" + "Type Exit to terminated connection.");
                received = dataIn.readUTF();
                if (received.equals("Exit")) {
                    System.out.println("Client " + this.socket + " exit!");
                    System.out.println("Closing connection!");
                    this.socket.close();
                    System.out.println("Connection closed.");
                    break;
                }
                Date dateF = new Date();
                switch (received) {
                    case "Date":
                        toReturn = date.format(dateF);
                        dataOut.writeUTF(toReturn);
                        break;
                    case "Time":
                        toReturn = time.format(dateF);
                        dataOut.writeUTF(toReturn);
                        break;
                    default:
                        dataOut.writeUTF("Invalid input!");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.dataIn.close();
            this.dataOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
