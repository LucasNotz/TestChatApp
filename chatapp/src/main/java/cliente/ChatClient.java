package cliente;

import java.io.BufferedReader;//read input buffer
import java.io.IOException;// i/o handling throwable
import java.io.InputStreamReader;//read input
import java.io.PrintWriter;//prints text to outputstream
import java.net.Socket; //socket connections
import java.net.UnknownHostException;
import java.util.function.Consumer;// accepts a single input argument and returns nothing


public class ChatClient {
    private Socket socket = null;
    private BufferedReader inputConsole = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Consumer<String> onMessageReceived;
    
    public ChatClient(String serverAddress, int serverPort, Consumer<String> onMessageReceived) throws IOException {
        this.socket = new Socket(serverAddress, serverPort);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.onMessageReceived = onMessageReceived; //allows it to accept message
    }
    
    public void sendMessage(String msg) {
        out.println(msg); //sends data through socket
    }
    
    public void startClient() {
        new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    onMessageReceived.accept(line); //uses consumer interface
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start(); //starts thread
    }
    
    //constructor 
    public ChatClient (String address, int port) {
        try {
            socket = new Socket(address, port); //assining to socket and connecting
            //if of course serversocket.accept() creates connection
            System.out.println("Connected to the chat server");
            
            inputConsole = new BufferedReader(new InputStreamReader(System.in)); //user input
            out = new PrintWriter(socket.getOutputStream(), true); //sends socket data and auto flushes
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));//gets socket data
            
            String line = "";
            while (!line.equals("exit")) {
                line = inputConsole.readLine();//reads console input
                out.println(line);//out is my variable and println is printwriter method
                System.out.println(in.readLine());//prints socket received to console                               
            }
            
            socket.close();
            inputConsole.close();
            out.close();      
        } catch (UnknownHostException u) {
            System.out.println("Host unknown: " + u.getMessage());
        } catch (IOException i) {
            System.out.println("Unexpected exception: " + i.getMessage());
        }
    }
    
    public static void main(String[] args) throws IOException{
        ChatClient client = new ChatClient("127.0.0.1", 5000);
    }
}
