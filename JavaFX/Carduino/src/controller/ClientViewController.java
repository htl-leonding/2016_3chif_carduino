package controller;

import carduino.Carduino;
import java.io.IOException;
import model.Client;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class ClientViewController implements Initializable
{
    
    Client client;
    String commant;
    public static final char UP = 'w'; 
    public static final char DOWN = 's';
    public static final char DIRECTION = 'd';
   

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
    public void initialize(URL url, ResourceBundle rb) {
        //serial = new Serial();
        //serial.initialize();
        //serial.addObserver(this);
        client = new Client();
        
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
                client.sendData(commant);
            }
        });
        directionSlider.valueProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                commant = DIRECTION + String.valueOf((int)directionSlider.getValue());
                client.sendData(commant);
            }
        });
        
    }

    @FXML
    private void ToMenu(ActionEvent event) throws IOException 
    {
        Parent p = FXMLLoader.load(getClass().getResource("/view/MenuView.fxml"));
        Scene s = new Scene(p);
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.setScene(s);
        stg.setTitle("Menu");
        stg.show();
    }
}
