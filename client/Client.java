/**
 * Name: Adrian Chen Young, Lee
 * Student ID: 201355786
 * 
 * This file provides functionality for client side of server
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	
	private Socket pollSocket = null;
	private PrintWriter socketOutput = null;
	private BufferedReader socketInput = null;
	private int port = 7777;

	public void start(String[] args) {
		try {
			// create socket with hostname "localhost" and specified port
			pollSocket = new Socket("localhost", port);
			socketOutput = new PrintWriter(pollSocket.getOutputStream(), true);
			socketInput = new BufferedReader(
				new InputStreamReader(pollSocket.getInputStream()));
		}
		catch (UnknownHostException e) {
			System.err.println("Invalid host\n");
			System.exit(1);
		}
		catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to host\n");
			System.exit(1);
		}

		String fromServer;

		
		try {
			// if no argument from user, exit and do not connect to server
			if (args.length > 0) {
				// join the args array into a string and write to server
				String fromUser = String.join(",", args);
				socketOutput.println(fromUser);
			}
			else {
				System.out.println("No argument found. Exiting.");
				System.exit(0);
			}

			// print server output
			while ((fromServer = socketInput.readLine()) != null) {
				// if empty line detected, break the loop
				if (fromServer.isEmpty()) {
					break;
				}
				// print server output to console
				System.out.println(fromServer);
			}
			socketOutput.close();
			socketInput.close();
			pollSocket.close();
		}
		catch(IOException e) {
			System.err.println("I/O exception during execution\n");
			System.exit(1);
		}


	}
	public static void main(String[] args) {
		// instantiate new Client object and call function
		Client c = new Client();
		c.start(args);
	}
}
