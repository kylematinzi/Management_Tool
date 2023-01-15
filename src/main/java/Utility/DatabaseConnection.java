package Utility;
import java.sql.*;

/**
 * This class is used to start and close the connection with the mysql database.
 */
public class DatabaseConnection {
    private static Connection connection = null;

    /**
     * This method starts the connection with the database.
     * @return - connection
     */
    public static Connection startConnection(){
        try{
            connection = DriverManager.getConnection("jdbc:mysql://management-tool-database.c9wsdjztcano.us-east-1.rds.amazonaws.com:3306/Management_Tool_Database","admin", "AdmiN305");
            Statement statement = (connection).createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from Employee_Table");
            while (resultSet.next()){
                System.out.println(resultSet.getString("Employee_Last_Name"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * This method is used to close an open connection with the database.
     */
    public static void closeConnection(){
        try{
            connection.close();
        }
        catch (Exception e){
        }
    }

    /**
     * This method is used to retrieve the open database connection.
     * @return - connection
     */
    public static Connection getConnection(){
        return connection;
    }
}
