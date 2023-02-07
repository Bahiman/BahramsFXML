package com.example.demo1.database;

import com.example.demo1.Tasks;
import com.example.demo1.User;

import java.sql.*;
import java.util.Vector;

public class GetClass {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/finalproject";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "11111";

    private static final String query = "SELECT * FROM users where name = ? and password = ? ";

    public int login(String user, String password) throws SQLException {

        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, user);

        preparedStatement.setString(2, password);

        ResultSet f = preparedStatement.executeQuery();

        int counter = 0;

        while(f.next()){
            counter++;
        }

        return counter;
    }

    public int id(String user, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, user);

        preparedStatement.setString(2, password);

        ResultSet f = preparedStatement.executeQuery();

        int get_id = 0;

        while (f.next()){
            get_id = f.getInt("id");
        }

        return get_id;
    }

    public Vector<Tasks> getTasks(int id) throws SQLException {

        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

        String query = "SELECT * FROM tasks where user_id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setString(1, String.valueOf(id));

        Vector<Tasks> tasks = new Vector<>();

        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            Integer task_id = resultSet.getInt("task_id");
            String taskname= resultSet.getString("taskname");
            String task_desc= resultSet.getString("task_desc");
            Integer task_level= resultSet.getInt("task_level");
            Integer user_id= resultSet.getInt("user_id");

            Tasks tasks1 = new Tasks(
                    task_id,
                    taskname,
                    task_desc,
                    task_level,
                    user_id
            );

            tasks.add(tasks1);
        }

        return tasks;
    }

    public ResultSet getNameAndPassword(int id) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        String query = "SELECT * FROM users where id = ?";
        System.out.println("The id is " + id);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, String.valueOf(id));

        ResultSet resultSet = preparedStatement.executeQuery();

        return  resultSet;

    }

    public Tasks tasks(String name) throws SQLException {
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tasks where taskname = ?");
        preparedStatement.setString(1,name);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Tasks tasks = new Tasks(
                resultSet.getInt("task_id"),
                resultSet.getString("taskname"),
                resultSet.getString("task_desc"),
                resultSet.getInt("task_level"),
                resultSet.getInt("user_id")
        );

        return  tasks;
    }
}

