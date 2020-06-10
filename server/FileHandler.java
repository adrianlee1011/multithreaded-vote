/**
 * Name: Adrian Chen Young, Lee
 * Student ID: 201355786
 * 
 * This file provides file handling functions
 */

import java.io.*;
import java.util.*;

public class FileHandler {
    
    private File file = null;

    // initialise file
    public FileHandler(String filename) {
        file = new File(filename);
    }

    public void createFile() {
        // create new file
        try {
            file.createNewFile();
        }
        catch (IOException e) {
            System.err.println("File already exists");
            System.exit(1);
        }
    }

    // append new line to exisiting file
    public void append(String line) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(line);
            writer.close();
        }
        catch (IOException e) {
            System.err.println("Can't write to 'log.txt' file");
            System.exit(1);
        }
    }
}
