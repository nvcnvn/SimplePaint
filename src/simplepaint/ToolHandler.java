/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplepaint;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author nvcnvn
 */
public class ToolHandler implements EventHandler<MouseEvent>, ChangeListener<Boolean> {

    private MainWindowController c;
    private boolean islining = false;
    private boolean iscopying = false;
    private boolean iscuting = false;
    //vi tri chuot ban dau
    private double currX;
    private double currY;
    private ClipboardContent content = new ClipboardContent();

    public ToolHandler(MainWindowController c) {
        this.c = c;
    }

    @Override
    public void handle(MouseEvent t) {
        double x = t.getX();
        double y = t.getY();

        if (c.btCopy.isSelected()) {
            //neu la click chuot
            if (t.getClickCount() == 1 && t.getButton().equals(MouseButton.PRIMARY)) {
                if (iscopying) {
                    c.cv.toolCtx.clearRect(0.0, 0.0, c.cv.tool.getWidth(), c.cv.tool.getHeight());
                    //copyt phan vung anh
                    SnapshotParameters sp = new SnapshotParameters();
                    sp.setViewport(new Rectangle2D(currX, currY, x - currX, y - currY));
                    content.putImage(c.cv.ground.snapshot(sp, null));
                    //bo vao bo nho phu
                    Clipboard.getSystemClipboard().setContent(content);
                    iscopying = false;
                } else {
                    iscopying = true;
                    currX = x;
                    currY = y;
                }
            } else {
                //chuot di chuyen
                if (iscopying) {
                    c.cv.toolCtx.clearRect(0.0, 0.0, c.cv.tool.getWidth(), c.cv.tool.getHeight());
                    c.cv.toolCtx.strokeRect(currX, currY, x - currX, y - currY);
                }
            }
        }
        if (c.btCut.isSelected()) {
            if (t.getClickCount() == 1 && t.getButton().equals(MouseButton.PRIMARY)) {
                if (iscuting) {
                    c.cv.toolCtx.clearRect(0.0, 0.0, c.cv.tool.getWidth(), c.cv.tool.getHeight());
                    SnapshotParameters sp = new SnapshotParameters();
                    sp.setViewport(new Rectangle2D(currX, currY, x - currX, y - currY));
                    content.putImage(c.cv.ground.snapshot(sp, null));
                    Clipboard.getSystemClipboard().setContent(content);
                    c.cv.groundCtx.clearRect(currX, currY, x - currX, y - currY);
                    iscuting = false;
                } else {
                    iscuting = true;
                    currX = x;
                    currY = y;
                }
            } else {
                if (iscuting) {
                    c.cv.toolCtx.clearRect(0.0, 0.0, c.cv.tool.getWidth(), c.cv.tool.getHeight());
                    c.cv.toolCtx.strokeRect(currX, currY, x - currX, y - currY);
                }
            }
        }
        if (c.btLine.isSelected()) {
            if (t.getClickCount() == 1) {
                if (islining) {
                    //ve duogn thang that su
                    c.cv.toolCtx.clearRect(0.0, 0.0, c.cv.tool.getWidth(), c.cv.tool.getHeight());
                    c.cv.groundCtx.beginPath();
                    c.cv.groundCtx.setStroke(c.ColorPicker.getValue());
                    c.cv.groundCtx.setLineWidth(c.SizePicker.getValue());
                    c.cv.groundCtx.moveTo(currX, currY);
                    c.cv.groundCtx.lineTo(x, y);
                    c.cv.groundCtx.stroke();
                    c.cv.groundCtx.closePath();
                    islining = false;
                } else {
                    islining = true;
                    currX = x;
                    currY = y;
                }
            } else {
                if (islining) {
                    //the hien duong thang di chuyen theo chuot
                    c.cv.toolCtx.clearRect(0.0, 0.0, c.cv.tool.getWidth(), c.cv.tool.getHeight());
                    c.cv.toolCtx.beginPath();
                    c.cv.toolCtx.setStroke(c.ColorPicker.getValue());
                    c.cv.toolCtx.setLineWidth(c.SizePicker.getValue());
                    c.cv.toolCtx.moveTo(currX, currY);
                    c.cv.toolCtx.lineTo(x, y);
                    c.cv.toolCtx.stroke();
                    c.cv.toolCtx.closePath();
                }
            }
        }
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
        if (c.btLine.isSelected() || c.btCopy.isSelected() || c.btCut.isSelected()) {
            c.cv.tool.toFront();
        } else {
            c.cv.ground.toFront();
        }
    }
}
