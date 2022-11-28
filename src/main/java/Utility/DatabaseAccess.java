package Utility;

import com.example.managementtool.Employee;
import com.example.managementtool.Project;
import com.example.managementtool.Ticket;
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
    private static ObservableList<Ticket> allTicketsList = FXCollections.observableArrayList();
    private static ObservableList<Employee> allEmployeesList = FXCollections.observableArrayList();
    private static ObservableList<Ticket> selectedTicketsList = FXCollections.observableArrayList();

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
        } catch (
                SQLException throwable) {
            throwable.printStackTrace();
        }
        return allProjectsList;
    }

    public static ObservableList<Ticket> getAllTickets() {
        allTicketsList.clear();
        try {
            String sqlCommand = "SELECT * from Ticket_Table";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ticketId = rs.getInt("Ticket_Id");
                int ticketProjectId = rs.getInt("Project_Association");
                String ticketTitle = rs.getString("Ticket_Title");
                String ticketPriority = rs.getString("Priority_Level");
                String ticketStatus = rs.getString("Ticket_Status");
                LocalDate dateCreated = rs.getDate("Date_Created").toLocalDate();
                LocalDate dateCompleted = rs.getDate("Date_Completed").toLocalDate();
                String ticketDescription = rs.getString("Ticket_Description");
                Ticket ticket = new Ticket(ticketId, ticketProjectId, ticketTitle, ticketPriority, ticketStatus, dateCreated,
                        dateCompleted, ticketDescription);
                allTicketsList.add(ticket);
            }
        } catch (
                SQLException throwable) {
            throwable.printStackTrace();
        }
        return allTicketsList;
    }

    public static ObservableList<Employee> getAllEmployees() {
        allEmployeesList.clear();
        try {
            String sqlCommand = "SELECT * from Employee_Table";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sqlCommand);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int employeeId = rs.getInt("Employee_Id");
                String employeeFirstName = rs.getString("Employee_First_Name");
                String employeeLastName = rs.getString("Employee_Last_Name");
                String username = rs.getString("Username");
                String jobTitle = rs.getString("Job_Title");
                String emailAddress = rs.getString("Email_Address");
                String employeePassword = rs.getString("Employee_Password");
                Employee employee = new Employee(employeeId, employeeFirstName, employeeLastName, username, jobTitle,
                        emailAddress, employeePassword);
                allEmployeesList.add(employee);
            }
        } catch (
                SQLException throwable) {
            throwable.printStackTrace();
        }
        return allEmployeesList;
    }

    public static ObservableList<Ticket> getSelectedTickets(int projectId){
        selectedTicketsList.clear();
        try{
            String sqlCommand = "SELECT * from Ticket_Table WHERE Project_Association = ?";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sqlCommand);
            ps.setInt(1, projectId);
            ResultSet results = ps.executeQuery();
            while(results.next()){
                selectedTicketsList.add(new Ticket(
                        results.getInt("Ticket_ID"),
                        results.getInt("Project_Association"),
                        results.getString("Ticket_Title"),
                        results.getString("Priority_Level"),
                        results.getString("Ticket_Status"),
                        results.getDate("Date_Created").toLocalDate(),
                        results.getDate("Date_Completed").toLocalDate(),
                        results.getString("Ticket_Description")
                ));
            }
            results.close();
        } catch (Exception throwables){
            //Can switch exception to SQLException
            throwables.printStackTrace();
        }
        return selectedTicketsList;
    }
}


