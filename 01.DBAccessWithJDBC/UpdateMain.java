package db01;

import java.sql.*;

public class UpdateMain {
    public static void main(String[] args) throws SQLException {
        String host = "127.0.0.1"; //localhost
        String port = "3306";
        String user = "root";
        String pass = "12345";

        String url = String.format("jdbc:mysql://%s:%s", host, port);

        Connection connection = DriverManager.getConnection(url, user, pass);

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE employees SET salary = salary * 2 WHERE employee_id = 1;");

        int resultSet = preparedStatement.executeUpdate();

        System.out.println(resultSet); //resultSet=1, which mens one row affected
    }
}
