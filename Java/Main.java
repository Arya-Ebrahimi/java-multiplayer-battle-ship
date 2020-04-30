package Java;

import java.io.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Main.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../LayoutFiles/main_menu.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException
    {
        launch(args);
    }

}