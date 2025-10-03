package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2000); //port number
        System.out.println("Server started. Wating for clients...");
        
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected.");
        
        
    }
}
