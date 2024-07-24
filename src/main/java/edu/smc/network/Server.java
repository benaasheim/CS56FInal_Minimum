package edu.smc.network;

import edu.smc.data.Administrator;
import edu.smc.data.Database;
import edu.smc.data.Student;

import java.io.*;
import java.net.*;

public class Server {
    private static final String CMD_LOGIN = "login";
    private static final String CMD_ADD = "add";
    private static final String CMD_REMOVE = "remove";
    private static final String CMD_LIST = "list";
    private static final String SUCCESS = "true";
    private static final String FAIL = "false";
    private static final String ADMIN = "admin";
    private static final String STUDENT = "student";
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private Database data = new Database();
    private Administrator admin = new Administrator("admin", "admin");


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
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Initialize the BufferedReader for the input stream of the client socket
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // A variable to hold the input line
                String inputLine;

                // Read each incoming line from the client
                // And echo it back to the client until 'bye' is received
                while ((inputLine = in.readLine()) != null) {
                    String[] parse = inputLine.split("#");
                    if (parse[0].equals(CMD_LOGIN)) {
                        if(parse[3].equals(ADMIN)){
                            if(admin.verify(parse[1], parse[2])){
                                out.println(SUCCESS);
                            }else{
                                out.println(FAIL);
                            }
                        } else {
                            if (data.verify(parse[1], parse[2])) {
                                out.println(SUCCESS);
                            } else {
                                out.println(FAIL);
                            }
                        }
                    } else if (parse[0].equals(CMD_ADD)) {
                        boolean studentExist = data.addStudent(parse[1], parse[2], parse[3],parse[4], parse[5]);
                        if(studentExist){
                            out.println(SUCCESS);
                            data.saveData();
                        }else{
                            out.println(FAIL);
                        }
                    } else if (parse[0].equals(CMD_REMOVE)) {
                        boolean studentRemoved = data.removeStudent(Integer.valueOf(parse[1]));
                        if(studentRemoved){
                            out.println(SUCCESS);
                            data.saveData();
                        }else{
                            out.println(FAIL);
                        }

                    } else if (parse[0].equals(CMD_LIST)) {
                        out.println(data.listStudents());

                    } else if (parse[0].equals("info")){
                        int studentID = Integer.valueOf(parse[1]);
                        Student student = data.getStudent(studentID);
                        if (student != null) {
                            StringBuilder info = new StringBuilder();
                            info.append(student.getFirstName()+ "#");
                            info.append(student.getLastName()+ "#");
                            info.append(student.getStudentID()+ "#");
                            info.append(student.getPhoneNumber()+ "#");
                            info.append(student.getAddress()+ "#");
                            info.append(student.getMajor());
                            out.println(info.toString());
                        } else {
                            out.println(FAIL);
                        }
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
        server.start(5000);

    }
}

