/**
 * Sample Skeleton for "MainWindow.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package simplepaint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import simplepaint.util.Dialog;


public class MainWindowController
    implements Initializable {

    @FXML //  fx:id="BrightnessPicker"
    private Slider BrightnessPicker;

    @FXML //  fx:id="ColorPicker"
    private ColorPicker ColorPicker;

    @FXML //  fx:id="GammaPicker"
    private Slider GammaPicker;

    @FXML // fx:id="Painter"
    private Pane Painter;
    
    public SimpleCanvas cv;
    
    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        // set a default canvas open
        this.cv = new SimpleCanvas(400.0, 400.0);
        this.Painter.getChildren().add(cv);
        this.GammaPicker.valueProperty().addListener(new GammaHandler(this));
        this.BrightnessPicker.valueProperty().addListener(new BrightnessHandler(this));
    }
    
    public void NewCanvas(double w, double h) {
            //remove and replace the old canvas with a new one
            Painter.getChildren().remove(cv);
            cv = new SimpleCanvas(w, h);
            Painter.getChildren().add(cv);
    }
    
    //wil trigger when user click on New menu
    public void handleNewMenu(ActionEvent e) {
        Dialog nd = new Dialog();
        nd.ShowDialog();
        if (nd.isOK()) {
            NewCanvas(nd.getInWidth(), nd.getInHeight());
        }
    }
    
    public void handleOpenMenu(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg", "*,jpeg", "*.bmp"));
        File f = fc.showOpenDialog(null);
        if(f != null) {
            try {
                Image i = new Image(new FileInputStream(f));
                NewCanvas(i.getWidth(), i.getHeight());
                cv.ctx.drawImage(i, 0.0, 0.0);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
