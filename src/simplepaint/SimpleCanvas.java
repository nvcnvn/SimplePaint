/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.*;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author nvcnvn
 */
public class SimpleCanvas extends AnchorPane {
    //canvas chinh de ve
    public Canvas ground;
    //canvas de the hien cac cong cu nhu copy, cut
    public Canvas tool;
    public GraphicsContext groundCtx;
    public GraphicsContext toolCtx;

    public SimpleCanvas(double w, double h) {
        this.setWidth(w);
        this.setHeight(h);
        this.setStyle("-fx-background-color: white;");
        this.ground = new Canvas(w, h);
        this.tool = new Canvas(w, h);
        this.groundCtx = this.ground.getGraphicsContext2D();
        this.toolCtx = this.tool.getGraphicsContext2D();
        this.getChildren().add(tool);
        this.getChildren().add(ground);
        ground.toFront();
        //quan ly menu chuot Paste
        MenuItem it = new MenuItem("Paste");
        it.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                Image img = Clipboard.getSystemClipboard().getImage();
                if (img != null) {
                    groundCtx.drawImage(img, 0.0, 0.0);
                }
            }
        });
        final ContextMenu cm = new ContextMenu();
        cm.getItems().add(it);
        //show menu chuot phai
        this.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (e.getButton() == MouseButton.SECONDARY) {
                            cm.show(ground, e.getScreenX(), e.getScreenY());
                        }
                    }
                });
    }
}
