package controller;

import carduino.Carduino;
import java.io.IOException;
import model.Client;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ClientViewController implements Initializable,Runnable
{
    
    static Client client = new Client();
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
    @FXML
    private StackPane pane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //serial = new Serial();
        //serial.initialize();
        //serial.addObserver(this);
        Thread threadW = new Thread(this);
        Thread threadS = new Thread(this);
        Thread threadD = new Thread(this);
        Thread threadA = new Thread(this);
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
        /*pane.setFocusTraversable(true);
        pane.requestFocus();
        pane.setOnKeyPressed(new  EventHandler<KeyEvent>() {
           @Override
           public void handle(KeyEvent event) {
               //set Value
               if (event.getCode() == KeyCode.W) 
               {
                    if(threadS.getState() == Thread.State.RUNNABLE)
                    {
                        threadS.stop();
                    }
                    if(threadW.getState() == Thread.State.NEW)
                    {
                        threadW.start();
                    }
               }
               else if (event.getCode() == KeyCode.S) 
               {
                    if(threadW.getState() == Thread.State.RUNNABLE)
                    {
                        threadW.stop();
                    }
                    if(threadS.getState() == Thread.State.NEW)
                    {
                        threadS.start();
                    }
               }
               else if (event.getCode() == KeyCode.A) 
               {
                    if(threadD.getState() == Thread.State.RUNNABLE)
                    {
                        threadD.stop();
                    }
                    if(threadA.getState() == Thread.State.NEW)
                    {
                        threadA.start();
                    }
               }
               else if (event.getCode() == KeyCode.D) 
               {
                    if(threadA.getState() == Thread.State.RUNNABLE)
                    {
                        threadA.stop();
                    }
                    if(threadD.getState() == Thread.State.NEW)
                    {
                        threadD.start();
                    }
               }
           }
       });
        pane.setFocusTraversable(true);
        pane.requestFocus();
        pane.setOnKeyReleased(new  EventHandler<KeyEvent>() {
           @Override
           public void handle(KeyEvent event) { 
               if (event.getCode() == KeyCode.W) 
               {
                   threadW.stop();
                   client.sendData("w0");
               }
               else if (event.getCode() == KeyCode.S) 
               {
                   threadS.stop();
                   client.sendData("w0");
               }
               else if (event.getCode() == KeyCode.D) 
               {
                   threadD.stop();
                   client.sendData("w0");
               }
               if (event.getCode() == KeyCode.A) 
               {
                   threadA.stop();
                   client.sendData("w0");
               }
           }
       });*/
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

    @Override
    public void run() 
    {
       client.sendData("w1024");   //change with Value (static variable)
    }
}