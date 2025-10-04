package com.daintree.javachatapp;

import cliente.ChatClientGui;
import javax.swing.SwingUtilities;

public class Chatapp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater((() -> new ChatClientGui().setVisible(true)));
        //runs gui on edt
        //creates new clientgui and makes visible
    }
}
