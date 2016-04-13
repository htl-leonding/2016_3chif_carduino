/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import carduino.Carduino;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import model.Serial;
import util.ControlledScreen;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class SerialViewController implements Initializable, ControlledScreen, Observer
{
    Serial serial;
    String commant;
    public static final char UP = 'w'; 
    public static final char DOWN = 's';
    public static final char DIRECTION = 'd';
    ScreensController myController;
   

    @FXML
    private Label recivelb;
    @FXML
    private Label speedlb;
    @FXML
    private CheckBox stbyCheck;
    @FXML
    private Slider speedSlider;
    @FXML
    private Slider directionSlider;
    @FXML
    private Label speedlb1;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        serial = new Serial();
        serial.initialize();
        serial.addObserver(this);
        
        speedSlider.valueProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                speedlb.setText(String.valueOf(speedSlider.getValue()));
                System.out.println(speedSlider.getValue());
                if ((int)speedSlider.getValue() > 0) {
                    commant = UP + String.valueOf((int)speedSlider.getValue());
                }
                else if ((int)speedSlider.getValue() < 0) {
                    commant = DOWN + String.valueOf(Math.abs((int)speedSlider.getValue()));
                }
                serial.setOutput(commant);

            }
        });
        directionSlider.valueProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                commant = DIRECTION + String.valueOf((int)directionSlider.getValue());
                serial.setOutput(commant);
            }
        });
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

    @Override
    public void update(Observable o, Object arg) {
        recivelb.setText(serial.getInputLine());
    }
    
    //speedSlider testen

    @FXML
    private void DebugSerial(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Testvorgang");
        
        for(int i = 0; i < (int) speedSlider.getMax();i =i + 10){
            commant = UP + String.valueOf((int)speedSlider.getValue());
            serial.setOutput(commant);
        }
        for(int i = (int) speedSlider.getMax();i > 0;i = i - 10){
            commant = DOWN + String.valueOf(Math.abs((int)speedSlider.getValue()));
            serial.setOutput(commant);
        }
        a.setHeaderText("TEST ABGESCHLOSSEN");
        a.setContentText("Alles funkionsfähig!!");

        a.showAndWait();
    }
}
