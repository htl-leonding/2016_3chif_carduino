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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Serial;
import util.ControlledScreen;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class SerialViewController implements Initializable, ControlledScreen, Runnable
{
    Serial serial;
    String commant;
    public static final char UP = 'w'; 
    public static final char DOWN = 's';
    public static final char DIRECTION = 'd';
    ScreensController myController;
    int steeringWorking = 0, engineWorking = 0; //0... noch nicht überprüft, 1... funktioniert nicht, 2... funktioniert
        

    @FXML
    private CheckBox stbyCheck;
    @FXML
    private ImageView steeringimgView;
    @FXML
    private ImageView engineimgView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
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
        Thread t = new Thread(this);
        t.start();
    }

    private void setPicture(int steeringWorking, int engineWorking) {
        if(steeringWorking == 0){
            Image img = new Image("file:src/res/lenkung_nt.png");
            steeringimgView.setImage(img);
        } 
        else if(steeringWorking == 1){
            Image img = new Image("file:src/res/lenkung_nw.png");
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

    @Override
    public void run() {
        serial = new Serial();
        serial.initialize();
        setPicture(0, 0); //Es wird 0 übergeben da beim Start noch nichts überprüft wird.
  
    /*Beschleunigugstest*/ 
        try {
            for(int i = 0; i < 1024; i += 64) {
                commant = UP + String.valueOf(i);
                serial.setOutput(commant);
                Thread.sleep(500);
            }
            for(int i = 1024; i >= 0; i -= 64) {
                commant = DOWN + String.valueOf(i);
                serial.setOutput(commant);
                Thread.sleep(500);
            }
            engineWorking = 2;
        } catch (IOException | NullPointerException ex) {
            //Beschleunigung gescheitert
            System.out.println("failed");
            engineWorking = 1;
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setPicture(0, engineWorking); 
        /*Lenkungstest*/
        try{
            for(int i = 514;i <= 1024;i += 64) //Von der Mitte bis ganz rechts.
            {
                commant = DIRECTION + String.valueOf(i);
                Thread.sleep(500);
                serial.setOutput(commant);
            }
            for(int i = 1024;i >= 0;i -= 64) //Von ganz rechts bis ganz links.
            {
                commant = DIRECTION + String.valueOf(i);
                Thread.sleep(500);
                serial.setOutput(commant);
            }
            for(int i = 0;i < 514;i += 64) //Von ganz links wieder zurück in die Mitte.
            {
                commant = DIRECTION + String.valueOf(i);
                Thread.sleep(500);
                serial.setOutput(commant);
            }
            steeringWorking = 2;
        }catch(IOException | NullPointerException ex){
            System.out.println("failed");
            steeringWorking = 1;
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialViewController.class.getName()).log(Level.SEVERE, null, ex);
        }   
        setPicture(steeringWorking, engineWorking); 
        serial.close();
    }
}
