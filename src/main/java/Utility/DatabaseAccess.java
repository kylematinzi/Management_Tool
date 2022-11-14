package Utility;

import com.example.managementtool.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * This class is used to retrieve information from the database.
 */
public class DatabaseAccess {

    private static ObservableList<Project> allProjectsList = FXCollections.observableArrayList();

    public static ObservableList<Project> getAllProjects() {
        allProjectsList.clear();
        try {
            String sqlCommand = "SELECT * from Project_Table";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int projectId = rs.getInt("Project_Id");
                String projectTitle = rs.getString("Project_Title");
                LocalDate dateCreated = rs.getDate("Date_Created").toLocalDate();
                LocalDate dateClosed = rs.getDate("Projected_Completion").toLocalDate();
                String projectDescription = rs.getString("Project_Description");
                Project project = new Project(projectId, projectTitle, dateCreated, dateClosed, projectDescription);
                allProjectsList.add(project);
            }
            //Printing
            System.out.println(allProjectsList.toString());
        } catch (
                SQLException throwable) {
            throwable.printStackTrace();
        }
        return allProjectsList;
    }
}


//    try{
//        String sqlCommand = "SELECT * from contacts";
//        PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sqlCommand);
//        ResultSet results = ps.executeQuery();
//        while(results.next()){
//            allContactsList.add(new Contact(
//                    results.getInt("Contact_ID"),
//                    results.getString("Contact_Name"),
//                    results.getString("Email")
//            ));
//        }
//        results.close();
//    } catch (
//    SQLException throwables) {
//        throwables.printStackTrace();
//    }
//        return allContactsList;

