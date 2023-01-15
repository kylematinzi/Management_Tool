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

    /**
     * This method returns a list of all the projects in the database.
     * @return - list of projects
     */
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

    /**
     * This method returns a list of all the tickets in the database.
     * @return - list of tickets
     */
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

    /**
     * This method returns a list of all employees in the database.
     * @return - list of employees
     */
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

    /**
     * This method returns a list of tickets that are assigned to a selected project.
     * @param projectId - project used to find tickets
     * @return - list of tickets
     */
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
            throwables.printStackTrace();
        }
        return selectedTicketsList;
    }

    /**
     * This method returns a list of all the project names in the database.
     * @return - list of project names
     */
    public static ObservableList<String> getAllProjectNames(){
        allProjectsList.clear();
        allProjectNames.clear();
        allProjectsList = getAllProjects();
        for(Project p: allProjectsList ){
            allProjectNames.add(p.getProjectTitle());
        }
        return allProjectNames;
    }

    /**
     * This method returns a list of all project ids in the database.
     * @return - list of project ids
     */
    public static ObservableList<Integer> getAllProjectId(){
        allProjectsList.clear();
        allProjectIds.clear();
        allProjectsList = getAllProjects();
        for(Project p: allProjectsList){
            allProjectIds.add(p.getProjectId());
        }
        return allProjectIds;
    }

    /**
     * This method fills and returns a list with all possible ticket statuses.
     * @return - list of ticket status types
     */
    public static ObservableList<String> getTicketStatusTypes() {
        ticketStatusTypes.clear();
        ticketStatusTypes.add("Not Started");
        ticketStatusTypes.add("In Work");
        ticketStatusTypes.add("Complete");
        return ticketStatusTypes;
    }

    /**
     * This method fills and returns a list with all possible ticket priorities.
     * @return - list of ticket priority types
     */
    public static ObservableList<String> getTicketPriorityLevels(){
        ticketPriorityLevels.clear();
        ticketPriorityLevels.add("Low");
        ticketPriorityLevels.add("Medium");
        ticketPriorityLevels.add("High");
        return ticketPriorityLevels;
    }

    /**
     * This method fills and returns a list with all possible employee job titles.
     * @return - list of job titles
     */
    public static ObservableList<String> getJobTitles(){
        jobTitles.clear();
        jobTitles.add("Developer");
        jobTitles.add("UX Developer");
        jobTitles.add("Engineer");
        jobTitles.add("Human Resources");
        jobTitles.add("Data Analyst");
        return  jobTitles;
    }

    /**
     * This method queries the database to calculate how many tickets are in the database assigned to a selected project.
     * @param projectId - project whose tickets are being counted
     * @return - total amount of tickets
     * @throws SQLException
     */
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

    /**
     * This method queries the database to calculate how many not started tickets are in the database assigned to a selected
     * project.
     * @param projectId - project whose not started tickets are being counted
     * @return - total number of not started tickets
     * @throws SQLException
     */
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

    /**
     * This method queries the database to calculate how many in work tickets are in the database assigned to a selected
     * project.
     * @param projectId - project whose in work tickets are being counted
     * @return - total number of in work tickets
     * @throws SQLException
     */
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

    /**
     * This method queries the database to calculate how many complete tickets are in the database assigned to a selected
     * project.
     * @param projectId - project whose complete tickets are being counted
     * @return - total number of complete tickets
     * @throws SQLException
     */
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

    /**
     * This method calculates the selected projects current completion percentage out of 100.
     * @param projectId - selected project
     * @return - projects current completion percentage.
     * @throws SQLException
     */
    public static float projectCompletionPercentage(int projectId) throws SQLException {
        float allTickets, completedTickets, projectPercentage;
        allTickets = countAllTickets(projectId);
        completedTickets = countCompletedTickets(projectId);
        projectPercentage = Math.round((completedTickets/allTickets) * 100);
        return projectPercentage;
    }
}


