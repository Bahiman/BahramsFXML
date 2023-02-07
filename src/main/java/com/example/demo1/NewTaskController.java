package com.example.demo1;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

import com.example.demo1.database.AddClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewTaskController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button submit;

    @FXML
    private TextField task_level;

    @FXML
    private TextField task_name;

    @FXML
    private TextField taskdesc;

    private User user;

    private int clientId;

    public void setNewTaskController(int clientId, User user) {
        this.clientId = clientId;

        this.user = user;
    }

    @FXML
    void initialize() {
        assert submit != null : "fx:id=\"submit\" was not injected: check your FXML file 'NewTask.fxml'.";
        assert task_level != null : "fx:id=\"task_level\" was not injected: check your FXML file 'NewTask.fxml'.";
        assert task_name != null : "fx:id=\"task_name\" was not injected: check your FXML file 'NewTask.fxml'.";
        assert taskdesc != null : "fx:id=\"taskdesc\" was not injected: check your FXML file 'NewTask.fxml'.";

        submit.setOnAction(actionEvent -> {
            String taskName = task_name.getText();

            String taskDesc = taskdesc.getText();

            Integer taskLevel = Integer.valueOf(task_level.getText());

            AddClass addClass = new AddClass();

            try {
                addClass.addTask(taskName,taskDesc,taskLevel,clientId);
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

            submit.getScene().getWindow().hide();

            stage.show();
        });
    }

}
