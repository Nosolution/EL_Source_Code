package el.chart._ui;



import java.io.IOException;
import java.net.URL;

import el._main.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Chart_Main extends AnchorPane {
	Controller control;

   public Chart_Main(Controller c) {
	   control=c;
	   URL location = getClass().getClassLoader().getResource("el/chart/res/chart.fxml");
       FXMLLoader fxmlLoader = new FXMLLoader();
       fxmlLoader.setLocation(location);
       fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
       Parent root=null;
       try {
		root = fxmlLoader.load();
	} catch (IOException e) {
		e.printStackTrace();
	}
      // chartController controller = fxmlLoader.getController();
       
       Button exit=new Button("返回主界面");
		exit.setOnAction(event ->{
			c.toTitle();
		});
       this.getChildren().addAll(root,exit);
       
       
       this.setPrefSize(1280, 749);
   }

   
}
