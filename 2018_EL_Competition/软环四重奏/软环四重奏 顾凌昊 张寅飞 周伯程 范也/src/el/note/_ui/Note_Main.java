package el.note._ui;

import el._main.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javax.sound.midi.Soundbank;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Note_Main extends AnchorPane {
	Controller control;

	public Note_Main(Controller c) {
		control=c;
		URL location = getClass().getClassLoader().getResource("el/note/res/note.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent root=null;
		try {
			root = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Button exit=new Button("不忘初心地返回 ");
		
		exit.setLayoutX(150);
		exit.setLayoutY(640);
		exit.setPrefSize(130, 40);
		
		
		exit.setOnAction(event ->{
			fxmlLoader.<noteController>getController().exit();
			c.toTitle();
		});
		this.getChildren().add(new ImageView(new Image("el/note/_ui/resource 5.png")));
		this.getChildren().addAll(root,exit);


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

}