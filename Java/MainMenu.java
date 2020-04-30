package Java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable
{

    private static Socket socket;
    private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;

    public static Socket getSocket()
    {
        return socket;
    }

    public static void setSocket(Socket socket)
    {
        MainMenu.socket = socket;
    }

    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;

    public static ObjectInputStream getObjectInputStream()
    {
        return objectInputStream;
    }

    public static void setObjectInputStream(ObjectInputStream objectInputStream)
    {
        MainMenu.objectInputStream = objectInputStream;
    }

    public static ObjectOutputStream getObjectOutputStream()
    {
        return objectOutputStream;
    }

    public static void setObjectOutputStream(ObjectOutputStream objectOutputStream)
    {
        MainMenu.objectOutputStream = objectOutputStream;
    }

    public static DataOutputStream getDataOutputStream()
    {
        return dataOutputStream;
    }

    public static void setDataOutputStream(DataOutputStream dataOutputStream)
    {
        MainMenu.dataOutputStream = dataOutputStream;
    }

    public static DataInputStream getDataInputStream()
    {
        return dataInputStream;
    }

    public static void setDataInputStream(DataInputStream dataInputStream)
    {
        MainMenu.dataInputStream = dataInputStream;
    }

    public TextField getName;
    public TextField boardSize;
    public Button play;
    @FXML private Label label;
    static Scene scene;
    String userName = null;
    private static String playerNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        play.setOnAction(this::click);
    }

    static int numberPerRow;
    private void click(ActionEvent actionEvent)
    {
        userName = getName.getText();

        numberPerRow =  Integer.parseInt(boardSize.getText());
        SetMap.NUM_PER_ROW = numberPerRow;
        SetMap.NUM_OF_TILES = numberPerRow * numberPerRow;

        try
        {
            socket = new Socket("localhost", 1380);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            startGame();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void startGame()
    {
        try
        {
            dataOutputStream.writeUTF("username");
            dataOutputStream.writeUTF(userName);

            dataOutputStream.writeUTF("givePlayerNumber");
            playerNumber = dataInputStream.readUTF();

            dataOutputStream.writeUTF("mapSize");
            dataOutputStream.writeInt(numberPerRow);

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        Parent root = null;
        try
        {
            root = FXMLLoader.load(getClass().getResource("../LayoutFiles/set_game.fxml"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        scene = new Scene(root);
        Main.primaryStage.setScene(scene);
    }

    public static String getPlayerNumber()
    {
        return playerNumber;
    }
}
