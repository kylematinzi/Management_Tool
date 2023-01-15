package com.example.managementtool;

import Utility.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is the screen controller for the log in screen. Here the user has to option to enter their username and
 * password. They also have the option to select forgot password which will allow them to send their username and password
 * to their previously provided email. There are two versions of the application accessed from the login screen. The first
 * version is the Admin version. To log in to this version username: Admin, password: Admin. The admin version provides
 * all available screens and information the application can provide. The second version is the user version. To access
 * this version use any other username and password combination located in the database such as username: JSmith, password: TestPassword.
 * The user version provides the user with a more limited version of the application simulating roles and restrictions.
 */
public class LoginScreenController implements Initializable {

    @FXML
    private Button loginButton;
    @FXML
    private Button closeButton;
    @FXML
    private TextField usernameTextBox;
    @FXML
    private PasswordField passwordField;
    private boolean usernamePasswordFound = false;

    /**
     *  This method opens the correct screen when a user is logging in. All users will see a generic user dashboard while
     * the administrator will have a more detailed screen showing details only they would be authorized to see. I have also
     * implemented the clearing of the password field in order to ensure when a user closes out someone could not come behind them
     * and press the login button to gain access.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void loginButtonPressed (ActionEvent actionEvent) throws IOException, SQLException {
        Connection conn = DatabaseConnection.getConnection();
        Statement statement = conn.createStatement();
        String userName = usernameTextBox.getText();
        String passWord = passwordField.getText();
        String sqlFinder = "SELECT Username, Employee_Password FROM Employee_Table";
        ResultSet rs = statement.executeQuery(sqlFinder);
        while(rs.next()){
            String UserName = rs.getString("Username");
            String PassWord = rs.getString("Employee_Password");
            if(UserName.equals(userName) && PassWord.equals(passWord)){
                usernamePasswordFound = true;
            }
        }
        rs.close();
        if(usernamePasswordFound & userName.equals("Admin")) {
            StackPane adminDashboardParent = new StackPane();
            adminDashboardParent.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminDashboard.fxml"))));
            Scene scene = new Scene(adminDashboardParent);
            Stage adminDashboardScene = new Stage();
            adminDashboardScene.setScene(scene);
            adminDashboardScene.initModality(Modality.WINDOW_MODAL);
            adminDashboardScene.initOwner(((((Button) actionEvent.getSource()).getScene().getWindow())));
            adminDashboardScene.sizeToScene();
            adminDashboardScene.setResizable(false);
            adminDashboardScene.setTitle("Project Management System");
            adminDashboardScene.show();
            passwordField.clear();
            usernamePasswordFound = false;
        }
        else if(usernamePasswordFound){
            StackPane adminDashboardParent = new StackPane();
            adminDashboardParent.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("userDashboard.fxml"))));
            Scene scene = new Scene(adminDashboardParent);
            Stage adminDashboardScene = new Stage();
            adminDashboardScene.setScene(scene);
            adminDashboardScene.initModality(Modality.WINDOW_MODAL);
            adminDashboardScene.initOwner(((((Button) actionEvent.getSource()).getScene().getWindow())));
            adminDashboardScene.sizeToScene();
            adminDashboardScene.setResizable(false);
            adminDashboardScene.setTitle("Project Management System");
            adminDashboardScene.show();
            passwordField.clear();
            usernamePasswordFound = false;
        }
        else if(usernamePasswordFound == false){
            passwordField.clear();
            Alert alert = new Alert(Alert.AlertType.WARNING, "Incorrect Username Password Combination. Please try again.");
            alert.setTitle("Warning");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**
     * This method closes the login screen when the close button is pressed.
     * @param actionEvent
     * @throws IOException
     */
    public void closeButtonPressed(ActionEvent actionEvent) throws IOException {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    /**
     * This method initializes the login screen with default information when the screen is initially loaded.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
