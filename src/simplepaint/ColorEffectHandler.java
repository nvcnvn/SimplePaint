/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.WritableImage;

/**
 *
 * @author nvcnvn
 */
public abstract class ColorEffectHandler implements ChangeListener<Number> {

    protected MainWindowController controller;
    private WritableImage wimg;

    public ColorEffectHandler(MainWindowController c) {
        controller = c;
    }

    @Override
    public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
        if(t.doubleValue() == 0.0) {
            wimg = controller.cv.ground.snapshot(null, wimg);
        }
        if(wimg != null) {
            controller.cv.groundCtx.drawImage(wimg, 0, 0);
        }
        applyEffect(t1.doubleValue());       
    }
    
    public abstract void applyEffect(double t);
}
