/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import carduino.Carduino;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Serial;

/**
 * FXML Controller class
 *
 * @author Alex
 */
public class SerialViewController implements Initializable, Runnable {

    Serial serial;
    String commant;
    public static final char UP = 'w';
    public static final char DOWN = 's';
    public static final char DIRECTION = 'd';
    int steeringWorking = 0, engineWorking = 0; //0... noch nicht überprüft, 1... funktioniert nicht, 2... funktioniert

    @FXML
    private CheckBox stbyCheck;
    @FXML
    private ImageView steeringimgView;
    @FXML
    private ImageView engineimgView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setPicture(0, 0); //Es wird 0 übergeben da beim Start noch nichts überprüft wird.
    }

    //speedSlider testen
    @FXML
    private void DebugSerial(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Testvorgang");
        Thread test = new Thread(this);
        Thread surveillance = new Thread(new ConnectorControl());
        test.start();
        surveillance.start();
    }

    private void setPicture(int steeringWorking, int engineWorking) {
        if (steeringWorking == 0) {
            Image img = new Image("file:src/res/lenkung_nt.png");
            steeringimgView.setImage(img);
        } else if (steeringWorking == 1) {
            Image img = new Image("file:src/res/lenkung_nw.png");
            steeringimgView.setImage(img);
        } else if (steeringWorking == 2) {
            Image img = new Image("file:src/res/lenkung_w.png");
            steeringimgView.setImage(img);
        }

        if (engineWorking == 0) {
            Image img = new Image("file:src/res/motor_nt.png");
            engineimgView.setImage(img);
        } else if (engineWorking == 1) {
            Image img = new Image("file:src/res/motor_nw.png");
            engineimgView.setImage(img);
        } else if (engineWorking == 2) {
            Image img = new Image("file:src/res/motor_w.png");
            engineimgView.setImage(img);
        }
    }

    @Override
    public void run() {
        serial = new Serial();
        serial.initialize();

        /*Beschleunigugstest*/
        try {
            for (int i = 0; i < 1024; i += 64) {
                commant = UP + String.valueOf(i);
                serial.setOutput(commant);
                Thread.sleep(500);
            }
            for (int i = 1024; i >= 0; i -= 64) {
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
        try {
            for (int i = 514; i <= 1024; i += 64) //Von der Mitte bis ganz rechts.
            {
                commant = DIRECTION + String.valueOf(i);
                Thread.sleep(500);
                serial.setOutput(commant);
            }
            for (int i = 1024; i >= 0; i -= 64) //Von ganz rechts bis ganz links.
            {
                commant = DIRECTION + String.valueOf(i);
                Thread.sleep(500);
                serial.setOutput(commant);
            }
            for (int i = 0; i < 514; i += 64) //Von ganz links wieder zurück in die Mitte.
            {
                commant = DIRECTION + String.valueOf(i);
                Thread.sleep(500);
                serial.setOutput(commant);
            }
            steeringWorking = 2;
        } catch (IOException | NullPointerException ex) {
            System.out.println("failed");
            steeringWorking = 1;
        } catch (InterruptedException ex) {
            Logger.getLogger(SerialViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setPicture(steeringWorking, engineWorking);
        serial.closePort();
    }

    @FXML
    private void ToMenu(ActionEvent event) throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/view/MenuView.fxml"));
        Scene s = new Scene(p);
        Stage stg = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stg.setScene(s);
        stg.setTitle("Menu");
        stg.show();
    }
}

class ConnectorControl implements Runnable {

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {

            if (readln().contains("Error 0x5")) {
                System.exit(0);
            }
        }
    }

    public static String readln() {
        try {
            // initiate input-reader
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(System.in));

            // read input
            String s = in.readLine();

            // return input
            return s;
        } catch (IOException e) {
            System.out.println("Disconnected");
        }

        return "No Input.";
    }
}
