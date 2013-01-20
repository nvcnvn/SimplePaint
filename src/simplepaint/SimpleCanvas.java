/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import javafx.event.EventHandler;
import javafx.scene.canvas.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author nvcnvn
 */
public class SimpleCanvas extends AnchorPane {

    public Canvas cv;
    public GraphicsContext ctx;

    public SimpleCanvas(double w, double h) {
        this.setWidth(w);
        this.setHeight(h);
        this.setStyle("-fx-background-color: white;");
        this.cv = new Canvas(w, h);
        this.ctx = this.cv.getGraphicsContext2D();
        this.getChildren().add(cv);
        cv.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        ctx.clearRect(e.getX() - 2, e.getY() - 2, 5, 5);
                    }
                });
    }
}
