package me.pertzis.mantissa;

import me.pertzis.mantissa.db.repositories.ClientRepository;
import me.pertzis.mantissa.server.MantissaServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class MantissaApplication {

	private static MantissaServer mantissaServer;

	@Autowired
	public MantissaApplication(MantissaServer mantissaServer) {
		MantissaApplication.mantissaServer = mantissaServer;
	}

	public static void main(String[] args) {
		SpringApplication.run(MantissaApplication.class, args);
		final int SOCKET_SERVER_PORT = 9999; // TODO: Grab port from database configuration.

		try {
			mantissaServer.bind(SOCKET_SERVER_PORT);
		} catch (IOException e) {
			System.err.printf("Could not bind socket to 0.0.0.0:%d:%n", SOCKET_SERVER_PORT);
			e.printStackTrace();
		}

		mantissaServer.accept();

	}

}
