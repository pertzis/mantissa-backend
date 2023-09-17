package me.pertzis.mantissa.server.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.pertzis.mantissa.server.Client;
import me.pertzis.mantissa.server.ClientHandler;
import me.pertzis.mantissa.server.exceptions.ClientNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Utils {

    public static void sendMessage(Message message, OutputStream os) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(message);
        int messageLength = jsonMessage.length();
        String finalMessage = String.format("%-10d%s", messageLength, jsonMessage);
        os.write(finalMessage.getBytes(StandardCharsets.UTF_8));
    }
    public static void sendMessage(Message message, String clientId) throws Exception {
        while (ClientHandler.getClient(clientId).isLocked()) {
            continue;
        }
        ClientHandler.lockClient(clientId);
        Client client = ClientHandler.getClient(clientId);

        sendMessage(message, client.getSocket().getOutputStream());

        ClientHandler.unlockClient(clientId);
    }

    public static ClientMessage receiveMessage(InputStream is) throws IOException {
        byte[] header = is.readNBytes(10);
        final int messageLength = Integer.parseInt(new String(header, StandardCharsets.UTF_8).trim());
        byte[] encodedMessage = is.readNBytes(messageLength);
        String jsonMessage = new String(encodedMessage, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        ClientMessage clientMessage = objectMapper.readValue(jsonMessage, ClientMessage.class);
        return clientMessage;
    }
    public static ClientMessage receiveMessage(String clientId) throws Exception {
        while (ClientHandler.getClient(clientId).isLocked()) {}

        ClientHandler.lockClient(clientId);
        Client client = ClientHandler.getClient(clientId);

        ClientMessage clientMessage = receiveMessage(client.getSocket().getInputStream());

        ClientHandler.unlockClient(clientId);

        return clientMessage;
    }

    public static ClientMessage performConversation(Message message, Socket socket) throws IOException {
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        sendMessage(message, os);
        return receiveMessage(is);
    }
    public static ClientMessage performConversation(Message message, String clientId) throws Exception {
        while (ClientHandler.getClient(clientId).isLocked()) {}

        ClientHandler.lockClient(clientId);
        Client client = ClientHandler.getClient(clientId);

        ClientMessage clientMessage = performConversation(message, client.getSocket());

        ClientHandler.unlockClient(clientId);

        return clientMessage;

    }

}
