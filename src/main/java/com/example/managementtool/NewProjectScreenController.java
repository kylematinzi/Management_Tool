package com.example.managementtool;


import Utility.DatabaseConnection;
import Utility.DatabaseQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class NewProjectScreenController implements Initializable {

    @FXML
    private TextField projectTitleTextField;
    @FXML
    private DatePicker projectDatePicker;
    @FXML
    private TextArea projectDescriptionTextArea;
    @FXML
    private ComboBox projectTeamMembersComboBox;
    @FXML
    private Button assignTeamMemberButton;
    @FXML
    private TableView<Employee> teamMembersTable;
    @FXML
    private Button viewTeamMemberButton;
    @FXML
    private Button removeTeamMemberButton;
    @FXML
    private Button createProjectButton;
    @FXML
    private Button closeButton;
    private int newProjectId;
    private int projectId;
    private String projectTitle;
    private LocalDate dateCreated;
    private LocalDate projectedCompletionDate;
    private String projectDescription;

    //TODO not saving to the database. I think its the dates.
    public void createProjectButtonPressed(ActionEvent actionEvent) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String insertStatement = "INSERT INTO Employee_Table(Project_Id, Project_Title, Date_Created, "+
                "Projected_Completion, Project_Description) VALUES (?,?,?,?,?)";
        DatabaseQuery.setPreparedStatement(conn, insertStatement);
        try{
            projectId = createUniqueProjectId();
            projectTitle = projectTitleTextField.getText();
            dateCreated = LocalDate.now();
            projectedCompletionDate = projectDatePicker.getValue();
            projectDescription = projectDescriptionTextArea.getText();
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ps.setInt(1, projectId);
            ps.setString(2, projectTitle);
            ps.setDate(3, Date.valueOf(dateCreated));
            ps.setDate(4, Date.valueOf(projectedCompletionDate));
            ps.setString(5, projectDescription);
            ps.execute();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Project: "+ projectTitle+" has been added.");
            alert.setTitle("Confirmation");
            Optional<ButtonType> result = alert.showAndWait();
            ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();

        } catch (Exception e){}


    }

    public int createUniqueProjectId() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String maxStatement = "SELECT MAX(Project_Id) from Project_Table;";
        DatabaseQuery.setPreparedStatement(conn, maxStatement);
        PreparedStatement ps = DatabaseQuery.getPreparedStatement();
        ResultSet rs = ps.executeQuery();
        rs.next();
        newProjectId = rs.getInt(1) + 1;
        return newProjectId;
    }


    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
