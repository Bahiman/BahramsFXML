package com.example.demo1;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.demo1.database.AddClass;
import com.example.demo1.database.GetClass;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.xml.transform.Result;

public class Listitem extends ListCell<Tasks>{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button delete;

    @FXML
    private Label desc;

    @FXML
    private CheckBox doneCheckbox;

    @FXML
    private Button edit;

    @FXML
    private Label name;
    @FXML
    private AnchorPane itemAnchor;

    private FXMLLoader loader;

    private Tasks tasks;

    private int id;

    private int user_id;

    private User user;

    public Listitem(User user) {
        this.user = user;
    }

    @FXML
    void initialize() throws SQLException  {
        assert delete != null : "fx:id=\"delete\" was not injected: check your FXML file 'listitem.fxml'.";
        assert desc != null : "fx:id=\"desc\" was not injected: check your FXML file 'listitem.fxml'.";
        assert doneCheckbox != null : "fx:id=\"doneCheckbox\" was not injected: check your FXML file 'listitem.fxml'.";
        assert edit != null : "fx:id=\"edit\" was not injected: check your FXML file 'listitem.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'listitem.fxml'.";

        delete.setOnAction(actionEvent -> {

            AddClass addClass = new AddClass();

            try {
                addClass.deleteListItem(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            GetClass getClass = new GetClass();

            ResultSet resultSet = null;

            String username = null;

            String password = null;

            try {
                resultSet = getClass.getNameAndPassword(user_id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            System.out.println(resultSet);

             while(true){
                 try {
                     if (!resultSet.next()) break;
                 } catch (SQLException e) {
                     throw new RuntimeException(e);
                 }
                 try {
                     username = resultSet.getString("name");

                     password = resultSet.getString("password");
                 } catch (SQLException e) {
                     throw new RuntimeException(e);
                 }
             }

            User user1 = new User(
                    user_id,
                    username,
                    password
            );


            FXMLLoader loader = new FXMLLoader(HelloController.class.getResource("Todolist.fxml"));

            Stage stage = new Stage();

            Scene scene = null;

            try {
                scene = new Scene(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ToDoListController toDoListController = loader.getController();

            toDoListController.setUser(user1);

            try {
                toDoListController.setTasks();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            toDoListController.showUserTasks();

            stage.setTitle("ToDoList");

            stage.setScene(scene);

            delete.getScene().getWindow().hide();

            stage.show();
        });

        doneCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>(){

            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                AddClass addClass = new AddClass();

                FXMLLoader loader1 = new FXMLLoader(HelloApplication.class.getResource("Todolist.fxml"));

                Scene scene = null;

                Stage stage = new Stage();

                try {
                    scene = new Scene(loader1.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                ToDoListController toDoListController = loader1.getController();

                GetClass getClass = new GetClass();

                ResultSet resultSet = null;

                String username = null;

                String password = null;

                try {
                    resultSet = getClass.getNameAndPassword(id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                try {
                    username = resultSet.getString("name");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                try {
                    password = resultSet.getString("password");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


                User user1 = new User(
                        user_id,
                        username,
                        password
                );

                toDoListController.setUser(user1);

                stage.setTitle("ToDoList");

                stage.setScene(scene);

                delete.getScene().getWindow().hide();

                try {
                    addClass.deleteListItem(id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                stage.show();
            }
        } );

        edit.setOnAction(actionEvent -> {
            FXMLLoader loader1 = new FXMLLoader(HelloApplication.class.getResource("EditWindow.fxml"));

            Scene scene = null;

            Stage stage = new Stage();

            try {
                scene = new Scene(loader1.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            EditWindow editWindow = loader1.getController();

            GetClass getClass = new GetClass();

            String namez = name.getText();

            Tasks tasks1 = null;

            try {
                tasks1  = getClass.tasks(namez);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            editWindow.setEditWindow(user,tasks1);

            stage.setScene(scene);

            stage.setTitle("Edit window");

            edit.getScene().getWindow().hide();

            stage.show();

            System.out.println(scene);
        });
    }

    protected void updateItem(Tasks task, boolean empty){
         super.updateItem(task,empty);

         if(empty || task == null){
             setGraphic(null);
         } else{
             loader = new FXMLLoader(HelloApplication.class.getResource("listitem.fxml"));

             loader.setController(this);

             try {
                 loader.load();
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }

             name.setText(task.taskname());
             desc.setText(task.task_desc());

             setGraphic(itemAnchor);

             id = task.task_id();

             user_id = task.user_id();

         }
    }

    public void setUser(User user){
        this.user = user;
    }
}
