package db01;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        String host = "127.0.0.1"; //localhost
        String port = "3306";
        String user = "root";
        String pass = "12345";

        String url = String.format("jdbc:mysql://%s:%s", host, port);

        Connection connection = DriverManager.getConnection(url, user, pass);

        //mysql-connector-j

        String query = "SELECT COUNT(*) FROM soft_uni.employees";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next(); // next = give me the next row
        int employeeCount = resultSet.getInt(1); // get the 1st col
        System.out.println(employeeCount);

        System.out.println(resultSet);

        PreparedStatement manyCols = connection.prepareStatement("SELECT employee_id, first_name, salary FROM soft_uni.employees LIMIT 1");
        ResultSet manyColsRes = manyCols.executeQuery();
        manyColsRes.next();

        int res_id = manyColsRes.getInt(1);
        String first_name = manyColsRes.getString(2);
        double salary = manyColsRes.getDouble(3);

        System.out.println();

        PreparedStatement manyRows = connection.prepareStatement("SELECT employee_id, first_name, salary FROM soft_uni.employees LIMIT 10");
        ResultSet manyRowsRes = manyCols.executeQuery();

        while (manyRowsRes.next()){
            System.out.printf("%d %s %f %n",
                    manyRowsRes.getInt(1), // .getInt("employee_id");
                    manyRowsRes.getString(2), //.getString("first_name");
                    manyRowsRes.getFloat(3)); //.getDouble("salary");
        }


    }

}
