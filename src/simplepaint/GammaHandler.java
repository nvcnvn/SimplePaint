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
import javafx.scene.paint.Color;

/**
 *
 * @author nvcnvn
 */
public class GammaHandler implements ChangeListener<Number> {

    private MainWindowController controller;

    public GammaHandler(MainWindowController c) {
        this.controller = c;
    }

    @Override
    public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
        //we cannot read image pixel from canvas. So we need convert it to an image
        WritableImage wimg = controller.cv.cv.snapshot(null, null);
        // Create the gamma correction lookup table
        int[] gamma_LUT = new int[256];

        for (int i = 0; i < gamma_LUT.length; i++) {
            gamma_LUT[i] = (int) (255 * (Math.pow((double) i / (double) 255, t1.doubleValue())));
        }
        PixelWriter w = controller.cv.ctx.getPixelWriter();
        PixelReader r = wimg.getPixelReader();
        for (int i = 0; i < wimg.getWidth(); i++) {
            for (int j = 0; j < wimg.getHeight(); j++) {
                Color oldc = r.getColor(i, j);
                int old = r.getArgb(i, j);
                int alpha = (old >> 24) & 0xff;
                int red = (old >> 16) & 0xff;
                int green = (old >> 8) & 0xff;
                int blue = old & 0xff;
                w.setColor(i, j, Color.rgb(gamma_LUT[red], gamma_LUT[green], gamma_LUT[blue], oldc.getOpacity()));
            }
        }
    }
}
