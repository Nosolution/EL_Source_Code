package el.clock._ui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SettingStage {
	Clock clock;
	Stage stage;
	boolean suceedToSet;
	Label hour=new Label("h"),minute=new Label("m"),second=new Label("s");
	Button confirm=new Button("确认"),reset=new Button("清空"),cancel=new Button("取消");
	TextField h=new TextField("0"),m=new TextField("0"),s=new TextField("0");
	
	public SettingStage(Clock clock) {
		this.clock = clock;
		clock.start.setDisable(true);
		stage = new Stage();
		stage.setTitle("设置时间");
		
		Pane root = new Pane();
		h.setLayoutX(5);m.setLayoutX(85);s.setLayoutX(165);
		h.setLayoutY(30);m.setLayoutY(30);s.setLayoutY(30);
		
		confirm.setLayoutX(5);reset.setLayoutX(85);cancel.setLayoutX(165);
		confirm.setLayoutY(120);reset.setLayoutY(120);cancel.setLayoutY(120);
		confirm.setOnAction(e ->{
			exitWindow(1);
		});
		cancel.setOnAction(e ->{
			exitWindow(0);
		});
		reset.setOnAction(e ->{
			h.setText("0");
			m.setText("0"); 
			s.setText("0");
			
		});
		hour.setLayoutX(65);minute.setLayoutX(145);second.setLayoutX(205);
		hour.setLayoutY(30);minute.setLayoutY(30);second.setLayoutY(30);
		
		root.resize(250, 150);
		root.getChildren().addAll(h,m,s,hour,minute,second,reset,confirm,cancel);
		stage.setScene(new Scene(root));
		stage.showAndWait();
	}

	
	public void exitWindow(int i) {
		if(i!= 0) {
			suceedToSet = true;
			clock.start.setDisable(false);
			clock.startTiming(getTime());
		}
		else {
			clock.start.setDisable(false);
			suceedToSet = false;
		}
		stage.close();
	}
	
	public long getTime() {
		long s1=Long.parseLong(s.getText());
		long m1=Long.parseLong(m.getText());
		long h1=Long.parseLong(h.getText());
		return (s1+m1*60+h1*3600);
	}
}
