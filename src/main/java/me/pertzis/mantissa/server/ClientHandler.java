package me.pertzis.mantissa.server;

import me.pertzis.mantissa.server.exceptions.ClientNotFoundException;
import me.pertzis.mantissa.server.messaging.Message;
import me.pertzis.mantissa.server.messaging.Utils;

import java.util.ArrayList;

public class ClientHandler {
    public static ArrayList<Client> clientList = new ArrayList<>();

    public static void addClient(Client client) {
        clientList.add(client);
    }

    public static Client getClient(String clientId) throws ClientNotFoundException {
        for (Client client : clientList) {
            if (client.getId().equals(clientId)) {
                return client;
            }
        }
        throw new ClientNotFoundException("Client not found in online client list.");
    }

    public static void lockClient(Client client) throws ClientNotFoundException {
        int idx = clientList.indexOf(client);
        if (idx == -1) {
            throw new ClientNotFoundException("Client not found in online client list.");
        }
        Client updatedClient = clientList.get(idx);
        updatedClient.setLocked(true);
        clientList.set(idx, updatedClient);
    }
    public static void lockClient(String clientId) throws ClientNotFoundException {
        Client client = getClient(clientId);
        int idx = clientList.indexOf(client);
        if (idx == -1) {
            throw new ClientNotFoundException("Client not found in online client list.");
        }
        Client updatedClient = clientList.get(idx);
        updatedClient.setLocked(true);
        clientList.set(idx, updatedClient);
    }

    public static void unlockClient(Client client) throws ClientNotFoundException {
        int idx = clientList.indexOf(client);
        if (idx == -1) {
            throw new ClientNotFoundException("Client not found in online client list.");
        }
        Client updatedClient = clientList.get(idx);
        updatedClient.setLocked(false);
        clientList.set(idx, updatedClient);
    }
    public static void unlockClient(String clientId) throws ClientNotFoundException {
        Client client = getClient(clientId);

        int idx = clientList.indexOf(client);
        if (idx == -1) {
            throw new ClientNotFoundException("Client not found in online client list.");
        }
        Client updatedClient = clientList.get(idx);
        updatedClient.setLocked(false);
        clientList.set(idx, updatedClient);
    }

    public static boolean clientExists(String clientId) {
        for (Client client : clientList) {
            if (client.getId().equals(clientId)) {
                return true;
            }
        }
        return false;
    }

}
