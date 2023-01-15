package Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class is used for sending queries to the database.
 */
public class DatabaseQuery {

    private static PreparedStatement preparedStatement;

    /**
     * This method will set the prepared statement for querying the database.
     * @param conn - connection to the database
     * @param sqlStatement - statement being sent to the database
     * @throws SQLException
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {

        preparedStatement = conn.prepareStatement(sqlStatement);

    }

    /**
     * This method retrieves the prepared statement.
     * @return - prepared statement
     */
    public static PreparedStatement getPreparedStatement(){
        return preparedStatement;
    }
}
