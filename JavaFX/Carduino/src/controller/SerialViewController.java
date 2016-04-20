/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import carduino.Carduino;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Serial;
import util.ControlledScreen;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class SerialViewController implements Initializable, ControlledScreen
{
    Serial serial;
    String commant;
    public static final char UP = 'w'; 
    public static final char DOWN = 's';
    public static final char DIRECTION = 'd';
    ScreensController myController;

    @FXML
    private CheckBox stbyCheck;
    @FXML
    private ImageView steeringimgView;
    @FXML
    private ImageView engineimgView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        serial = new Serial();
        serial.initialize();
        setPicture(0, 0); //Es wird 0 übergeben da beim Start noch nichts überprüft wird.
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

    
    //speedSlider testen

    @FXML
    private void DebugSerial(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Testvorgang");
        int steeringWorking = 0, engineWorking = 0; //0... noch nicht überprüft, 1... funktioniert nicht, 2... funktioniert

        /*Lenkungstest*/
        
        /*Beschleunigugstest*/ 
        try {
            for(int i = 0; i < 1024; i += 10) {
                commant = UP + String.valueOf(i);
                serial.setOutput(commant);
            }
            for(int i = 0; i < 1024; i += 10) {
                commant = DOWN + String.valueOf(i);
                serial.setOutput(commant);
            }
            engineWorking = 2;
        } catch (IOException | NullPointerException ex) {
            //Beschleunigung gescheitert
            System.out.println("failed");
            engineWorking = 1;
        }
        
        setPicture(steeringWorking, engineWorking);
        a.setHeaderText("TEST ABGESCHLOSSEN");
        a.setContentText("Alles funkionsfähig!!");

        //a.showAndWait();
    }

    private void setPicture(int steeringWorking, int engineWorking) {
        if(steeringWorking == 0){
            Image img = new Image("file:src/res/lenkung_nt.png");
            steeringimgView.setImage(img);
        } 
        else if(steeringWorking == 1){
            Image img = new Image("file:src/res/enkung_nw.png");
            steeringimgView.setImage(img);
        }
        else if(steeringWorking == 2){
            Image img = new Image("file:src/res/lenkung_w.png");
            steeringimgView.setImage(img);
        }
        
        if(engineWorking == 0){
            Image img = new Image("file:src/res/motor_nt.png");
            engineimgView.setImage(img);
        } 
        else if(engineWorking == 1){
            Image img = new Image("file:src/res/motor_nw.png");
            engineimgView.setImage(img);
        }
        else if(engineWorking == 2){
            Image img = new Image("file:src/res/motor_w.png");
            engineimgView.setImage(img);
        }
    }
}
