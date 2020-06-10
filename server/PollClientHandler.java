/**
 * Name: Adrian Chen Young, Lee
 * Student ID: 201355786
 * 
 * This file provides a single handler for a single client
 */

import java.net.*;
import java.io.*;
import java.util.*;
import java.time.*;

public class PollClientHandler extends Thread {
    
    private Socket socket = null;
    private PollProtocol pp = null;
    // log file
    private FileHandler file = new FileHandler("log.txt");

    public PollClientHandler(Socket socket, PollProtocol pp) {
        super("PollClientHandler");
        this.socket = socket;
        this.pp = pp;
        file.createFile();
    }

    public void run() {
        // run thread for client
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                        socket.getInputStream()));
            String inputLine = in.readLine();
            
            // convert commas to whitespaces for writing to 'log.txt' file
            String command = inputLine.replace(",", " ");
            String outputLine;
            
            // add new line to file with log
            file.append(LocalDateTime.now().toString() + ":" +
                socket.getInetAddress() + ":" + command + "\n");
            outputLine = pp.processInput(inputLine);
            out.println(outputLine);
            out.close();
            in.close();
            socket.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
