/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carduino;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
    DataOutputStream  output;

    public Client() {
       
    }    
    
    public void sendData(String s){
         try {
            client = new Socket("192.168.1.128", 1337);
            System.out.println("connected");
            input = new DataInputStream(client.getInputStream());
            output = new DataOutputStream (client.getOutputStream());
            output.writeBytes(s + '\n');
            output.flush();
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}
