package Utility;

import com.example.managementtool.Employee;
import com.example.managementtool.Project;
import com.example.managementtool.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
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
    private static ObservableList<String> allProjectNames = FXCollections.observableArrayList();
    private static ObservableList<Integer> allProjectIds = FXCollections.observableArrayList();
    private static ObservableList<String> ticketStatusTypes = FXCollections.observableArrayList();
    private static ObservableList<String> ticketPriorityLevels = FXCollections.observableArrayList();
    private static ObservableList<String> jobTitles = FXCollections.observableArrayList();

    private static int uniqueEmployeeId = 100;

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

    public static ObservableList<String> getAllProjectNames(){
        allProjectsList.clear();
        allProjectNames.clear();
        allProjectsList = getAllProjects();
        for(Project p: allProjectsList ){
            allProjectNames.add(p.getProjectTitle());
        }
        return allProjectNames;
    }

    public static ObservableList<Integer> getAllProjectId(){
        allProjectsList.clear();
        allProjectIds.clear();
        allProjectsList = getAllProjects();
        for(Project p: allProjectsList){
            allProjectIds.add(p.getProjectId());
        }
        return allProjectIds;
    }


    public static ObservableList<String> getTicketStatusTypes() {
        ticketStatusTypes.clear();
        ticketStatusTypes.add("Not Started");
        ticketStatusTypes.add("In Work");
        ticketStatusTypes.add("Complete");
        return ticketStatusTypes;
    }

    public static ObservableList<String> getTicketPriorityLevels(){
        ticketPriorityLevels.clear();
        ticketPriorityLevels.add("Low");
        ticketPriorityLevels.add("Medium");
        ticketPriorityLevels.add("High");
        return ticketPriorityLevels;
    }

    public static ObservableList<String> getJobTitles(){
        jobTitles.clear();
        jobTitles.add("Developer");
        jobTitles.add("UX Developer");
        jobTitles.add("Engineer");
        jobTitles.add("Human Resources");
        jobTitles.add("Data Analyst");
        return  jobTitles;
    }

    public static int countAllTickets(int projectId) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String countStatement = "SELECT COUNT(*) as Counted FROM Ticket_Table WHERE Project_Association = ?;";
        DatabaseQuery.setPreparedStatement(conn, countStatement);
        PreparedStatement ps = DatabaseQuery.getPreparedStatement();
        ps.setInt(1, projectId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int countNumber = rs.getInt(1);
        return countNumber;
    }

    public static int countNotStartedTickets(int projectId) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String countStatement = "SELECT COUNT(*) as Counted FROM Ticket_Table WHERE Ticket_Status = 'Not Started' && Project_Association = ?;";
        DatabaseQuery.setPreparedStatement(conn, countStatement);
        PreparedStatement ps = DatabaseQuery.getPreparedStatement();
        ps.setInt(1, projectId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int countNumber = rs.getInt(1);
        return countNumber;
    }
    public static int countInWorkTickets(int projectId) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String countStatement = "SELECT COUNT(*) as Counted FROM Ticket_Table WHERE Ticket_Status = 'In Work' && Project_Association = ?;";
        DatabaseQuery.setPreparedStatement(conn, countStatement);
        PreparedStatement ps = DatabaseQuery.getPreparedStatement();
        ps.setInt(1, projectId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int countNumber = rs.getInt(1);
        return countNumber;
    }

    public static int countCompletedTickets(int projectId) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String countStatement = "SELECT COUNT(*) as Counted FROM Ticket_Table WHERE Ticket_Status = 'Complete' && Project_Association = ?;";
        DatabaseQuery.setPreparedStatement(conn, countStatement);
        PreparedStatement ps = DatabaseQuery.getPreparedStatement();
        ps.setInt(1, projectId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int countNumber = rs.getInt(1);
        return countNumber;
    }

    public static float projectCompletionPercentage(int projectId) throws SQLException {
        float allTickets, completedTickets, projectPercentage;
        allTickets = countAllTickets(projectId);
        completedTickets = countCompletedTickets(projectId);
        projectPercentage = Math.round((completedTickets/allTickets) * 100);
        return projectPercentage;
    }




}


