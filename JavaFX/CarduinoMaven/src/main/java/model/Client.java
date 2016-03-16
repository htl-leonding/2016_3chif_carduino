/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex
 */
public class Client {
    Socket client;
    DataInputStream input;
    PrintWriter output;

    public Client() {
        try {
            this.client = new Socket( "Hello World", 1337 );
            input = new DataInputStream(client.getInputStream());
            output = new PrintWriter(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void sendData(int i) {
        output.println(i);
    }
    
}
