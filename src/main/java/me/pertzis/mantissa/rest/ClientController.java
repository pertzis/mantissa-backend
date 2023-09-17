package me.pertzis.mantissa.rest;

import me.pertzis.mantissa.db.entities.ClientEntity;
import me.pertzis.mantissa.db.repositories.ClientRepository;
import me.pertzis.mantissa.server.ClientHandler;
import me.pertzis.mantissa.server.messaging.ClientMessage;
import me.pertzis.mantissa.server.messaging.Message;
import me.pertzis.mantissa.server.messaging.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

@RestController
public class ClientController {
    ClientRepository clientRepository;
    @Autowired
    public ClientController(ClientRepository clientRepository) throws IOException {
        this.clientRepository = clientRepository;
    }
    @GetMapping("/screenshot")
    public ResponseEntity<Object> getScreenshot(
            @RequestParam String clientId,
            @RequestParam int monitor
    ) {
        HashMap<String, Object> response = new HashMap<>();
        if (!ClientHandler.clientExists(clientId)) {
            response.put("success", false);
            response.put("error", "Client does not exist, or is offline.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        HashMap<String, Object> parameters = new HashMap<>(){{
            put("monitor", monitor);
        }};
        Message serverMessage = new Message("getScreenshot", parameters);

        ClientMessage clientMessage;
        try {
            clientMessage = Utils.performConversation(serverMessage, clientId);
        } catch (Exception e) {
            System.out.println(e);
            response.put("success", false);
            response.put("error", "Could not perform successful communication with requested client.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!clientMessage.isSuccess()) {
            response.put("success", false);
            response.put(
                    "error",
                    clientMessage.getResponse().getOrDefault(
                            "error",
                            "The client encountered an error while performing requested action."
                    )
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        byte[] byteImage = Base64.getDecoder().decode(clientMessage.getResponse().get("screenshot").toString());

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(byteImage.length)
                .contentType(MediaType.IMAGE_PNG)
                .body(byteImage);
    }
    
    @GetMapping("/clients")
    public ResponseEntity<Object> getClients() {
        Iterable<ClientEntity> clientIterable = clientRepository.findAll();
        HashMap<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("clients", clientIterable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
