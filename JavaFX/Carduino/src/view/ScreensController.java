package view;

import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 *
 * @author Tobias
 */
public class ScreensController  extends StackPane 
{  
    //Holds the screens to be displayed
    private HashMap<String, Node> screens = new HashMap<>();
    
    public ScreensController() 
    {
        super();
    }
   
    //Adds the screen to the collection
    public void addScreen(String name, Node screen) 
    {
        screens.put(name, screen);
    }

    //Returns the Node with the appropriate name
    public Node getScreen(String name) 
    {
        return screens.get(name);
    }

    //Loads the fxml file, adds the screen to the screens collection and injects the screenPane to the controller.
    public boolean loadScreen(String name, String resource) 
    {
        try 
        {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            ControlledScreen myScreenControler = ((ControlledScreen) myLoader.getController());
            myScreenControler.setScreenParent(this);
            addScreen(name, loadScreen);
            return true;
        } 
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean setScreen(final String name) 
    {       
        if (screens.get(name) != null)  //screen loaded
        {  
            final DoubleProperty opacity = opacityProperty();
            if (!getChildren().isEmpty())                           //if there is more than one screen
            {    
                Timeline fade = new Timeline(new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() 
                {
                    @Override
                    public void handle(ActionEvent t) 
                    {
                        getChildren().remove(0);                    //remove the displayed screen
                        getChildren().add(0, screens.get(name));     //add the screen
                        Timeline fadeIn = new Timeline(new KeyFrame(new Duration(800), new KeyValue(opacity, 1.0)));
                        fadeIn.play();
                    }
                }, new KeyValue(opacity, 0.0)));
                fade.play();
            } 
            else 
            {
                setOpacity(0.0);
                getChildren().add(screens.get(name));       //no other Screen has been displayed -> show Screen
                Timeline fadeIn = new Timeline(new KeyFrame(new Duration(2500), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } 
        else 
        {
            System.out.println("screen hasn't been loaded!!! \n");
            return false;
        }
    }

    //This method will remove the screen with the same name as one in the collection from the collection
    public boolean unloadScreen(String name) 
    {
        if (screens.remove(name) == null) 
        {
            System.out.println("Screen didn't exist");
            return false;
        } 
        else 
        {
            return true;
        }
    }
}
