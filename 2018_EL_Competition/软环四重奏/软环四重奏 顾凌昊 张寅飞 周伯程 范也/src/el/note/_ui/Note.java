package el.note._ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Note extends Application {

    public void start(Stage primaryStage) throws Exception{
        URL location = getClass().getResource("el/note/res/note.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        noteController controller = fxmlLoader.getController();
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            fxmlLoader.<noteController>getController().exit();
        });

        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }




/*public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("notes2.fxml"));
        Parent root = loader.load();

        stage.setOnCloseRequest(e -> {
            e.consume();
            loader.<notesController>getController().exit();
        });
        notesController controller = loader.getController();
        stage.setScene(new Scene(root));


        stage.show();
    }*/
    public static void main(String[] args) {
        launch(args);
    }
}



//stage.setOnShowing(event -> {
//            event.consume();
//            loader.<notesController>getController().outtime();
//        });