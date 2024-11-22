package db02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnector {
    private static final String JDBC_URL_TEMPLATE = "jdbc:mysql://localhost:3306/%s";
    private static Connection connection;

    private MyConnector(){}

    public static void createConnection(String user, String password, String dbName) throws SQLException {
        String url = String.format(JDBC_URL_TEMPLATE, dbName);

        connection = DriverManager.getConnection(url, user, password);
    }
    public static Connection getConnection(){
        return connection;
    }
}
