/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint.util;

import java.io.IOException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 *
 * @author nvcnvn
 */
public class Dialog extends Stage {
    @FXML
    private NumField inputWidth;
    @FXML
    private NumField inputHeight;
    @FXML
    private Button btOK;
    @FXML
    private Button btCancle;
    
    private boolean ok = false;
    public boolean isOK() {
        return ok;
    }
    
    public IntegerProperty inWidth = new SimpleIntegerProperty(420);
    public IntegerProperty inHeigth = new SimpleIntegerProperty(300);
    
    public int getInWidth() {
        return inWidth.get();
    }
    
    public int getInHeight() {
        return inHeigth.get();
    }
    
    public Dialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dialog.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }        
    }
    
    public void handleOKClick(ActionEvent event) {
        int w, h;
        try {
            w = Integer.parseInt(inputWidth.getText());
            h = Integer.parseInt(inputHeight.getText());
        } catch (NumberFormatException e) {
            System.out.print(e.toString());
            ok = false;
            close();
            return;
        }
        inWidth.set(w);
        inHeigth.set(h);
        ok = true;
        close();
    }
    
    public void handleCancelClick(ActionEvent event) {
        ok = false;
        close();
    }
    
    public void ShowDialog() {
        centerOnScreen();
        showAndWait();
    }
}
