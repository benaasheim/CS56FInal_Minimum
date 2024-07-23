package edu.smc.network;

import edu.smc.base.User;
import edu.smc.data.Administrator;
import edu.smc.data.Database;
import edu.smc.data.Student;

import java.io.*;
import java.net.*;
import java.util.Set;

public class Server {
    private static final String CMD_LOGIN = "login";
    private static final String CMD_ADD = "add";
    private static final String CMD_REMOVE = "remove";
    private static final String CMD_LIST = "list";
    private static final String SUCCESS = "true";
    private static final String Fail = "false";
    public static final String REGEX = "#";
    public static final String USERNAME = "admin";
    public static final String PASSWORD = "admin";
    public static final boolean AUTO_FLUSH = true;
    public static final int PORT = 5000;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private Database data = new Database();
    private Administrator admin = new Administrator(USERNAME, PASSWORD);


    public void start(int port) {
        // Try enclosing the code to handle exceptions
        try {
            data.loadData();
            // Create a new ServerSocket that listens to the specified port number
            serverSocket = new ServerSocket(port);

            // Enter into an infinite loop
            while (true) {
                // Accept a client's connection request and
                // Create a new socket for the client
                clientSocket = serverSocket.accept();

                // Initialize the PrintWriter for the output stream of the client socket
                out = new PrintWriter(clientSocket.getOutputStream(), AUTO_FLUSH);

                // Initialize the BufferedReader for the input stream of the client socket
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // A variable to hold the input line
                String inputLine;

                // Read each incoming line from the client
                // And echo it back to the client until 'bye' is received
                while ((inputLine = in.readLine()) != null) {
                    String[] parse = inputLine.split(REGEX);
                    if (parse[0].equals(CMD_LOGIN)) {
                        if (parse[1].equals(admin.getUsername()) && parse[2].equals(admin.getPassword())) {
                            out.println(SUCCESS);
                        } else {
                            out.println(Fail);
                        }
                    } else if (parse[0].equals(CMD_ADD)) {
                        boolean studentExist = data.addStudent(parse[1], parse[2], parse[3],parse[4], parse[5]);
                        if(studentExist){
                            out.println(SUCCESS);
                        }else{
                            out.println(Fail);
                        }

                    } else if (parse[0].equals(CMD_REMOVE)) {
                        boolean studentRemoved = data.removeStudent(Integer.valueOf(parse[1]));
                        if(studentRemoved){
                            out.println(SUCCESS);
                        }else{
                            out.println(Fail);
                        }
                    } else if (parse[0].equals(CMD_LIST)) {
                        out.println(data.listStudents());

                    }
                }


            }
        } catch (IOException e) {
            // Print stack trace in case of an exception
            e.printStackTrace();
        } finally {
            // Regardless of whether an exception occurs or not, stop the server
            stop();
        }
    }

    public void stop() {
        // Enclose the code in a try block to handle exceptions
        try {
            // Close all the resources
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            // Print stack trace in case of an exception
            e.printStackTrace();
        }
    }

    // The entry point of the server application
    public static void main(String[] args) {
        // Create a new Server instance
        Server server = new Server();
        // Start the server at port 5000
        server.start(PORT);

    }
}

