package carduino;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.ScreensController;
import java.util.Properties;

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
        String os = "os.name";
        String result;
        Properties prop = System.getProperties( );
        result = prop.getProperty(os);
        
        System.out.println(result);
        System.out.println("");
        if(result == "Mac OS X"){
            System.setProperty("java.library.path", "/Library/Java/Extensions");
        }
        else{
            System.setProperty("java.library.path", ".\\rxtx-2.1-7-bins-r2\\Windows\\i368-mingw32");            
        }
        launch(args);
    }
}