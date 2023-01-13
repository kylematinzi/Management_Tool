package com.example.managementtool;

import Utility.DatabaseAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * This class is the screen controller for the all projects screen. The screen displays a list off all the projects held
 * in the database, a pie chart to show the currently selected projects completion breakdown, the project's current
 * completion percentage, and the project's projected completion date.
 */
public class allProjectsScreenController implements Initializable {

    @FXML
    private Label projectedDateLabel;
    @FXML
    private Label completionPercentLabel;
    @FXML
    private TableView<Project> allProjectsTableView;
    @FXML
    private PieChart projectsPieChart;
    @FXML
    private ProgressBar projectCompletionBar;
    @FXML
    private TableColumn<Project, Integer> projectIdColumn;
    @FXML
    private TableColumn<Project, String> projectTitleColumn;
    private int selectedProjectId;
    private static final ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

    /**
     * This method gathers the data needed to fill the all projects screen pie chart.
     * @return Data used to fill the all projects pie chart.
     */
    public static ObservableList<PieChart.Data> fillProjectsPieChart(int projectId) throws SQLException {
        int notStarted, inWork, complete;
        notStarted = DatabaseAccess.countNotStartedTickets(projectId);
        inWork = DatabaseAccess.countInWorkTickets(projectId);
        complete = DatabaseAccess.countCompletedTickets(projectId);
        pieChartData.clear();
        pieChartData.add(new PieChart.Data("Not started: "+ notStarted, notStarted));
        pieChartData.add(new PieChart.Data("In work: "+ inWork, inWork));
        pieChartData.add(new PieChart.Data("Complete: "+ complete, complete));
        return pieChartData;
    }

    /**
     * This method is used to find which project the user has clicked on in the all projects table.
     * @param mouseEvent mouse click
     * @throws SQLException
     */
    public void mouseClicked(MouseEvent mouseEvent) throws SQLException {
        selectedProjectId = allProjectsTableView.getSelectionModel().getSelectedItem().getProjectId();
        projectsPieChart.setData(fillProjectsPieChart(selectedProjectId));
        projectedDateLabel.setText(String.valueOf(allProjectsTableView.getSelectionModel().getSelectedItem().getDateCompleted()));
        projectCompletionBar.setProgress((DatabaseAccess.projectCompletionPercentage(selectedProjectId)) / 100);
        completionPercentLabel.setText((int) DatabaseAccess.projectCompletionPercentage(selectedProjectId)+"%");
    }

    /**
     * This method closes the all projects screen when the close button is pressed.
     * @param actionEvent
     * @throws IOException
     */
    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    /**
     * This method initializes the all projects screen with the correct data to display to the user.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            allProjectsTableView.setItems(DatabaseAccess.getAllProjects());
            allProjectsTableView.getSelectionModel().select(0);
            selectedProjectId = allProjectsTableView.getSelectionModel().getSelectedItem().getProjectId();
            projectIdColumn.setCellValueFactory((new PropertyValueFactory<>("projectId")));
            projectTitleColumn.setCellValueFactory((new PropertyValueFactory<>("projectTitle")));
            projectedDateLabel.setText(String.valueOf(allProjectsTableView.getSelectionModel().getSelectedItem().getDateCompleted()));
            projectsPieChart.setData(fillProjectsPieChart(allProjectsTableView.getSelectionModel().getSelectedItem().getProjectId()));
            projectCompletionBar.setProgress((DatabaseAccess.projectCompletionPercentage(selectedProjectId)) / 100);
            completionPercentLabel.setText((int) DatabaseAccess.projectCompletionPercentage(selectedProjectId)+"%");
        }catch (Exception e){
        }
    }
}
