package carduino;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class CarduinoController implements Initializable, Observer{
    
    Serial serial;
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
                /*try {
                    serial.setOutput(commant);
                } catch (IOException ex) {
                    Logger.getLogger(CarduinoController.class.getName()).log(Level.SEVERE, null, ex);
                }*/
            }
        });
        directionSlider.valueProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                commant = DIRECTION + String.valueOf((int)directionSlider.getValue());
                client.sendData(commant);
                /*try {
                    serial.setOutput(commant);
                } catch (IOException ex) {
                    Logger.getLogger(CarduinoController.class.getName()).log(Level.SEVERE, null, ex);
                }*/
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
