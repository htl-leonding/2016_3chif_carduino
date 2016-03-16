package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import model.Client;
import model.Serial;

public class FXMLController implements Initializable, Observer{
    
    Serial serial;
    Client client;
    String commant;
    public static final char UP = 'w'; 
    public static final char DOWN = 's';
    public static final char RIGHT = 'd';
    public static final char LEFT = 'a';
    @FXML
    private Button leftBtn;
    @FXML
    private Button rightBtn;
    @FXML
    private Label recivelb;
    @FXML
    private Label speedlb;
    @FXML
    private CheckBox stbyCheck;
    @FXML
    private Slider speedSlider;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        serial = new Serial();
        //client = new Client();
        serial.initialize();
        serial.addObserver(this);
        
        speedSlider.valueProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                speedlb.setText(String.valueOf(speedSlider.getValue()));
                if ((int)speedSlider.getValue() >= 0) {
                    commant = UP + String.valueOf((int)speedSlider.getValue());
                }
                else if ((int)speedSlider.getValue() < 0) {
                    commant = DOWN + String.valueOf((int)speedSlider.getValue());
                }
                try {
                    //client.sendData(i);
                    serial.setOutput(commant);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }    

    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                recivelb.setText(serial.getInputLine());
            }
        });
    }


}
