/**
 * Sample Skeleton for 'Login.fxml' Controller Class
 */

package com.example.demo1;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.demo1.database.GetClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Sample Skeleton for 'Login.fxml' Controller Class
 */


import javafx.stage.Stage;

public class MyLoginController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="logbtn"
    private Button logbtn; // Value injected by FXMLLoader

    @FXML // fx:id="passfield"
    private TextField passfield; // Value injected by FXMLLoader

    @FXML // fx:id="usfield"
    private TextField usfield; // Value injected by FXMLLoader


    @FXML // fx:id="notRegist"
    private Button notRegist; // Value injected by FXMLLoader

    public String user;

    public String password;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert logbtn != null : "fx:id=\"logbtn\" was not injected: check your FXML file 'Login.fxml'.";
        assert passfield != null : "fx:id=\"passfield\" was not injected: check your FXML file 'Login.fxml'.";
        assert usfield != null : "fx:id=\"usfield\" was not injected: check your FXML file 'Login.fxml'.";

        logbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // loginning

                GetClass g = new GetClass();

                String user = usfield.getText();

                String password = passfield.getText();

                int f;
                try {
                    f = g.login(user, password);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                if (f > 0){
                    System.out.println("Successfully registered");

                    logbtn.getScene().getWindow().hide();

                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Profile.fxml"));
                    
                    Stage stage = new Stage();

                    Scene scene = null;
                    try {
                        scene = new Scene(fxmlLoader.load());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    int get_id = 0;

                    try {
                         get_id = g.id(user,password);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    User users = new User(
                            get_id,
                            user,
                            password
                    );


                    ProfileController profileController = fxmlLoader.getController();

                    profileController.setUser(users);

                    stage.setTitle("Profile");

                    stage.setScene(scene);

                    stage.show();

                    System.out.println("BUTTON PRESED");
                }
            }
        });

        notRegist.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // opening the registration panel if not registered

                FXMLLoader fxmlLoader = new FXMLLoader(MyLoginController.class.getResource("Register.fxml"));

                Stage stage = new Stage();

                Scene scene = null;

                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                stage.setTitle("Login!");

                stage.setScene(scene);

                stage.show();

            }
        });
    }
}

