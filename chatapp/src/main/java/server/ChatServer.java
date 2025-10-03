package server;

import java.io.BufferedReader;//reader buffer
import java.io.IOException;//handels inputo output
import java.io.InputStreamReader;//input
import java.io.PrintWriter;//text output
import java.net.ServerSocket;//handles server connection
import java.net.Socket;//handles connections
import java.util.ArrayList;
import java.util.List;

//only one public class per .java file !!!
public class ChatServer { 
    //ArrayList to keep track of connected clients
    private static List<ClientHandler> clients = new ArrayList<>();
    
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000); //serverSocket listens to port 5000
        System.out.println("Server started. Wating for clients...");
        
        while (true) {
            //clintSocket creates connection with server
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cleint connected: " + clientSocket);
            
            //Spawn new thread for each lient
            ClientHandler clientThread = new ClientHandler(clientSocket, clients);
            clients.add(clientThread);
            new Thread(clientThread).start();
        }
        
        
    }
}

//Create ClientHandler class 
//Runnable --> used when class instance will be excecuted by thread
////Must have method run() with no arguments
class ClientHandler implements Runnable {
    //class variables
    private Socket clientSocket;
    private List<ClientHandler> clients;
    private PrintWriter out;
    private BufferedReader in;
    
    //full constructor for clienthandler aka method
    public ClientHandler(Socket socket, List<ClientHandler> clients) throws IOException {
        this.clientSocket = socket;
        this.clients = clients;
        this.out = new PrintWriter(clientSocket.getOutputStream(), true); //sends bytes to socket and flushes socket
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));//reads bytes from socket
        //bufferedreader wraps and facilitates access to what will be read
        //inputstream//getinput stream converts into text
        //buffer is a contiguous block of memory
    }
    
    public void run() {
       try {
           String inputLine;
           // readLine() returns null when there is nothing more to read
           while ((inputLine = in.readLine()) != null) {
               for (ClientHandler aClient : clients) {
                   //for (Type var : array) aka for each loop: use variable
                   aClient.out.println(inputLine);
               }
                   
           }
       } catch (IOException e) {
           System.out.println("An error occurred: " + e.getMessage());
       } finally {
           try {
               in.close();
               out.close();
               clientSocket.close();         
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       
    }
    
}
