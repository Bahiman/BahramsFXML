package com.example.demo1;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.demo1.database.AddClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditWindow {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField descField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField levelField;

    @FXML
    private Button submitBTN;

    private User user;

    private int task_id;

    @FXML
    void initialize() {
        assert descField != null : "fx:id=\"descField\" was not injected: check your FXML file 'EditWindow.fxml'.";
        assert nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file 'EditWindow.fxml'.";
        assert levelField != null : "fx:id=\"passField\" was not injected: check your FXML file 'EditWindow.fxml'.";
        assert submitBTN != null : "fx:id=\"submitBTN\" was not injected: check your FXML file 'EditWindow.fxml'.";

        submitBTN.setOnAction(actionEvent -> {

            int counter = 0;

            if(nameField.getText() != "") counter++;

            if(descField.getText() != "") counter++;

            if(levelField.getText() != "") counter++;

            int realCounter = 0;
            String query = "UPDATE tasks SET ";

            if(!nameField.getText().equals("")){
                query = query + "taskname = '" + nameField.getText() + "'";
                realCounter++;
                if(realCounter < counter){
                    query = query + ",";
                }
            }

            if(!descField.getText().equals("")){
                query = query + "task_desc = '" + descField.getText() + "'";
                realCounter++;
                if(realCounter < counter){
                    query = query + ",";
                }

            }

            if(!levelField.getText().equals("") ){
                query = query + "task_level = " + "'" + levelField.getText() + "'";
                realCounter++;
                if(realCounter < counter){
                    query = query + ",";
                }
            }

            query = query + " where task_id = " + task_id + ";";

            AddClass addClass = new AddClass();

            try {
                addClass.execute(query);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            FXMLLoader loader = new FXMLLoader(HelloController.class.getResource("Todolist.fxml"));

            Stage stage = new Stage();

            Scene scene = null;

            try {
                scene = new Scene(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ToDoListController toDoListController = loader.getController();

            toDoListController.setUser(user);

            try {
                toDoListController.setTasks();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            toDoListController.showUserTasks();

            stage.setTitle("ToDoList");

            stage.setScene(scene);

            submitBTN.getScene().getWindow().hide();

            stage.show();
        });

    }

    public void setEditWindow(User user, Tasks tasks) {

        assert  user != null : "Da user is null";

        task_id = tasks.task_id();

        this.user = user;
    }


}
