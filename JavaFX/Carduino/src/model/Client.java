/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.UnknownHostException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


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
            client = new Socket();
            client.connect(new InetSocketAddress("192.168.1.128", 1337),1000);
            System.out.println("connected");
            input = new DataInputStream(client.getInputStream());
            output = new DataOutputStream (client.getOutputStream());
            output.writeBytes(s + '\n');
            output.flush();
            client.close();
        } 
         catch(SocketTimeoutException ex){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Fehler");
            alert.setContentText("Es wurde kein Server gefunden!");

            alert.showAndWait();
         }
         catch(UnknownHostException ex){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Fehler");
            alert.setContentText("Ungültige IP");

            alert.showAndWait();
         }
         catch(SocketException ex){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Fehler");
            alert.setContentText("Clientfehler!");

            alert.showAndWait();
         }
         catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}
