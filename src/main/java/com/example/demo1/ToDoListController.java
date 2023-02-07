package com.example.demo1;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;

import com.example.demo1.database.GetClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ToDoListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button all;

    @FXML
    private Button complete;

    @FXML
    private Button inProgress;

    @FXML
    private Button openProfile;

    private User user;

    private Vector<Tasks> tasks;

    @FXML
    private ListView<Tasks> list;

    private ObservableList<Tasks> tasks_l;

    @FXML
    private Button addTask;

    public void setUser(User user) {
        assert user != null : "Da yuser is nal";
        
        this.user = user;
    }

    public void setTasks() throws SQLException {
        GetClass g = new GetClass();

        tasks = g.getTasks(user.id());
    }

    @FXML
    void initialize() {
        assert all != null : "fx:id=\"all\" was not injected: check your FXML file 'Todolist.fxml'.";
        assert complete != null : "fx:id=\"complete\" was not injected: check your FXML file 'Todolist.fxml'.";
        assert inProgress != null : "fx:id=\"inProgress\" was not injected: check your FXML file 'Todolist.fxml'.";
        assert openProfile != null : "fx:id=\"openProfile\" was not injected: check your FXML file 'Todolist.fxml'.";

        openProfile.setOnAction(actionEvent -> {
            System.out.println("Opening profile from todolistcontroller");

            FXMLLoader fxmlLoader = new FXMLLoader(HelloController.class.getResource("Profile.fxml"));

            Stage stage = new Stage();

            Scene scene = null;

            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ProfileController profileController = fxmlLoader.getController();


            profileController.setUser(user);

            stage.setTitle("Profile");

            stage.setScene(scene);

            openProfile.getScene().getWindow().hide();

            stage.show();

            for(Tasks t : tasks){
                System.out.println(t.toString());
            }
        });

        addTask.setOnAction(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloController.class.getResource("NewTask.fxml"));

            Stage stage = new Stage();

            Scene scene = null;

            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            NewTaskController taskController = fxmlLoader.getController();

            taskController.setNewTaskController(user.id(),user);

            stage.setTitle("AddTask");

            stage.setScene(scene);

            addTask.getScene().getWindow().hide();

            stage.show();

        });
    }

    public void showUserTasks(){
        tasks_l = FXCollections.observableArrayList();

        for(Tasks k : tasks){
            tasks_l.add(k);
        }

        list.setItems(tasks_l);

        list.setCellFactory(Listitem -> new Listitem(user));


    }

}
