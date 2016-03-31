/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import carduino.Carduino;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import util.ControlledScreen;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class MenuViewController implements Initializable , ControlledScreen 
{
    
    ScreensController myController;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void StartSerial(ActionEvent event) 
    {
        myController.setScreen(Carduino.screenSerialID);
    }

    @FXML
    private void StartClient(ActionEvent event) 
    {
        myController.setScreen(Carduino.screenClientID);
    }

    @Override
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }
    
}
