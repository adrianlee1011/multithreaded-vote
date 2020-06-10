/**
 * Name: Adrian Chen Young, Lee
 * Student ID: 201355786
 * 
 * This file provides the protocol for handling the client commands using hashtable
 * Note that hashtable does not maintain the order of the input options, but that order
 * does not matter in this case
 */

import java.net.*;
import java.util.Hashtable;
import java.io.*;

public class PollProtocol {

    private Hashtable<String, Integer> hashTable = new Hashtable<String, Integer>();
    private FileHandler file = new FileHandler("log.txt");

    public PollProtocol(String[] args) {
        initPoll(args);
    }

    // initialise hashtable poll with key, value=0
    private void initPoll(String[] pollArray) {
        for (String element: pollArray) {
            hashTable.put(element.toLowerCase(), 0);
		}
		if (hashTable.size() < 2) {
            // if hashtable size is less than 2, not enough number of choices
			System.out.println("Insufficient number of choices");
			System.exit(1);
		}
    }

    // process input string
    public String processInput(String inputString) {
        String outputString = null;
        String key = null;

        // split input string into array
        String[] clientCommand = inputString.split(",");

        // invalid arguments if array length is more than 2
        if (clientCommand.length > 2) {
            outputString = "Invalid number of arguments\n";
            return outputString;
        }

        if (clientCommand[0].equalsIgnoreCase("vote")) {
            try {
                key = clientCommand[1].toLowerCase();
            }

            // if cannot access index 1, it means client did not specify a choice
            catch (ArrayIndexOutOfBoundsException e) {
                outputString = "Vote <option> required\n";
                return outputString;
            }
            outputString = castVote(key);
        }

        else if (clientCommand[0].equalsIgnoreCase("show") && clientCommand.length == 1) {
            outputString = "";

            // iterate over hashtable and print key-value pairs
            for (String element : hashTable.keySet()) {
                outputString += "'" + element + "' has " + hashTable.get(element) + " vote(s)\n";
            }
        }

        // if command is not 'vote' or 'show', invalid command
        else {
            outputString = "Invalid command, only 'show' and 'vote <option>' recognized\n";
        }
        return outputString;
    }

    // function to vote
    private String castVote(String key) {
        String outputString = null;

        // if hashtable contains key, increment value of key and print updated number of votes
        if (hashTable.containsKey(key)) {
            int value = hashTable.get(key);
            hashTable.replace(key, value + 1);
            outputString = "Vote casted\n";
            outputString += "'" + key + "' now has " + hashTable.get(key) + " vote(s)\n";
        }
        else {
            outputString = "Invalid vote option\n";
        }
        return outputString;
    }
}