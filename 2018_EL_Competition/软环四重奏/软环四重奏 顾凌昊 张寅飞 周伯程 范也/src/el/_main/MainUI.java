package el._main;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainUI extends Application{
	Stage stage;
	Scene scene;
	public void changePane(Parent root) {
	    scene=new Scene(root);
		stage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		
		stage=primaryStage;
		  
		new Controller(this).toTitle();
		
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
