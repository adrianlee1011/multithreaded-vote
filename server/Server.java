/**
 * Name: Adrian Chen Young, Lee
 * Student ID: 201355786
 * 
 * Main file for the server side
*/

import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class Server {

	private ServerSocket server = null;
	private PollProtocol poll = null;
	private ExecutorService service = null;
	private int port = 7777;

	public Server(String[] args) {
		try {
			// open listening port
			server = new ServerSocket(port);
		}
		catch (IOException e) {
			System.err.println("Could not listen to port: " + port);
			System.exit(1);
		}

		// initialise protocol and passing in argument from command line
		poll = new PollProtocol(args);

		// initalise executor with fixed thread pool of 20
		service = Executors.newFixedThreadPool(20);

		// delete previous 'log.txt' file if it exists
		File file = new File("log.txt");
		file.delete();
	}

	public void runServer() throws IOException {
		// run the server and submit new handler for every new client
		while (true) {
			Socket client = server.accept();
			// pass in protocol as argument so that there is only one protocol for
			// the entire server
			service.submit(new PollClientHandler(client, poll));
		}
	}
	public static void main(String[] args) {
		// instantiate new Server object
		Server s = new Server(args);
		try {
			s.runServer();
		}
		catch (IOException e) {
			System.err.println("Cannot accept client");
			System.exit(1);
		}
	}
}