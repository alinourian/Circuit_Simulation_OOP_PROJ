package view.fxml;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;

public class ShowGraphPageController {

    @FXML private GridPane gridPane;

    private final AreaChart<Number, Number> thisChart = new AreaChart<>(new NumberAxis(), new NumberAxis());

    public ShowGraphPageController() {
        this.thisChart.setStyle("-fx-font-style: italic");
    }

    public void initial(AreaChart<Number, Number> chart) {
        gridPane.setPadding(new Insets(20, 20, 20, 20));
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
        thisChart.setTitle(chart.getTitle());
        thisChart.setCreateSymbols(false);
        gridPane.getChildren().add(thisChart);

    }
}
