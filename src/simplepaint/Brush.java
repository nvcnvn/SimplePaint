/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.effect.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author nvcnvn
 */
public class Brush implements EventHandler<MouseEvent>, ChangeListener<Number> {

    private MainWindowController c;
    private DisplacementMap displacementMap;
    private double size = 2.0;
    private boolean fill = true;
    private boolean rect = true;
    private boolean pen = true;
    private boolean eraser = false;
    private boolean text = false;
    private Color clr = Color.WHITE;
    public ChangeListener<Boolean> selst = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
            if (c.btEraser.isSelected()) {
                eraser = true;
                pen = false;
                text = false;
            } else if (c.btPen.isSelected()) {
                pen = true;
                eraser = false;
                text = false;
            } else {
                text = true;
                pen = false;
                eraser = false;
            }
            if (c.btFill.isSelected()) {
                fill = true;
            } else {
                fill = false;
            }
            if (c.btRect.isSelected()) {
                rect = true;
            } else {
                rect = false;
            }
        }
    };
    public ChangeListener<Color> cllst = new ChangeListener<Color>() {
        @Override
        public void changed(ObservableValue<? extends Color> ov, Color t, Color t1) {
            clr = t1;
        }
    };

    public Brush(MainWindowController c) {
        this.c = c;
        //for the DisplacementMap effect
        int width = 220;
        int height = 100;
        FloatMap floatMap = new FloatMap();
        floatMap.setWidth(width);
        floatMap.setHeight(height);

        for (int i = 0; i < width; i++) {
            double v = (Math.sin(i / 20.0 * Math.PI) - 0.5) / 40.0;
            for (int j = 0; j < height; j++) {
                floatMap.setSamples(i, j, 0.0f, (float) v);
            }
        }
        displacementMap = new DisplacementMap();
        displacementMap.setMapData(floatMap);
    }

    @Override
    public void handle(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        c.cv.groundCtx.restore();
        if (e.getButton().equals(MouseButton.PRIMARY)) {
            if (e.getClickCount() == 2) {
                System.out.println("Double clicked");
            }
        }

        if (c.btEraser.isSelected()) {
            c.cv.groundCtx.clearRect(x - size / 2, y - size / 2, size, size);
        } else if (c.btPen.isSelected()) {
            if (fill) {
                c.cv.groundCtx.setFill(clr);
                if (rect) {
                    c.cv.groundCtx.fillRect(x - size / 2, y - size / 2, size, size);
                } else {
                    c.cv.groundCtx.fillOval(x - size / 2, y - size / 2, size, size);
                }
            } else {
                c.cv.groundCtx.setStroke(clr);
                if (rect) {
                    c.cv.groundCtx.strokeRect(x - size / 2, y - size / 2, size, size);
                } else {
                    c.cv.groundCtx.strokeOval(x - size / 2, y - size / 2, size, size);
                }
            }
        }
    }

    @Override
    public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
        size = c.SizePicker.getValue();

    }
}