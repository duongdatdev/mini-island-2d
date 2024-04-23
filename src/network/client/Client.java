package network.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    /**
     * Creates a new instance of Client
     */

    private Socket clientSocket;
    private String hostName = "localhost";
    private int serverPort = 11111;
    private DataInputStream reader;
    private DataOutputStream writer;
    private Protocol protocol;

    private static Client client;

    private Client() throws IOException {
        protocol = new Protocol();
        clientSocket = new Socket(hostName, serverPort);
        writer = new DataOutputStream(clientSocket.getOutputStream());
    }

    public void register(int posX, int posY) throws IOException {


//        writer.writeUTF(protocol.RegisterPacket(posX, posY));

    }

    public void sendToServer(String message) {
        if (message.equals("exit"))
            System.exit(0);
        else {
            try {
                Socket s = new Socket(hostName, serverPort);
                System.out.println(message + " " + hostName + " " + serverPort);
                writer = new DataOutputStream(s.getOutputStream());
                writer.writeUTF(message);
            } catch (IOException ex) {

            }
        }

    }

    public DataOutputStream getWriter() {
        return writer;
    }

    public void setWriter(DataOutputStream writer) {
        this.writer = writer;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Socket getSocket() {
        return clientSocket;
    }

    public String getIP() {
        return hostName;    }

    public static Client getGameClient() {
        if (client == null)

            try {
                client = new Client();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        return client;
    }

    public void closeAll() {
        try {
            reader.close();
            writer.close();
            clientSocket.close();
        } catch (IOException ex) {

        }
    }
}
