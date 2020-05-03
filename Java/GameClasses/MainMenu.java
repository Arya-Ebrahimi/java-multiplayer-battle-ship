package Java.GameClasses;

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

    @FXML private TextField getName;
    @FXML private TextField boardSize;
    @FXML private Button play;
    @FXML private Label label;


    private static Socket socket;
    private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;
    private static Scene scene;
    private String userName = null;
    private static String playerNumber;
    private static int numberPerRow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        play.setOnAction(this::click);
    }


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
            root = FXMLLoader.load(getClass().getResource("/LayoutFiles/set_game.fxml"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        scene = new Scene(root);
        Main.getPrimaryStage().setScene(scene);
    }

    public static String getPlayerNumber()
    {
        return playerNumber;
    }

    public static void closeConnection() {

        try {
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException ex) {
            System.out.println("Error closing the socket and streams");
        }

    }

    public static Socket getSocket()
    {
        return socket;
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

    public TextField getGetName()
    {
        return getName;
    }

    public void setGetName(TextField getName)
    {
        this.getName = getName;
    }

    public TextField getBoardSize()
    {
        return boardSize;
    }

    public void setBoardSize(TextField boardSize)
    {
        this.boardSize = boardSize;
    }

    public Button getPlay()
    {
        return play;
    }

    public void setPlay(Button play)
    {
        this.play = play;
    }

    public Label getLabel()
    {
        return label;
    }

    public void setLabel(Label label)
    {
        this.label = label;
    }

    public static void setSocket(Socket socket)
    {
        MainMenu.socket = socket;
    }

    public static Scene getScene()
    {
        return scene;
    }

    public static void setScene(Scene scene)
    {
        MainMenu.scene = scene;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public static void setPlayerNumber(String playerNumber)
    {
        MainMenu.playerNumber = playerNumber;
    }

    public static int getNumberPerRow()
    {
        return numberPerRow;
    }

    public static void setNumberPerRow(int numberPerRow)
    {
        MainMenu.numberPerRow = numberPerRow;
    }
}
