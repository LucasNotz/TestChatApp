package cliente;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;


public class ChatClientGui2 extends JFrame {
    private JTextArea messageArea;
    private JTextField textField;
    private ChatClient2 client; //chatclient class to create chat client
    private JButton exitButton;
    private JPanel bottomPanel;
    
    //constructor
    public ChatClientGui2() {
        super("Chat Application");
        setSize(400,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Color backgroundColor = new Color(240,240,240); //light gray background
        Color buttonColor = new Color(75,75,75); //dark gray for buttons
        Color textColor = new Color(50,50,50); // aslmost black for text
        Font textFont = new Font("Arial", Font.PLAIN, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        
        
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setBackground(backgroundColor);
        messageArea.setForeground(textColor);
        messageArea.setFont(textFont);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);
        
        String name = JOptionPane.showInputDialog(this, "Enter your name:", "Name entry", JOptionPane.PLAIN_MESSAGE);
        this.setTitle("Chat Application - " + name);
        
        textField = new JTextField();
        //adds actionlistener without creating new class
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + name + ": " + textField.getText();
                client.sendMessage(message); //have to create senMessage on chatclient
                textField.setText("");
            }
            });
        //add(textField, BorderLayout.SOUTH);
        
        //initialize and start chat client
        try {
            this.client = new ChatClient2("127.0.0.1", 5000, this::onMessageReceived); //have to add onmessagereceived to chatclient /done
            client.startClient(); //have to add start client /done
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to server", "Connection error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
          

        }
        exitButton = new JButton("Exit");
        exitButton.setFont(buttonFont);
        exitButton.setBackground(buttonColor);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> {
            String departureMessage = name + " has left the chat.";
            client.sendMessage(departureMessage);
            
            try {
                Thread.sleep(1000);            
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            
            System.exit(0);            
        });
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.add(textField, BorderLayout.CENTER);
        bottomPanel.add(exitButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void onMessageReceived(String message) {
        SwingUtilities.invokeLater(() -> messageArea.append(message + "\n")); //adds message to messageArea 
        //Swing is not thread safe aka can crash or have strange behavior 
        //SwingUtilities.invokeLater( () -> ...) runs on EVENT DISPATCH TREAD, thread that manages all gui updates
        // () -> ... is lambda that is scheduled by invoke later to run on event dispatch tread
    }
    /*
    public static void main(String[] args) {
        SwingUtilities.invokeLater((() -> new ChatClientGui().setVisible(true)));
        //runs gui on edt
        //creates new clientgui and makes visible
    }*/
}
