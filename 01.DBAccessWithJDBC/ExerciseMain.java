package db01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class ExerciseMain {
    private static final Connection connection = DBConnection.getConnection();
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws SQLException, IOException {

        getVillianNamesAndCount();
        getMinionNames();
        addMinion();
        connection.close();
    }

    private static void getVillianNamesAndCount() throws SQLException {
        String query = "SELECT v.name, COUNT(mv.minion_id) AS count " +
                "FROM villains v " +
                "JOIN minions_villains mv on v.id = mv.villain_id " +
                "GROUP BY v.name " +
                "HAVING count > 15 " +
                "ORDER BY count DESC;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            System.out.printf("%s %d%n", resultSet.getString("name"), resultSet.getInt("count"));
        }
    }

    private static void getMinionNames() throws SQLException, IOException {
        int villianId = Integer.parseInt(reader.readLine());

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id FROM villians WHERE id = ?");
        preparedStatement.setInt(1, villianId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()){
            System.out.println("No villians with ID: " + villianId + " exist in the db.");
            return;
        }
        System.out.printf("Villian: %s%n", resultSet.getString("id"));

        preparedStatement = connection.prepareStatement(
                "SELECT m.name, m.age FROM minions m " +
                "JOIN minions_villians mv on mv.minion_id = m.id" +
                "WHERE mv.villian_id = ?");

        preparedStatement.setInt(1, villianId);

        resultSet = preparedStatement.executeQuery();

        int count = 1;
        while (resultSet.next()){
            System.out.printf("%d. %s %d%n", count++,
                    resultSet.getString("name"),
                    resultSet.getInt("age"));
        }
    }

    private static void addMinion() throws SQLException, IOException {
        String[] minionTokens = reader.readLine().split(" ");
        String[] villianTokens = reader.readLine().split(" ");

        String minionName = minionTokens[1];
        int minionAge = Integer.parseInt(minionTokens[2]);
        String minionTown = minionTokens[3];
        int townId = townExists(minionTown);

        if (townId == 0){
            townId = createTown(minionTown);
        }
        int minionId = createMinion(minionName, minionAge, townId);
        String villianName = villianTokens[1];
        int villianID = villianExists(villianName);
        if (villianID == 0) {
            villianID = createVillian(villianName);
        }
        assignMinionToVillian(villianID, minionId);

    }
    private static void assignMinionToVillian(int villianId, int minionId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO minions_villians VALUE (?, ?)");
        preparedStatement.setInt(1, minionId);
        preparedStatement.setInt(2, villianId);

    }

    private static int villianExists(String villianName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id FROM villians WHERE name = ?");
        preparedStatement.setString(1, villianName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt("id");
        }
        return 0;
    }

    private static int createVillian(String villianName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO villians (name, evilness_factor) VALUE (?, 'evil')");
        preparedStatement.setString(1, villianName);
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement(
                "SELECT id from villians WHERE name = ?");
        preparedStatement.setString(1, villianName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        return resultSet.getInt("id");
    }

    private static int createMinion(String minionName, int minionAge, int townId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO minions(name, age, town_id) VALUE (?, ?, ?)");
        preparedStatement.setString(1, minionName);
        preparedStatement.setInt(2, minionAge);
        preparedStatement.setInt(3, townId);
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement(
                "SELECT id FROM minions WHERE name = ?");
        preparedStatement.setString(1, minionName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("id");
    }

    private static int createTown(String townName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO towns(name) VALUE (?)");
        preparedStatement.setString(1, townName);
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement(
                "SELECT id FROM towns WHERE name = ?");
        preparedStatement.setString(1, townName);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        return resultSet.getInt("id");
    }

    private static int townExists(String townName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM towns WHERE name = ?");
        preparedStatement.setString(1, townName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt("id");
        }
        return 0;
    }
}
