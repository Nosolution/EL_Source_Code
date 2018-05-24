package el.chart._ui;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import el._main.DataController;
import el.clock._ui.Day;

public class chartController {

	private int[] time=new int[7];
    
    public  Label total;
    public  Label highest ;
    public Label taskcount,lasttime,totalTime,efficiency;
    public ImageView bg=null;
    public NumberAxis mxAxis = new NumberAxis("the last seven days", 1d, 7d, 1d);
    public NumberAxis myAxis = new NumberAxis("hours of your concentration ", 0.0d, 20.0d, 1d);

    private ArrayList<Day> days;

    ObservableList<StackedAreaChart.Series> areaChartData; 

    public LineChart mchart=new LineChart(mxAxis,myAxis);

    public  void initialize() {
    	
    	days=DataController.getDays();
    	for(int i=days.size()-1,j=0;i>=0;i--,j++) {
    		time[6-j]=(int) (days.get(i).getTotalActualTime()/1000/60);
    	}
    	int totaltime=0,maxTime=0;
    	for(int i=0;i<7;i++) {
    		maxTime=(maxTime>time[i])?maxTime :time[i];
    		totaltime+=time[i];
    	}
    	
    	if(!days.isEmpty()) lasttime.setText(days.get(days.size()-1).getDate());
    	efficiency.setText("100%");
    	int tasks=0;
    	float eff=0;
    	totaltime=0;
    	for(Day d:days) {
    		tasks+=d.getTasks().size();
    		totaltime+=d.getTotalActualTime();
    	}
    	
    	taskcount.setText(tasks+"");
    	totalTime.setText((int)(totaltime/60/1000)+"min");
    	
    	totaltime=0;
    	
    	
    	
    	total.setText(totaltime+" min");
    	highest.setText(maxTime+"  min");
    	//mchart.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    	areaChartData=
                FXCollections.observableArrayList(
                        new StackedAreaChart.Series("Series",
                                FXCollections.observableArrayList(
                                        new LineChart.Data(1, time[0]),
                                        new LineChart.Data(2, time[1]),
                                        new LineChart.Data(3, time[2]),
                                        new LineChart.Data(4, time[3]),
                                        new LineChart.Data(5, time[4]),
                                        new LineChart.Data(6, time[5]),
                                        new LineChart.Data(7, time[6])
                                ))
                );
    	
        mchart.setData(areaChartData);
       
    }
}
