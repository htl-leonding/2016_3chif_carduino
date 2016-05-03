/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import carduino.Carduino;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class MenuViewController implements Initializable
{
    
    @FXML
    private AnchorPane MenuPane;
    @FXML
    private Button buttonSerial;
    @FXML
    private Button buttonClient;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void StartSerial(ActionEvent event) throws IOException 
    {
        Parent p = FXMLLoader.load(getClass().getResource("/view/SerialView.fxml"));
        Scene s = new Scene(p);
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.setScene(s);
        stg.setTitle("Serial");
        stg.show();
    }

    @FXML
    private void StartClient(ActionEvent event) throws IOException 
    {
        Parent p = FXMLLoader.load(getClass().getResource("/view/ClientView.fxml"));
        Scene s = new Scene(p);
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.setScene(s);
        stg.setTitle("Client");
        stg.show();
    }   
}