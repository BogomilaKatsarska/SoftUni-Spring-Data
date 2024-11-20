package db01;

import java.sql.*;

public class PreparedMain {
    public static void main(String[] args) throws SQLException {
        String host = "127.0.0.1"; //localhost
        String port = "3306";
        String user = "root";
        String pass = "12345";

        String url = String.format("jdbc:mysql://%s:%s", host, port);

        Connection connection = DriverManager.getConnection(url, user, pass);

        String query = "SELECT * FROM soft_uni.employees WHERE first_name LIKE ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "%gu%");

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            System.out.printf("%d -> %s",
                    resultSet.getInt("employee_id"),
                    resultSet.getString("first_name"));
        }
    }
}
