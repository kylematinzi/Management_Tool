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

/**
 * This class is the screen controller for the new project screen. Through this screen the user may create a new project
 * by entering the appropriate data in the provided fields.
 */
public class NewProjectScreenController implements Initializable {

    @FXML
    private TextField projectTitleTextField;
    @FXML
    private TextField projectIdTextField;
    @FXML
    private DatePicker projectDatePicker;
    @FXML
    private TextArea projectDescriptionTextArea;
    @FXML
    private ComboBox<Employee> projectTeamMembersComboBox;
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

    /**
     * This method creates a new project when the create project button is pressed. The method does this by gathering the
     * data entered by the user in the given fields and saving it to the database.
     * @param actionEvent
     * @throws SQLException
     */
    //TODO input validation
    public void createProjectButtonPressed(ActionEvent actionEvent) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String insertStatement = "INSERT INTO  Project_Table (Project_Id, Project_Title, Date_Created,"+
                " Projected_Completion, Project_Description) Values (?,?,?,?,?)";
        DatabaseQuery.setPreparedStatement(conn, insertStatement);
        try{
            int projectId = Integer.parseInt(projectIdTextField.getText());
            String projectTitle = projectTitleTextField.getText();
            LocalDate dateCreated = LocalDate.now();
            LocalDate projectedCompletionDate = projectDatePicker.getValue();
            String projectDescription = projectDescriptionTextArea.getText();
            PreparedStatement ps = DatabaseQuery.getPreparedStatement();
            ps.setInt(1, projectId);
            ps.setString(2, projectTitle);
            ps.setDate(3, Date.valueOf(dateCreated));
            ps.setDate(4, Date.valueOf(projectedCompletionDate));
            ps.setString(5, projectDescription);
            ps.execute();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Project Id: " + projectId +": "+ projectTitle + " has been added.");
            alert.setTitle("Confirmation");
            Optional<ButtonType> result = alert.showAndWait();
            ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
            AdminDashboardController tableRefresh = new AdminDashboardController();
            tableRefresh.refreshAdminProjectTable();
        }
        catch (Exception e){
        }
    }

    /**
     * This method creates a unique project id for a new project. To accomplish this the method queries the database to
     * locate the highest value project id and then increments it by plus one to create the new id.
     * @return - unique project id
     * @throws SQLException
     */
    public int createUniqueProjectId() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String maxStatement = "SELECT MAX(Project_Id) from Project_Table;";
        DatabaseQuery.setPreparedStatement(conn, maxStatement);
        PreparedStatement ps = DatabaseQuery.getPreparedStatement();
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1) + 1;
    }

    /**
     * This method closes the new project screen when the close button is pressed.
     * @param actionEvent
     * @throws IOException
     */
    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    /**
     * This method initializes the new project screen with the correct default data to display to the user.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            projectIdTextField.setText(String.valueOf(createUniqueProjectId()));
        }
        catch (Exception e){
        }
    }
}
