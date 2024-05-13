package network.client;


import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class GameSocket {
    private Socket socket;
    private String hostName = "localhost";
    private int serverPort = 11111;
    private DataInputStream reader;
    private DataOutputStream writer;

    public GameSocket() {
        try {
            socket = new Socket(hostName, serverPort);
            writer = new DataOutputStream(socket.getOutputStream());
            reader = new DataInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataOutputStream getWriter() {
        return writer;
    }

    public void setWriter(DataOutputStream writer) {
        this.writer = writer;
    }

    public DataInputStream getReader() {
        return reader;
    }

    public void setReader(DataInputStream reader) {
        this.reader = reader;
    }
}
