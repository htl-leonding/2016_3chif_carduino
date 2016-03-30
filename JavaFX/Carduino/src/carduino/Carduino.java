package carduino;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.ScreensController;

public class Carduino extends Application {
  
    public static String screenMenuID = "menu";
    public static String screenMenuFile = "MenuView.fxml";
    public static String screenClientID = "client";
    public static String screenClientFile = "ClientView.fxml";
    public static String screenSerialID = "serial";
    public static String screenSerialFile = "SerialView.fxml";
    
    
    @Override
    public void start(Stage primaryStage) 
    {

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Carduino.screenMenuID, Carduino.screenMenuFile);
        mainContainer.loadScreen(Carduino.screenClientID, Carduino.screenClientFile);
        mainContainer.loadScreen(Carduino.screenSerialID, Carduino.screenSerialFile);
        
        mainContainer.setScreen(Carduino.screenMenuID);
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }  
            //Für Seriel
         /*stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

           @Override
            public void handle(WindowEvent event) {
                Serial.serialPort.close();
            }
        });*/
    public static void main(String[] args) 
    {
        launch(args);
    }
}