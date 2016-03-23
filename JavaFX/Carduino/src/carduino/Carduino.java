package carduino;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Carduino extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("CarduinoView.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("MenuView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        //Für Seriel
         /*stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

           @Override
            public void handle(WindowEvent event) {
                Serial.serialPort.close();
            }
        });*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}
