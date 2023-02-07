package com.example.demo1.database;

import java.sql.*;

public class AddClass {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/finalproject";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "11111";

    private static final String SET_USER = "INSERT INTO users(name, password) VALUES (?, ?)";

    // 'sss', u', 'a', 'zz' are the queries which are responsible for the correctness of the id when the table is manipulated

    public int insertValues(String username, String password){
        try {

            // adding the user

            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(SET_USER);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();


            // creating the batch statement

        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int addTask(String taskname, String task_desc, int task_level,int user_ids) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

        String query = "INSERT INTO tasks VALUES(null, ?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, taskname);

        preparedStatement.setString(2, task_desc);

        preparedStatement.setString(3, String.valueOf(task_level));

        preparedStatement.setString(4, String.valueOf(user_ids));

        System.out.println(preparedStatement.toString());

        preparedStatement.executeUpdate();

        return 0;
    }

    public void deleteListItem(int id) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

        String query = "DELETE FROM tasks where (`task_id` = ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, String.valueOf(id));

        System.out.println(preparedStatement);

        preparedStatement.executeUpdate();
    }

    public void execute(String query) throws SQLException {
            System.out.println(query);
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD); ;
            connection.createStatement().execute(query);
    }
}
