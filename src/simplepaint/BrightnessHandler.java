/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 *
 * @author nvcnvn
 */
public class BrightnessHandler implements ChangeListener<Number> {

    private MainWindowController controller;

    public BrightnessHandler(MainWindowController c) {
        controller = c;
    }

    @Override
    public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
        //we cannot read image pixel from canvas. So we need convert it to an image
        WritableImage wimg = controller.cv.cv.snapshot(null, null);

        PixelWriter w = controller.cv.ctx.getPixelWriter();
        PixelReader r = wimg.getPixelReader();
        if (t.intValue() < t1.intValue()) {
            for (int i = 0; i < wimg.getWidth(); i++) {
                for (int j = 0; j < wimg.getHeight(); j++) {
                    w.setColor(i, j, r.getColor(i, j).brighter());
                }
            }
        } else {
            for (int i = 0; i < wimg.getWidth(); i++) {
                for (int j = 0; j < wimg.getHeight(); j++) {
                    w.setColor(i, j, r.getColor(i, j).darker());
                }
            }
        }
    }
}
