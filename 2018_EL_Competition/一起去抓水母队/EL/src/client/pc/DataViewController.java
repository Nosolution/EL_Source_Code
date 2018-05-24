package client.pc;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class DataViewController extends AppController{
    //一坨控件
    public GridPane pane;
    public ImageView back;
    public Label name;
    public Label school;
    public Label score;
    public Label totalTime;
    public Label totalTimes;
    public ImageView timePieChartController;
    public ImageView timesPieChartController;
    public ImageView timeBarChartController;
    public ImageView timesBarChartController;
    private PieChart timePieChart;
    private PieChart timesPieChart;
    private BarChart<String, Number> timeBarChart;
    private BarChart<String, Number> timesBarChart;


    private XYChart.Series<String, Number> timeSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> timesSeries = new XYChart.Series<>();

    //一坨事件
    public void clickBack() {
        appWheel.setScene("HomeView");
    }
    public void enterTimePieChartController() {
        if (timesPieChart != null)
            timesPieChart.setVisible(false);
        if (timeBarChart != null)
            timeBarChart.setVisible(false);
        if (timesBarChart != null)
            timesBarChart.setVisible(false);
        if (timePieChart == null) {
            timePieChart = new PieChart();
            pane.add(timePieChart, 4, 1, 1, 8);
            timePieChart.getData().add(new PieChart.Data("Astronomy", appWheel.appUser.time[1]));
            timePieChart.getData().add(new PieChart.Data("Care Of Magical Creatures", appWheel.appUser.time[2]));
            timePieChart.getData().add(new PieChart.Data("Charms", appWheel.appUser.time[3]));
            timePieChart.getData().add(new PieChart.Data("Defence Against The Dark Arts", appWheel.appUser.time[4]));
            timePieChart.getData().add(new PieChart.Data("Divination", appWheel.appUser.time[5]));
            timePieChart.getData().add(new PieChart.Data("Flying Lessons", appWheel.appUser.time[6]));
            timePieChart.getData().add(new PieChart.Data("Herbology", appWheel.appUser.time[7]));
            timePieChart.getData().add(new PieChart.Data("History Of Magic", appWheel.appUser.time[8]));
            timePieChart.getData().add(new PieChart.Data("Muggle Studies", appWheel.appUser.time[9]));
            timePieChart.getData().add(new PieChart.Data("Potions", appWheel.appUser.time[10]));
            timePieChart.getData().add(new PieChart.Data("Transfiguration", appWheel.appUser.time[11]));
        } else {
            timePieChart.setVisible(true);
            int i = 0;
            for (PieChart.Data data : timePieChart.getData()) {
                i++;
                data.setPieValue(appWheel.appUser.time[i]);
            }
        }
    }
    public void enterTimesPieChartController() {
        if (timePieChart != null)
            timePieChart.setVisible(false);
        if (timeBarChart != null)
            timeBarChart.setVisible(false);
        if (timesBarChart != null)
            timesBarChart.setVisible(false);
        if (timesPieChart == null) {
            timesPieChart = new PieChart();
            pane.add(timesPieChart, 4, 1, 1, 8);
            timesPieChart.getData().add(new PieChart.Data("Astronomy", appWheel.appUser.times[1]));
            timesPieChart.getData().add(new PieChart.Data("Care Of Magical Creatures", appWheel.appUser.times[2]));
            timesPieChart.getData().add(new PieChart.Data("Charms", appWheel.appUser.times[3]));
            timesPieChart.getData().add(new PieChart.Data("Defence Against The Dark Arts", appWheel.appUser.times[4]));
            timesPieChart.getData().add(new PieChart.Data("Divination", appWheel.appUser.times[5]));
            timesPieChart.getData().add(new PieChart.Data("Flying Lessons", appWheel.appUser.times[6]));
            timesPieChart.getData().add(new PieChart.Data("Herbology", appWheel.appUser.times[7]));
            timesPieChart.getData().add(new PieChart.Data("History Of Magic", appWheel.appUser.times[8]));
            timesPieChart.getData().add(new PieChart.Data("Muggle Studies", appWheel.appUser.times[9]));
            timesPieChart.getData().add(new PieChart.Data("Potions", appWheel.appUser.times[10]));
            timesPieChart.getData().add(new PieChart.Data("Transfiguration", appWheel.appUser.times[11]));
        } else {
            timesPieChart.setVisible(true);
            int i = 0;
            for (PieChart.Data data : timesPieChart.getData()) {
                i++;
                data.setPieValue(appWheel.appUser.times[i]);
            }
        }
    }
    public void enterTimeBarChartController() {
        if (timePieChart != null)
            timePieChart.setVisible(false);
        if (timesPieChart != null)
            timesPieChart.setVisible(false);
        if (timesBarChart != null)
            timesBarChart.setVisible(false);
        if (timeBarChart == null) {
            timeBarChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
            timeBarChart.setLegendVisible(false);
            pane.add(timeBarChart, 4, 1, 1, 8);
            timeSeries.getData().add(new XYChart.Data<>("Astronomy", appWheel.appUser.time[1]));
            timeSeries.getData().add(new XYChart.Data<>("Care Of Magical Creatures", appWheel.appUser.time[2]));
            timeSeries.getData().add(new XYChart.Data<>("Charms", appWheel.appUser.time[3]));
            timeSeries.getData().add(new XYChart.Data<>("Defence Against The Dark Arts", appWheel.appUser.time[4]));
            timeSeries.getData().add(new XYChart.Data<>("Divination", appWheel.appUser.time[5]));
            timeSeries.getData().add(new XYChart.Data<>("Flying Lessons", appWheel.appUser.time[6]));
            timeSeries.getData().add(new XYChart.Data<>("Herbology", appWheel.appUser.time[7]));
            timeSeries.getData().add(new XYChart.Data<>("History Of Magic", appWheel.appUser.time[8]));
            timeSeries.getData().add(new XYChart.Data<>("Muggle Studies", appWheel.appUser.time[9]));
            timeSeries.getData().add(new XYChart.Data<>("Potions", appWheel.appUser.time[10]));
            timeSeries.getData().add(new XYChart.Data<>("Transfiguration", appWheel.appUser.time[11]));
            timeBarChart.getData().add(timeSeries);
        } else {
            timeBarChart.setVisible(true);
            int i = 0;
            for (XYChart.Series<String, Number> series : timeBarChart.getData())
                for (XYChart.Data<String, Number> data : series.getData()) {
                    i++;
                    data.setYValue(appWheel.appUser.time[i]);
                }
        }
    }
    public void enterTimesBarChartController() {
        if (timePieChart != null)
            timePieChart.setVisible(false);
        if (timesPieChart != null)
            timesPieChart.setVisible(false);
        if (timeBarChart != null)
            timeBarChart.setVisible(false);
        if (timesBarChart == null) {
            timesBarChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
            timesBarChart.setLegendVisible(false);
            pane.add(timesBarChart, 4, 1, 1, 8);
            timesSeries.getData().add(new XYChart.Data<>("Astronomy", appWheel.appUser.times[1]));
            timesSeries.getData().add(new XYChart.Data<>("Care Of Magical Creatures", appWheel.appUser.times[2]));
            timesSeries.getData().add(new XYChart.Data<>("Charms", appWheel.appUser.times[3]));
            timesSeries.getData().add(new XYChart.Data<>("Defence Against The Dark Arts", appWheel.appUser.times[4]));
            timesSeries.getData().add(new XYChart.Data<>("Divination", appWheel.appUser.times[5]));
            timesSeries.getData().add(new XYChart.Data<>("Flying Lessons", appWheel.appUser.times[6]));
            timesSeries.getData().add(new XYChart.Data<>("Herbology", appWheel.appUser.times[7]));
            timesSeries.getData().add(new XYChart.Data<>("History Of Magic", appWheel.appUser.times[8]));
            timesSeries.getData().add(new XYChart.Data<>("Muggle Studies", appWheel.appUser.times[9]));
            timesSeries.getData().add(new XYChart.Data<>("Potions", appWheel.appUser.times[10]));
            timesSeries.getData().add(new XYChart.Data<>("Transfiguration", appWheel.appUser.times[11]));
            timesBarChart.getData().add(timesSeries);
        } else {
            timesBarChart.setVisible(true);
            int i = 0;
            for (XYChart.Series<String, Number> series : timesBarChart.getData())
                for (XYChart.Data<String, Number> data : series.getData()) {
                    i++;
                    data.setYValue(appWheel.appUser.times[i]);
                }
        }
    }

    //一坨函数
    void start() {
        name.setText("用户名: " + appWheel.appUser.name);
        switch (appWheel.appUser.school) {
            case "G":
                school.setText("学院：Gryffindor");
                break;
            case "H":
                school.setText("学院：Hufflepuff");
                break;
            case "R":
                school.setText("学院：Ravenclaw");
                break;
            case "S":
                school.setText("学院：Slytherin");
                break;
        }
        score.setText("总积分: " + appWheel.appUser.score);
        totalTime.setText("总时间:" + appWheel.appUser.time[0]);
        totalTimes.setText("总次数:" + appWheel.appUser.times[0]);
    }
}
