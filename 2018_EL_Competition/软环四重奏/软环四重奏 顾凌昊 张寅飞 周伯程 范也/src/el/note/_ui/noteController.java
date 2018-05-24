package el.note._ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
public class noteController {
    @FXML
    int today=8;//*关于时间的更改
    public int time(int n){return n;}//*时间设定
    public DatePicker picker;

    @FXML
    public TextArea notes;
    
    private Map<LocalDate, String> data = new HashMap<>();



    public void updateNotes() {
        data.put(picker.getValue(), notes.getText());
    }

    public void exit() {
        save();
        
    }

    private void save() {
        try (ObjectOutputStream stream = new ObjectOutputStream(Files.newOutputStream(Paths.get("note.data")))) {
            stream.writeObject(data);
            System.out.println("Saved!");
        } catch (Exception e) {
            System.out.println("Failed to save: " + e);
        }
    }

    private void load() {
        Path file = Paths.get("note.data");

        if (Files.exists(file)) {
            try (ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(file))) {
                data = (Map<LocalDate, String>) stream.readObject();
                System.out.println("Loaded!");
            } catch (Exception e) {
                System.out.println("Failed to load: " + e);
            }
        }
    }
    public void initialize() {
        
        load();

        picker.valueProperty().addListener((o, oldDate, date) -> {
            notes.setText(data.getOrDefault(date, ""));
            notes.setEffect(new DropShadow());
        });

        picker.setValue(LocalDate.now());

    }
      /*public void output() {
        todaytotal.setText(String.valueOf(today));
        thistime.setText(String.valueOf(today));
        percent.setText(String.valueOf(today));
    }*/

}