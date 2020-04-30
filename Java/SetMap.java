package Java;

import Java.Units.Soldier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SetMap implements Initializable
{

    @FXML private Text soldierText;
    @FXML private Text horsemanText;
    @FXML private Text castleText;
    @FXML private Text commandCenterText;
    @FXML private Button soldier;
    @FXML private Button horseman;
    @FXML private Button castle;
    @FXML private Button commandCenter;
    @FXML private Text text;
    @FXML private Button button;
    @FXML private Pane root;

    String playerNumber = null;
    String troopsTiles = "";

    int x;
    private boolean isVertical = false;
    private boolean isStartButtonClicked = false;
    private int soldierLeft = 5;
    private int horsemanLeft = 3;
    private int castleLeft = 2;
    private int commandCenterLeft = 1;
    public static int NUM_PER_ROW ;
    public static int NUM_OF_TILES;
    static List<Tile> tiles = new ArrayList<>();

    public Text getCastleText()
    {
        return castleText;
    }

    public static int getNumPerRow()
    {
        return NUM_PER_ROW;
    }

    public static int getNumOfTiles()
    {
        return NUM_OF_TILES;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        makeGameBoard();
        handleTexts();
        button.setOnAction(this::handleButtonClick);
        soldier.setOnAction(this::soldierAction);
        horseman.setOnAction(this::horsemanAction);
        castle.setOnAction(this::castleAction);
        commandCenter.setOnAction(this::commandCenterAction);
    }

    private void handleTexts()
    {
        text.setText(MainMenu.getPlayerNumber());
        soldierText.setText( "soldiers left : " + soldierLeft);
        horsemanText.setText("horsemen left : " + horsemanLeft) ;
        castleText.setText("castles left : "  + castleLeft) ;
        commandCenterText.setText("command centers left : " + commandCenterLeft);
    }

    private void handleButtonClick(javafx.event.ActionEvent actionEvent)
    {

       if(!isStartButtonClicked && soldierLeft == 0 && horsemanLeft == 0 && castleLeft == 0 && commandCenterLeft == 0)
       {
           try
           {
               MainMenu.getDataOutputStream().writeUTF("settingMap");
           } catch (IOException e)
           {
               e.printStackTrace();
           }
           for(int i = 0; i < NUM_OF_TILES; i++)
           {
               if(tiles.get(i).isChecked())
               {
                   try
                   {
                       MainMenu.getDataOutputStream().writeInt(i);
                   } catch (IOException e)
                   {
                       e.printStackTrace();
                   }
               }
           }

           try
           {
               MainMenu.getDataOutputStream().writeUTF(troopsTiles);
           } catch (IOException e)
           {
               e.printStackTrace();
           }
           isStartButtonClicked = true;

           try
           {
               root = FXMLLoader.load(getClass().getResource("../LayoutFiles/start_game.fxml"));
           } catch (IOException e)
           {
               e.printStackTrace();
           }

           Main.primaryStage.setScene(new Scene(root));

           try
           {
               MainMenu.getDataOutputStream().writeUTF("printMap");
           } catch (IOException e)
           {
               e.printStackTrace();
           }
       }
        //button.setText("clicked");
    }

    private void commandCenterAction(ActionEvent actionEvent)
    {
        x = NUM_PER_ROW;


        for(Tile tile : tiles)
        {
            int tileNumber = tile.getTileNumber();

            tile.setOnMouseClicked(event ->
            {
                if((tile.getTileNumber() + 1) % NUM_PER_ROW != 0 && tile.getTileNumber() < NUM_OF_TILES - NUM_PER_ROW && tile.getTileNumber() % NUM_PER_ROW != 0 && tile.getTileNumber() > NUM_PER_ROW - 1)
                {
                    if(!(tile.isChecked() || tiles.get(tileNumber + x).isChecked() ||
                            tiles.get(tileNumber + 1).isChecked() || tiles.get(tileNumber + x + 1).isChecked() ||
                            tiles.get(tileNumber - 1).isChecked() || tiles.get(tileNumber - x - 1).isChecked() ||
                            tiles.get(tileNumber - x).isChecked() || tiles.get(tileNumber - x + 1).isChecked() ||
                            tiles.get(tileNumber + x - 1).isChecked()))
                    {

                        //for preventing duplicate code
                        if(commandCenterLeft > 0)
                        {
                            setFillsMain(tile, tileNumber);

                            //others
                            tiles.get(tileNumber + x - 1).getBorderOfTile().setFill(Paint.valueOf("blue"));
                            tiles.get(tileNumber - 1).getBorderOfTile().setFill(Paint.valueOf("blue"));
                            tiles.get(tileNumber - x + 1).getBorderOfTile().setFill(Paint.valueOf("blue"));
                            tiles.get(tileNumber - x - 1).getBorderOfTile().setFill(Paint.valueOf("blue"));
                            tiles.get(tileNumber - x).getBorderOfTile().setFill(Paint.valueOf("blue"));


                            tile.setChecked(true);
                            tiles.get(tileNumber + 1).setChecked(true);
                            tiles.get(tileNumber + x).setChecked(true);
                            tiles.get(tileNumber + x + 1).setChecked(true);
                            tiles.get(tileNumber - 1).setChecked(true);
                            tiles.get(tileNumber + x - 1).setChecked(true);
                            tiles.get(tileNumber - x - 1).setChecked(true);
                            tiles.get(tileNumber - x + 1).setChecked(true);
                            tiles.get(tileNumber - x).setChecked(true);

                            troopsTiles += tileNumber + "," + (tileNumber + 1) + "," + (tileNumber + x) + "," + (tileNumber + x + 1) + "," +
                                    (tileNumber - 1) + "," + (tileNumber + x - 1) + "," + (tileNumber - x - 1) + "," + (tileNumber - x + 1) +
                                    "," + (tileNumber - x) + "_";

                            System.out.println(troopsTiles);

                            commandCenterLeft--;

                            handleTexts();

                        }
                    }
                }
            });
            tile.setOnMouseEntered(event ->
            {
                if((tile.getTileNumber() + 1) % NUM_PER_ROW != 0 && tile.getTileNumber() < NUM_OF_TILES - NUM_PER_ROW && tile.getTileNumber() % NUM_PER_ROW != 0 && tile.getTileNumber() > NUM_PER_ROW - 1)
                {
                    if(!(tile.isChecked() || tiles.get(tileNumber + x).isChecked() ||
                            tiles.get(tileNumber + 1).isChecked() || tiles.get(tileNumber + x + 1).isChecked() ||
                            tiles.get(tileNumber - 1).isChecked() || tiles.get(tileNumber - x - 1).isChecked() ||
                            tiles.get(tileNumber - x).isChecked() || tiles.get(tileNumber - x + 1).isChecked() ||
                            tiles.get(tileNumber + x - 1).isChecked()))
                    {
                        setFillsMouseEntered(tile, tileNumber);
                        tiles.get(tileNumber + x - 1).getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                        tiles.get(tileNumber - 1).getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                        tiles.get(tileNumber - x + 1).getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                        tiles.get(tileNumber - x - 1).getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                        tiles.get(tileNumber - x).getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                    }
                }

            });
            tile.setOnMouseExited(event ->
            {
                if((tile.getTileNumber() + 1) % NUM_PER_ROW != 0 && tile.getTileNumber() < NUM_OF_TILES - NUM_PER_ROW && tile.getTileNumber() % NUM_PER_ROW != 0 && tile.getTileNumber() > NUM_PER_ROW - 1)
                {
                    if(!(tile.isChecked() || tiles.get(tileNumber + x).isChecked() ||
                            tiles.get(tileNumber + 1).isChecked() || tiles.get(tileNumber + x + 1).isChecked() ||
                            tiles.get(tileNumber - 1).isChecked() || tiles.get(tileNumber - x - 1).isChecked() ||
                            tiles.get(tileNumber - x).isChecked() || tiles.get(tileNumber - x + 1).isChecked() ||
                            tiles.get(tileNumber + x - 1).isChecked()))
                    {
                        //for preventing duplicate code
                        tile.getBorderOfTile().setFill(null);
                        tiles.get(tileNumber + 1).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber + x).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber + x + 1).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber + x - 1).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber - 1).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber - x + 1).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber - x - 1).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber - x).getBorderOfTile().setFill(null);
                    }
                }
            });

        }

    }

    private void setFillsMouseEntered(Tile tile, int tileNumber)
    {
        tile.getBorderOfTile().setFill(Paint.valueOf("lightblue"));
        tiles.get(tileNumber + 1).getBorderOfTile().setFill(Paint.valueOf("lightblue"));
        tiles.get(tileNumber + x).getBorderOfTile().setFill(Paint.valueOf("lightblue"));
        tiles.get(tileNumber + x + 1).getBorderOfTile().setFill(Paint.valueOf("lightblue"));
    }

    private void castleAction(ActionEvent actionEvent)
    {
        x = NUM_PER_ROW;
        for(Tile tile : tiles)
        {
            int tileNumber = tile.getTileNumber();

            tile.setOnMouseClicked(event ->
            {
                if((tile.getTileNumber() + 1) % NUM_PER_ROW != 0 && tile.getTileNumber() < NUM_OF_TILES - NUM_PER_ROW)
                {
                    if(!(tile.isChecked() || tiles.get(tileNumber + x).isChecked() ||
                            tiles.get(tileNumber + 1).isChecked() || tiles.get(tileNumber + x + 1).isChecked()))
                    {
                        if(castleLeft > 0)
                        {
                            setFillsMain(tile, tileNumber);

                            tile.setChecked(true);
                            tiles.get(tileNumber + 1).setChecked(true);
                            tiles.get(tileNumber + x).setChecked(true);
                            tiles.get(tileNumber + x + 1).setChecked(true);

                            troopsTiles += tileNumber + "," + (tileNumber + 1) + "," + (tileNumber + x) + "," + (tileNumber + x + 1) + "_";

                            castleLeft--;
                            handleTexts();
                        }
                    }
                }
            });
            tile.setOnMouseEntered(event ->
            {
                if((tile.getTileNumber() + 1) % NUM_PER_ROW != 0 && tile.getTileNumber() < NUM_OF_TILES - NUM_PER_ROW)
                {
                    if(!(tile.isChecked() || tiles.get(tileNumber + x).isChecked() ||
                            tiles.get(tileNumber + 1).isChecked() || tiles.get(tileNumber + x + 1).isChecked()))
                    {
                        setFillsMouseEntered(tile, tileNumber);
                    }
                }

            });
            tile.setOnMouseExited(event ->
            {
                if((tile.getTileNumber() + 1) % NUM_PER_ROW != 0 && tile.getTileNumber() < NUM_OF_TILES - NUM_PER_ROW)
                {
                    if(!(tile.isChecked() || tiles.get(tileNumber + x).isChecked() ||
                            tiles.get(tileNumber + 1).isChecked() || tiles.get(tileNumber + x + 1).isChecked()))
                    {
                        tile.getBorderOfTile().setFill(null);
                        tiles.get(tileNumber + 1).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber + x).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber + x + 1).getBorderOfTile().setFill(null);
                    }
                }
            });
        }

    }

    private void setFillsMain(Tile tile, int tileNumber)
    {
        tile.getBorderOfTile().setFill(Paint.valueOf("blue"));
        tiles.get(tileNumber + 1).getBorderOfTile().setFill(Paint.valueOf("blue"));
        tiles.get(tileNumber + x).getBorderOfTile().setFill(Paint.valueOf("blue"));
        tiles.get(tileNumber + x + 1).getBorderOfTile().setFill(Paint.valueOf("blue"));
    }

    private void horsemanAction(ActionEvent actionEvent)
    {
        x = 1;
        for (Tile tile : tiles)
        {
            int tileNumber = tile.getTileNumber();

            tile.setOnScroll(event ->
            {
                if(!isVertical)
                {
                    x = NUM_PER_ROW;
                    isVertical = true;
                    if(!(tile.isChecked() || tiles.get(tileNumber + x).isChecked() || tiles.get(tileNumber + 1).isChecked()))
                    {
                        tiles.get(tileNumber + 1).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber + x).getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                    }
                }
                else
                {
                    x = 1;
                    isVertical = false;
                    if(!(tile.isChecked() || tiles.get(tileNumber + 1).isChecked() || tiles.get(tileNumber + NUM_PER_ROW).isChecked()))
                    {
                        tiles.get(tileNumber + NUM_PER_ROW).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber + x).getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                    }
                }
            });
            tile.setOnMouseClicked(event ->
            {
                if (isVertical && (tile.getTileNumber() < NUM_OF_TILES - NUM_PER_ROW))
                {
                    HorsemanClickHandle(tile, tileNumber);
                }
                else if (!isVertical && ( (tile.getTileNumber() + 1 ) % NUM_PER_ROW != 0))
                {
                    HorsemanClickHandle(tile, tileNumber);

                }

            });
            tile.setOnMouseEntered(event ->
            {
                if (isVertical && (tile.getTileNumber() < NUM_OF_TILES - NUM_PER_ROW))
                {
                    if(! (tile.isChecked() || tiles.get(tileNumber + x).isChecked()))
                    {
                        tile.getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                        tiles.get(tileNumber + x).getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                    }
                }
                else if (!isVertical && ( (tile.getTileNumber() + 1 ) % NUM_PER_ROW != 0))
                {
                    if(! (tile.isChecked() || tiles.get(tileNumber + x).isChecked()))
                    {
                        tile.getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                        tiles.get(tileNumber + x).getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                    }
                }
            });
            tile.setOnMouseExited(event ->
            {
                if (isVertical && (tile.getTileNumber() < NUM_OF_TILES - NUM_PER_ROW))
                {
                    if(! (tile.isChecked() || tiles.get(tileNumber + x).isChecked()))
                    {
                        tile.getBorderOfTile().setFill(null);
                        tiles.get(tileNumber + x).getBorderOfTile().setFill(null);
                    }
                }
                else if (!isVertical && ( (tile.getTileNumber() + 1 ) % NUM_PER_ROW != 0))
                {
                    if(! (tile.isChecked() || tiles.get(tileNumber + x).isChecked()))
                    {
                        tile.getBorderOfTile().setFill(null);
                        tiles.get(tileNumber + x).getBorderOfTile().setFill(null);
                    }
                }
            });
        }

    }

    private void HorsemanClickHandle(Tile tile, int tileNumber)
    {
        if(! (tile.isChecked() || tiles.get(tileNumber + x).isChecked()))
        {
            if(horsemanLeft > 0)
            {
                tile.getBorderOfTile().setFill(Paint.valueOf("blue"));
                tiles.get(tileNumber + x).getBorderOfTile().setFill(Paint.valueOf("blue"));
                tile.setChecked(true);
                tiles.get(tileNumber + x).setChecked(true);

                troopsTiles += tileNumber + "," + (tileNumber + x) + "_";

                horsemanLeft--;
                handleTexts();
            }

        }
    }

    private void soldierAction(ActionEvent actionEvent)
    {
        for (Tile tile : tiles)
        {
            tile.setOnMouseClicked(event ->
            {
                if(!tile.isChecked())
                {
                    if(soldierLeft > 0)
                    {

                        tile.getBorderOfTile().setFill(Paint.valueOf("blue"));
                        tile.setChecked(true);

                        troopsTiles += tile.getTileNumber() + "_";
                        soldierLeft--;
                        handleTexts();
                    }
                }
            });
            tile.setOnMouseEntered(event ->
            {
                if (!tile.isChecked())
                {
                    tile.getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                }
            });
            tile.setOnMouseExited(event ->
            {
                if (!tile.isChecked())
                {
                    tile.getBorderOfTile().setFill(null);
                }
            });
        }
    }


    private void makeGameBoard()
    {
        for (int i = 0; i < NUM_OF_TILES; i++)
        {
            tiles.add(new Tile(i));
        }

        for (int i = 0; i < tiles.size(); i++)
        {
            Tile tile = tiles.get(i);
            tile.setTranslateX(Tile.getTileSides() * (i % NUM_PER_ROW));
            tile.setTranslateY(Tile.getTileSides() * (int)(i / NUM_PER_ROW));
            root.getChildren().add(tile);
        }
    }


}
