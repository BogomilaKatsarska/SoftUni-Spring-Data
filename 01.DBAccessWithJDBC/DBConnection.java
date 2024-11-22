package db01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection connection;
    private static final String dbName = "minions_db";
    private static final String user = "root";
    private static final String password = "123456";


    public static Connection getConnection(){
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, properties);
        }catch (SQLException e){
            System.out.println("There was a problem init the db conn");
            e.printStackTrace();
        }
        return connection;
    }
}