package me.pertzis.mantissa;

import me.pertzis.mantissa.socket.SocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class MantissaApplication {

	static Logger logger = Logger.getLogger(MantissaApplication.class.getName());

	public static void main(String[] args) {

		SpringApplication.run(MantissaApplication.class, args);

		try {
			SocketServer.initialize();
			logger.log(Level.INFO, "Server is listening on port...");
		} catch (IOException e) {
			System.err.println("Could not initialize socket server!");
			e.printStackTrace();
			return;
		}

		try {
			SocketServer.listen();
		} catch (Exception e) {
			System.err.println("Could not listen for new connections!");
			e.printStackTrace();
		}

	}

}
