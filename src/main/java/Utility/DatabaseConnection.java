package Utility;
import java.sql.*;


public class DatabaseConnection {

    private static Connection connection = null;


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

    public static void closeConnection(){
        try{
            connection.close();
        }
        catch (Exception e){

        }
    }
}
