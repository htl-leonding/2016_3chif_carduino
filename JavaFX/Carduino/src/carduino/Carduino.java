package carduino;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Carduino extends Application {
  
    public static String screenMenuID = "menu";
    public static String screenMenuFile = "MenuView.fxml";
    public static String screenClientID = "client";
    public static String screenClientFile = "ClientView.fxml";
    public static String screenSerialID = "serial";
    public static String screenSerialFile = "SerialView.fxml";
    
    
    @Override
    public void start(Stage stage) throws IOException 
    {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MenuView.fxml"));      
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Menu");
        stage.show();
    }  
            //Für Seriel
         /*stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

           @Override
            public void handle(WindowEvent event) {
                Serial.serialPort.close();
            }
        });*/
    public static void main(String[] args) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException 
    {
        String os = "os.name";
        String result;
        Properties prop = System.getProperties( );
        result = prop.getProperty(os);
        
        if(result.contains("Mac")){
            System.setProperty("java.library.path", "/Library/Java/Extensions");
            Field fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
            fieldSysPath.setAccessible( true );
            fieldSysPath.set( null, null );
        }   
        launch(args);
    }
}