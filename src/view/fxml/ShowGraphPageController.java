package view.fxml;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ShowGraphPageController {

    @FXML private GridPane gridPane;

    public void initial(AreaChart<Number, Number> chart) {
        AreaChart<Number, Number> thisChart = new AreaChart<>(new NumberAxis(), new NumberAxis());
        for (XYChart.Series<Number, Number> datum : chart.getData()) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(datum.getName());
            for (int i = 0; i < datum.getData().size(); i++) {
                XYChart.Data<Number, Number> data;
                data = new XYChart.Data<>(datum.getData().get(i).getXValue(),
                        datum.getData().get(i).getYValue());
                series.getData().add(data);
            }
            thisChart.getData().add(series);
        }
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.getChildren().add(thisChart);
    }
}
