/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint.util;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author nvcnvn
 */
public class NumField extends TextField {

    public NumField() {
        this.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                char ar[] = t.getCharacter().toCharArray();
                char ch = ar[t.getCharacter().toCharArray().length - 1];
                if (!(ch >= '0' && ch <= '9')) {
                    System.out.println("The char you entered is not a number");
                    t.consume();
                }
            }
        });
    }
}
