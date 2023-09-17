package me.pertzis.mantissa.server;

import me.pertzis.mantissa.MantissaLogger;
import me.pertzis.mantissa.db.entities.ClientEntity;
import me.pertzis.mantissa.db.repositories.ClientRepository;
import me.pertzis.mantissa.server.messaging.ClientMessage;
import me.pertzis.mantissa.server.messaging.Message;
import me.pertzis.mantissa.server.messaging.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.ServerSocket;

@Component
public class MantissaServer {
    private ServerSocket socketServer;
    private ClientRepository clientRepository;


    @Autowired
    public MantissaServer(ClientRepository clientRepository) throws IOException {
        this.clientRepository = clientRepository;
    }

    private Logger logger = new MantissaLogger(MantissaServer.class.getName()).getLogger();
    private ArrayList<Client> clientList = new ArrayList();


    public void bind(int port) throws IOException {

        System.setProperty("javax.net.ssl.keyStore", "/Users/pertzis/Coding/mantissa/crypt/mantissa.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "M4nt1SSA!");

        SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        socketServer = sslServerSocketFactory.createServerSocket(port);
        logger.log(Level.INFO, String.format("Mantissa Server is listening on 0.0.0.0:%d...", port));
    }

    public void accept() {
        while (true) {
            try {
                Socket clientSocket = socketServer.accept();
                OutputStream clientOutputStream = clientSocket.getOutputStream();
                ClientMessage clientMessage = Utils.receiveMessage(clientSocket.getInputStream());

                String clientId = "";
                ClientEntity clientEntity;

                if (clientMessage.isSuccess()) {
                    if (!clientMessage.getResponse().containsKey("token")) {
                        clientId = UUID.randomUUID().toString();
                        try {
                            clientEntity = new ClientEntity(
                                    clientId,
                                    clientMessage.getResponse().get("hostname").toString(),
                                    clientMessage.getResponse().get("username").toString(),
                                    clientSocket.getInetAddress().toString()
                            );
                            clientRepository.save(clientEntity);
                        } catch (IllegalArgumentException e) {
                            HashMap<String, Object> response = new HashMap<>() {{
                                put("success", false);
                                put("error", e.toString());
                            }};
                            Utils.sendMessage(new Message("authenticate", response), clientOutputStream);
                            continue;
                        }
                    } else {
                        clientId = clientMessage.getResponse().get("token").toString();
                        Optional<ClientEntity> clientOptional = clientRepository.findById(clientId);
                        if (clientOptional.isEmpty()) {
                            HashMap<String, Object> response = new HashMap<>() {{
                                put("success", false);
                                put("error", "Invalid token present in request.");
                            }};
                            Utils.sendMessage(new Message("authenticate", response), clientOutputStream);
                            continue;
                        }
                        clientEntity = clientOptional.get();

                    }

                    Client authenticatedClient = new Client(clientId, clientSocket);
                    activateClient(authenticatedClient, clientEntity);
                    HashMap<String, Object> response = new HashMap<>() {{
                        put("success", true);
                        put("message", "You have successfully authenticated.");
                    }};
                    if(!clientMessage.getResponse().containsKey("token")) {
                        response.put("token", clientId);
                    }
                    Utils.sendMessage(new Message(clientMessage.getId(), "authenticate", response),
                            clientOutputStream);

                    new Thread(() -> checkConnection(authenticatedClient)).start();

                } else {
                    String warning = "Client could not authenticate with server";
                    if (clientMessage.getResponse().containsKey("error")) {
                        warning += ": " + clientMessage.getResponse().get("error").toString();
                    } else {
                        warning += "!";
                    }
                    logger.log(Level.WARNING, warning);
                }

            } catch (IOException e) {
                logger.log(Level.SEVERE, "Client could not be accepted due to exception!");
                e.printStackTrace();
            }
        }
    }

    private void activateClient(Client client, ClientEntity clientEntity) {
        clientEntity.setOnline(true);
        clientRepository.save(clientEntity);
        ClientHandler.addClient(client);
    }

    private void checkConnection(Client client) {
        logger.log(Level.INFO, String.format("[%s]: Guarding connection for disconnects.",
                client.getSocket().getInetAddress()));
        try {
            Utils.sendMessage(new Message("checkAlive"), client.getSocket().getOutputStream());
        } catch (Exception e) {
            Optional<ClientEntity> clientOptional = clientRepository.findById(client.getId());
            if (clientOptional.isPresent()) {
                ClientEntity clientEntity = clientOptional.get();
                clientEntity.setOnline(false);
                clientRepository.save(clientEntity);
                logger.log(Level.INFO, String.format("[%s]: Client disconnected.", client.getSocket().getInetAddress()));
            }
        }
    }

}
