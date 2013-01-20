/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import javafx.scene.effect.ColorAdjust;

/**
 *
 * @author nvcnvn
 */
public class BrightnessHandler extends ColorEffectHandler {
    
    public BrightnessHandler(MainWindowController c) {
        super(c);
    }
    @Override
    public void applyEffect(double t) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(t);
        controller.cv.groundCtx.applyEffect(colorAdjust);
    }
}
