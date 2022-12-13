package com.example.managementtool;

import Utility.DatabaseAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class allProjectsScreenController implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private ListView<String> projectsListView;
    @FXML
    private PieChart projectsPieChart;

    private static final ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();


    public static ObservableList<PieChart.Data> fillProjectsPieChart(){
        // just test data
        pieChartData.clear();
        pieChartData.add(new PieChart.Data("Not started", 20));
        pieChartData.add(new PieChart.Data("In work", 50));
        pieChartData.add(new PieChart.Data("Complete", 20));
        pieChartData.add(new PieChart.Data("Unassigned", 10));
        System.out.println(pieChartData);
        return pieChartData;
    }
    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            projectsListView.setItems(DatabaseAccess.getAllProjectNames());
            projectsPieChart.setData(fillProjectsPieChart());
        }catch (Exception e){

        }

    }
}
