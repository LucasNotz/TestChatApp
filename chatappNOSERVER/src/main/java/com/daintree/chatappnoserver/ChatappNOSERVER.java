package com.daintree.chatappnoserver;

import cliente.ChatClientGui2;
import javax.swing.SwingUtilities;

public class ChatappNOSERVER {

    public static void main(String[] args) {
        SwingUtilities.invokeLater((() -> new ChatClientGui2().setVisible(true)));
        //runs gui on edt
        //creates new clientgui and makes visible
    }
}
