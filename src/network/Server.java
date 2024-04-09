package network;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    //Server settings
    private final int PORT = 1234;
    private final int MAX_PLAYERS = 4;
    private final int TIMEOUT = 5000;
    private final int BUFFER_SIZE = 1024;
    private final int MAX_ATTEMPTS = 5;
    private final String SERVER_IP = "localhost";
    private final String DISCONNECT_MESSAGE = "DISCONNECT";
    private final String CONNECT_MESSAGE = "CONNECT";
    private final String READY_MESSAGE = "READY";
    private final String START_MESSAGE = "START";
    private final String END_MESSAGE = "END";
    private final String WIN_MESSAGE = "WIN";
    private final String LOSE_MESSAGE = "LOSE";
    private final String DRAW_MESSAGE = "DRAW";
    private final String ERROR_MESSAGE = "ERROR";
    private ServerSocket serverSocket;

    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            try {
                new ServerThread(serverSocket.accept()).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
