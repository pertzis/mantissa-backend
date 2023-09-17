package me.pertzis.mantissa.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private static ServerSocket serverSocket;
    private static int port = 9999;

    /**
     * Initializes the server socket and listens for incoming connections.
     * @throws IOException thrown if there was a problem binding the server socket to the specified port.
     */
    public static void initialize() throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public static void listen() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
        }
    }

}
