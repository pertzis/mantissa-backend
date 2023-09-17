package me.pertzis.mantissa.server;

import java.net.Socket;
import java.util.Formatter;

public class Client {
    private String id;
    private Socket socket;
    private boolean locked = false;

    public Client(String id, Socket socket) {
        this.id = id;
        this.socket = socket;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
