package com.example.demo1; /**
 * Sample Skeleton for 'Profile.fxml' Controller Class
 */

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProfileController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="passprofile"
    private Text passprofile; // Value injected by FXMLLoader

    @FXML // fx:id="usprofile"
    private Text usprofile; // Value injected by FXMLLoader

    @FXML // fx:id="toDoOpenB"
    private Button toDoOpenB; // Value injected by FXMLLoader

    private User user;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        toDoOpenB.setOnAction(actionEvent -> {

            toDoOpenB.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Todolist.fxml"));

            Stage stage = new Stage();

            Scene scene = null;

            try {
                scene  = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ToDoListController toDoListController = fxmlLoader.getController();

            toDoListController.setUser(user);

            try {
                toDoListController.setTasks();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            toDoListController.showUserTasks();

            stage.setTitle("Todolist");

            stage.setScene(scene);

            stage.show();
        });
    }

    public ResourceBundle getResources() {
        return resources;
    }

    public void setResources(ResourceBundle resources) {
        this.resources = resources;
    }

    public URL getLocation() {
        return location;
    }

    public void setLocation(URL location) {
        this.location = location;
    }

    public void setUser(User user) {
        this.user = user;

        assert user != null : "The user is null";

        usprofile.setText(user.username());

        passprofile.setText(user.password());
    }
}
