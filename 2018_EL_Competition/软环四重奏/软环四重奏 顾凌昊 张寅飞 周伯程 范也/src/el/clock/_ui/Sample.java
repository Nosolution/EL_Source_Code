package el.clock._ui;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Sample extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		Pane root=new Clock();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
