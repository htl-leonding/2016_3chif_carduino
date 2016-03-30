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
import view.ControlledScreen;
import view.ScreensController;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class SerialViewController implements Initializable, ControlledScreen
{
    
    ScreensController myController;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
    
    @FXML
    private void BackToRoot(ActionEvent event) 
    {
        myController.setScreen(Carduino.screenMenuID);
    }
    
}
