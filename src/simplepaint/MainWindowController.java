/**
 * Sample Skeleton for "MainWindow.fxml" Controller Class You can copy and paste
 * this code into your favorite IDE
 *
 */
package simplepaint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import simplepaint.util.Dialog;

public class MainWindowController
        implements Initializable {

    @FXML //  fx:id="BrightnessPicker"
    private Slider BrightnessPicker;
    @FXML //  fx:id="ColorPicker"
    public ColorPicker ColorPicker;
    @FXML //  fx:id="GammaPicker"
    private Slider GammaPicker;
    @FXML // fx:id="Painter"
    private Pane Painter;
    @FXML
    public Slider SizePicker;
    @FXML
    public ToggleButton btFill;
    @FXML
    public ToggleButton btStroke;
    @FXML
    public ToggleButton btRect;
    @FXML
    public ToggleButton btOval;
    @FXML
    public ToggleButton btPen;
    @FXML
    public ToggleButton btEraser;
    @FXML
    public ToggleButton btText;
    @FXML
    public ToggleButton btLine;
    @FXML
    public ToggleButton btCopy;
    @FXML
    public ToggleButton btCut;
    @FXML
    public ComboBox cbEffect;
    //
    public SimpleCanvas cv;
    //image for undo function
    private Image bkImg;
    //danh cho hieu ung
    private DisplacementMap displacementMap;
    //quan ly co ve
    private Brush brh;
    //quan ly cac tool nhu copy, cut..
    private ToolHandler th;
    //dia chi file
    private String fileStr = "";

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        brh = new Brush(this);
        th = new ToolHandler(this);
        NewCanvas(400.0, 400.0);
        //gan listen ner
        this.ColorPicker.setValue(Color.BLACK);
        this.GammaPicker.valueProperty().addListener(new GammaHandler(this));
        this.BrightnessPicker.valueProperty().addListener(new BrightnessHandler(this));
        this.SizePicker.valueProperty().addListener(brh);
        this.ColorPicker.valueProperty().addListener(brh.cllst);
        this.btFill.selectedProperty().addListener(brh.selst);
        this.btOval.selectedProperty().addListener(brh.selst);
        this.btRect.selectedProperty().addListener(brh.selst);
        this.btStroke.selectedProperty().addListener(brh.selst);
        this.btPen.selectedProperty().addListener(brh.selst);
        this.btEraser.selectedProperty().addListener(brh.selst);
        this.btText.selectedProperty().addListener(brh.selst);
        this.cbEffect.getSelectionModel().selectFirst();
        this.cbEffect.getSelectionModel().selectedIndexProperty().addListener(brh);

        this.btLine.selectedProperty().addListener(th);
        this.btCopy.selectedProperty().addListener(th);
        this.btCut.selectedProperty().addListener(th);

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

    public void NewCanvas(double w, double h) {
        //remove and replace the old canvas with a new one
        Painter.getChildren().remove(cv);
        cv = new SimpleCanvas(w, h);
        cv.ground.addEventHandler(MouseEvent.MOUSE_DRAGGED, brh);
        cv.ground.addEventHandler(MouseEvent.MOUSE_CLICKED, brh);
        cv.tool.addEventHandler(MouseEvent.MOUSE_DRAGGED, th);
        cv.tool.addEventHandler(MouseEvent.MOUSE_CLICKED, th);
        cv.tool.addEventHandler(MouseEvent.MOUSE_MOVED, th);
        Painter.getChildren().add(cv);
    }

    //wil trigger when user click on New menu
    public void handleNewMenu(ActionEvent e) {
        //hien dialog lay thong tin tu nguoi dung w*h
        Dialog nd = new Dialog();
        nd.ShowDialog();
        if (nd.isOK()) {
            NewCanvas(nd.getInWidth(), nd.getInHeight());
        }
    }

    public void handleOpenMenu(ActionEvent e) {
        //hien dialog chon file
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg", "*,jpeg", "*.bmp"));
        File f = fc.showOpenDialog(null);
        if (f != null) {
            try {
                fileStr = f.getAbsolutePath();
                Image i = new Image(new FileInputStream(f));
                NewCanvas(i.getWidth(), i.getHeight());
                cv.groundCtx.drawImage(i, 0.0, 0.0);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void handleSaveMenu(ActionEvent e) {
        File f;
        String extension = "png";
        if (fileStr.length() > 0) {
            //neu la file dc mo thi chi luu lai
            f = new File(fileStr);
        } else {
            //neu la file tao moi thi hien ban luu
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                    new FileChooser.ExtensionFilter("JPG Image", "*.jpg"),
                    new FileChooser.ExtensionFilter("JPEG Image", "*,jpeg"),
                    new FileChooser.ExtensionFilter("Bitmap Image", "*.bmp"));
            f = fc.showSaveDialog(null);
        }
        //neu f == null co nghia la nguoi dung khong luu file
        if(f == null) {
            return;
        }
        //thu lay ten duoi
        int i = f.getName().lastIndexOf('.');
        int p = Math.max(f.getName().lastIndexOf('/'), f.getName().lastIndexOf('\\'));

        if (i > p) {
            extension = f.getName().substring(i + 1);
        }

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(cv.ground.snapshot(null, null), null), extension, f);
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleUnduMenu(ActionEvent e) {
        if (bkImg != null) {
            cv.groundCtx.drawImage(bkImg, 0, 0);
        }
    }

    public void bkImage() {
        bkImg = cv.ground.snapshot(null, null);
    }

    public void handleBoxBlurMenu(ActionEvent e) {
        BoxBlur boxBlur = new BoxBlur();
        boxBlur.setWidth(10);
        boxBlur.setHeight(3);
        boxBlur.setIterations(3);
        bkImage();
        cv.groundCtx.applyEffect(boxBlur);
    }

    public void handleBloomMenu(ActionEvent e) {
        Bloom bloom = new Bloom();
        bloom.setThreshold(0.1);
        bkImage();
        cv.groundCtx.applyEffect(bloom);
    }

    public void handleDisplacementMapMenu(ActionEvent e) {
        bkImage();
        cv.groundCtx.applyEffect(displacementMap);
    }

    public void handleGlowMenu(ActionEvent e) {
        bkImage();
        cv.groundCtx.applyEffect(new Glow(0.8));
    }

    public void handleSepiaToneMenu(ActionEvent e) {
        SepiaTone sepiaTone = new SepiaTone();
        sepiaTone.setLevel(0.7);
        bkImage();
        cv.groundCtx.applyEffect(sepiaTone);
    }
}
