package edu.smc.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class Client implements AutoCloseable{

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Client(String serverAddress, int port) throws IOException {
        this.socket = new Socket(serverAddress, port);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }
    public void sendToServer(String message){
        // Sending the message to the server
        out.println(message);
    }

    public String readFromServer() throws IOException {
        // Reading the response from the server
        return in.readLine();
    }

    @Override
    public void close() throws IOException {
        // Closing the resources
        if (out != null) {
            out.close();
        }
        if (in != null) {
            in.close();
        }
        if (socket != null) {
            socket.close();
        }
    }
}

