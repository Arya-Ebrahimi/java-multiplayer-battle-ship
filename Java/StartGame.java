package Java;

import Java.Units.Castle;
import Java.Units.CommandCenter;
import Java.Units.Horseman;
import Java.Units.Soldier;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class StartGame implements Initializable
{

    public MenuItem soldier1;
    public MenuItem soldier2;
    public MenuItem soldier3;
    public MenuItem soldier4;
    public MenuItem soldier5;
    public MenuItem horseman1;
    public MenuItem horseman2;
    public MenuItem horseman3;
    public Text roundsLeft;
    public MenuItem castle1;
    public MenuItem castle2;
    public MenuItem commandcenter;

    private String troopsTiles;
    private String[] splitted;

    private boolean isFirstRound = true;

    @FXML
    private Button soldier;
    @FXML
    private Button horseman;
    @FXML
    private Button castle;
    @FXML
    private Button command;

    @FXML
    private Text alarmText;
    @FXML
    private Pane targetMap;
    @FXML
    private Pane playerMap;

    private List<MediumTiles> tiles = new ArrayList<>();
    private List<SmallTiles> myTiles = new ArrayList<>();

    private ArrayList<Soldier> soldiers = new ArrayList<>();
    private ArrayList<Horseman> horsemen = new ArrayList<>();
    private ArrayList<Castle> castles = new ArrayList<>();
    private CommandCenter commandCenter;

    private boolean isVertical = false;

    private int count = 0;
    private int hitCount = 0;

    private Thread thread;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        makeGameBoardTargetMap();
        makeGameBoardPlayerMap();

        thread = new ClientHandler(alarmText, myTiles, tiles);
        getPlayerMapFromServer();

        addSoldiers();

        ThreadSave.soldiers = soldiers;

        addHorseman();

        ThreadSave.horsemen = horsemen;

        addCommandCenter();

        ThreadSave.commandCenter = commandCenter;

        addCastles();

        ThreadSave.castles = castles;

        thread.start();

        handleActionListeners();

    }

    private void addCastles()
    {
        char[] chars;
        int countt;

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < splitted.length; j++)
            {
                countt = 0;
                chars = splitted[j].toCharArray();

                for (int k = 0; k < splitted[j].length(); k++)
                {
                    if (chars[k] == ',')
                    {
                        countt++;
                    }
                }

                if (countt == 3)
                {
                    castles.add(new Castle(splitted[j], myTiles));

                    splitted[j] = "null";

                    break;
                }

            }
        }
    }

    private void addCommandCenter()
    {

        char[] chars;
        int countt;

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < splitted.length; j++)
            {
                countt = 0;
                chars = splitted[j].toCharArray();

                for (int k = 0; k < splitted[j].length(); k++)
                {
                    if (chars[k] == ',')
                    {
                        countt++;
                    }
                }

                if (countt == 8)
                {
                    commandCenter = new CommandCenter(splitted[j], myTiles);

                    splitted[j] = "null";

                    break;
                }

            }
        }
    }

    private void addHorseman()
    {
        char[] chars;
        int countt = 0;

        for (int i = 0; i < 3; i++)
        {
            for(int j = 0; j < splitted.length; j++)
            {
                countt = 0;
                chars = splitted[j].toCharArray();
                //System.out.println(splitted[j]);
                for(int k = 0; k < splitted[j].length(); k++)
                {
                    if(chars[k] == ',')
                    {
                        countt++;
                        //System.out.println(chars[k]);
                    }
                }

                //System.out.println(countt);
                //System.out.println("__________________");
                if(countt == 1)
                {
                    //System.out.println("jesus");
                    horsemen.add(new Horseman(splitted[j], myTiles));

                    splitted[j] = "null";

                    break;
                }

            }

            //System.out.println(horsemen.get(i).canAttack() + " " + i) ;
        }
    }

    private void addSoldiers()
    {
        for (int i = 0; i < 5; i++)
        {
            for(int j = 0; j < splitted.length; j++)
            {
                if(!(splitted[j].contains(",") || splitted[j].equals("null")))
                {
                    soldiers.add(new Soldier(splitted[j], myTiles));

                    splitted[j] = "null";

                    break;
                }

            }

            //System.out.println(soldiers.get(i).getTilesOfThisSoldier() + " " + i) ;
        }
    }

    private void handleText(String type, int position)
    {
        for(Soldier soldier : soldiers)
        {
           // System.out.println(soldier.isAlive());
        }
        if(type.equals("soldier"))
        {
            if(soldiers.get(position).getRoundsLeftToAttack() == 0 && soldiers.get(position).isCanAttack())
            {
                roundsLeft.setText("AVAILABLE");
            }
            else
            {
                roundsLeft.setText("NOT AVAILABLE");
            }
        }
        else if(type.equals("horseman"))
        {
            for(Horseman horseman : horsemen)
            {
                System.out.println(horseman.isAlive());
            }
            if(horsemen.get(position).getRoundsLeft() == 0 && horsemen.get(position).isCanAttack())
            {
                roundsLeft.setText("AVAILABLE");
            }
            else
            {
                roundsLeft.setText("NOT AVAILABLE");
            }
        }
        else if(type.equals("castle"))
        {
            if(castles.get(position).getRoundsLeft() == 0 && castles.get(position).isCanAttack())
            {
                roundsLeft.setText("AVAILABLE");
            }
            else
            {
                roundsLeft.setText("NOT AVAILABLE");
            }
        }
        else if(type.equals("commandCenter") && commandCenter.isCanAttack())
        {
            if(commandCenter.getRoundsLeft() == 0)
            {
                roundsLeft.setText("AVAILABLE");
            }
            else
            {
                roundsLeft.setText("NOT AVAILABLE");
            }
        }

    }

    private void nextTurn()
    {
        hitCount = 0;

        ThreadSave.jesus = false;


        if (ThreadSave.isPlayersTurn())
        {
            count = 0;
            try
            {
                MainMenu.getDataOutputStream().writeUTF("nextTurn");

                for (MediumTiles tile : tiles)
                {
                    if (tile.isChecked())
                    {
                        count++;
                    }
                }

                //System.out.println(ThreadSave.jesus);

                MainMenu.getDataOutputStream().writeInt(count);

                for (MediumTiles tile : tiles)
                {
                    if (tile.isChecked())
                    {
                        //System.out.println(tile.getTileNumber());
                        MainMenu.getDataOutputStream().writeInt(tile.getTileNumber());
                    }
                }

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        for(SmallTiles tile : myTiles)
        {
            if(tile.isChecked())
            {
                hitCount++;
            }
        }

        try
        {
            MainMenu.getDataOutputStream().writeInt(hitCount);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        ThreadSave.jesus = true;

        ClientHandler.jesus = true;
        //System.out.println(ThreadSave.jesus);
    }
    int x = SetMap.getNumPerRow();
    int tileNumber;

    private void castleAction(int position)
    {
        for(MediumTiles tile : tiles)
        {
            tile.setOnMouseClicked(mouseEvent -> {
                tileNumber = tile.getTileNumber();

                if(tileNumber < SetMap.getNumOfTiles() - SetMap.getNumPerRow() && castles.get(position).isCanAttack()
                        && ((tile.getTileNumber() + 1 ) % SetMap.NUM_PER_ROW != 0) && ThreadSave.isPlayersTurn())
                {
                    if(!(tile.isChecked() || tiles.get(tileNumber + 1).isChecked()
                            || tiles.get(tileNumber + x).isChecked() || tiles.get(tileNumber + x + 1).isChecked()))
                    {

                        tile.setChecked(true);
                        tiles.get(tileNumber + 1).setChecked(true);
                        tiles.get(tileNumber + x).setChecked(true);
                        tiles.get(tileNumber + x + 1).setChecked(true);

                        tile.getBorderOfTile().setFill(Paint.valueOf("blue"));
                        tiles.get(tileNumber + 1).getBorderOfTile().setFill(Paint.valueOf("blue"));
                        tiles.get(tileNumber + x).getBorderOfTile().setFill(Paint.valueOf("blue"));
                        tiles.get(tileNumber + x + 1).getBorderOfTile().setFill(Paint.valueOf("blue"));

                        castles.get(position).attack();

                        nextTurn();
                        isFirstRound = false;
                        handleText("castle", position);
                    }
                }
            });

            tile.setOnMouseEntered(mouseEvent -> {
                tileNumber = tile.getTileNumber();

                if(tileNumber < SetMap.getNumOfTiles() - SetMap.getNumPerRow() && ((tile.getTileNumber() + 1 ) % SetMap.getNumPerRow() != 0))
                {
                    if(!(tile.isChecked() || tiles.get(tileNumber + 1).isChecked()
                            || tiles.get(tileNumber + x).isChecked() || tiles.get(tileNumber + x + 1).isChecked()) )
                    {

                        tile.getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                        tiles.get(tileNumber + 1).getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                        tiles.get(tileNumber + x).getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                        tiles.get(tileNumber + x + 1).getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                    }
                }
            });

            tile.setOnMouseExited(mouseEvent -> {
                tileNumber = tile.getTileNumber();

                if(tileNumber < SetMap.getNumOfTiles() - SetMap.getNumPerRow() && ((tile.getTileNumber() + 1 ) % SetMap.getNumPerRow() != 0))
                {
                    if(!(tile.isChecked() || tiles.get(tileNumber + 1).isChecked()
                            || tiles.get(tileNumber + x).isChecked() || tiles.get(tileNumber + x + 1).isChecked()) )
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

    private void horsemenAction(int position)
    {
        for (MediumTiles tile : tiles)
        {
            //System.out.println(tileNumber);;

            tile.setOnMouseClicked(mouseEvent -> {
                tileNumber = tile.getTileNumber();
                if(isVertical && horsemen.get(position).canAttack())
                {
                    if(tileNumber < SetMap.getNumOfTiles() - SetMap.getNumPerRow() && !tile.isChecked() &&
                            !tiles.get(tileNumber + 1).isChecked() && horsemen.get(position).canAttack() && ThreadSave.isPlayersTurn())
                    {
                        tile.setChecked(true);
                        tiles.get(tileNumber + x).setChecked(true);

                        tile.getBorderOfTile().setFill(Paint.valueOf("blue"));
                        tiles.get(tileNumber + x).getBorderOfTile().setFill(Paint.valueOf("blue"));

                        if(horsemen.get(position).canAttack())
                        {
                            horsemen.get(position).attack();
                        }

                        nextTurn();
                        isFirstRound = false;
                        handleText("horseman", position);
                    }
                }
                else if(!isVertical && horsemen.get(position).canAttack())
                {
                    if(!tile.isChecked() && !tiles.get(tileNumber + 1).isChecked() && horsemen.get(position).canAttack() &&
                            ( (tile.getTileNumber() + 1 ) % SetMap.NUM_PER_ROW != 0) && ThreadSave.isPlayersTurn())
                    {
                        tile.setChecked(true);
                        tiles.get(tileNumber + 1).setChecked(true);

                        tile.getBorderOfTile().setFill(Paint.valueOf("blue"));
                        tiles.get(tileNumber + 1).getBorderOfTile().setFill(Paint.valueOf("blue"));

                       horsemen.get(position).attack();

                        nextTurn();
                        isFirstRound = false;
                        handleText("horseman", position);
                    }
                }
            });

            tile.setOnMouseEntered(mouseEvent -> {
                tileNumber = tile.getTileNumber();
                if(isVertical)
                {
                    if(tileNumber < SetMap.getNumOfTiles() - SetMap.getNumPerRow() && !tile.isChecked() && !tiles.get(tileNumber + 1).isChecked())
                    {
                        tile.getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                        tiles.get(tileNumber + x).getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                    }
                }
                else
                {
                    if(!tile.isChecked() && !tiles.get(tileNumber + 1).isChecked() && ( (tile.getTileNumber() + 1 ) % SetMap.NUM_PER_ROW != 0))
                    {
                        tile.getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                        tiles.get(tileNumber + 1).getBorderOfTile().setFill(Paint.valueOf("lightblue"));
                    }
                }
            });

            tile.setOnMouseExited(mouseEvent -> {
                tileNumber = tile.getTileNumber();
                if(isVertical)
                {
                    if(tileNumber < SetMap.getNumOfTiles() - SetMap.getNumPerRow() && !tile.isChecked() && !tiles.get(tileNumber + 1).isChecked())
                    {
                        tile.getBorderOfTile().setFill(null);
                        tiles.get(tileNumber + x).getBorderOfTile().setFill(null);
                    }
                }
                else
                {
                    if(!tile.isChecked() && !tiles.get(tileNumber + 1).isChecked() && ( (tile.getTileNumber() + 1 ) % SetMap.NUM_PER_ROW != 0))
                    {
                        tile.getBorderOfTile().setFill(null);
                        tiles.get(tileNumber + 1).getBorderOfTile().setFill(null);
                    }
                }
            });

        }
    }

    private void soldierAction(int position)
    {
        for (MediumTiles tile : tiles)
        {
            tile.setOnMouseClicked(event ->
            {
                if (soldiers.get(position).isCanAttack() && ThreadSave.isPlayersTurn() && !tile.isChecked())
                {
                    tile.setChecked(true);
                    tile.getBorderOfTile().setFill(Paint.valueOf("blue"));

                    soldiers.get(position).attack();


                    nextTurn();
                    isFirstRound = false;

                    handleText("soldier", position);
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

    private void getPlayerMapFromServer()
    {
        try
        {
            MainMenu.getDataOutputStream().writeUTF("givePlayerMap");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        int number = 0;
        for (int i = 0; i < 28; i++)
        {
            try
            {
                number = MainMenu.getDataInputStream().readInt();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            myTiles.get(number).getBorderOfTile().setFill(Color.web("#3c1053"));
            myTiles.get(number).setChecked(true);
        }

        try
        {
            troopsTiles = MainMenu.getDataInputStream().readUTF();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        splitted = troopsTiles.split("_");
    }

    private void makeGameBoardPlayerMap()
    {
        for (int i = 0; i < SetMap.NUM_OF_TILES; i++)
        {
            myTiles.add(new SmallTiles(i));
        }

        for (int i = 0; i < myTiles.size(); i++)
        {
            SmallTiles tile = myTiles.get(i);
            tile.setTranslateX(SmallTiles.getTileSides() * (i % SetMap.NUM_PER_ROW));
            tile.setTranslateY(SmallTiles.getTileSides() * (int) (i / SetMap.NUM_PER_ROW));
            playerMap.getChildren().add(tile);
        }
    }

    private void makeGameBoardTargetMap()
    {
        for (int i = 0; i < SetMap.NUM_OF_TILES; i++)
        {
            tiles.add(new MediumTiles(i));
        }

        for (int i = 0; i < tiles.size(); i++)
        {
            MediumTiles tile = tiles.get(i);
            tile.setTranslateX(MediumTiles.getTileSides() * (i % SetMap.NUM_PER_ROW));
            tile.setTranslateY(MediumTiles.getTileSides() * (int) (i / SetMap.NUM_PER_ROW));
            targetMap.getChildren().add(tile);
        }
    }

    private void handleActionListeners()
    {
        commandcenter.setOnAction(actionEvent -> {
            handleText("commandCenter", 0);

            commandCenterAction();
        });
        soldier1.setOnAction(actionEvent ->
        {
            handleText("soldier", 0);
            soldierAction(0);

        });
        soldier2.setOnAction(actionEvent ->
        {
            handleText("soldier", 1);
            soldierAction(1);
        });
        soldier3.setOnAction(actionEvent ->
        {
            handleText("soldier", 2);

            soldierAction(2);
        });
        soldier4.setOnAction(actionEvent ->
        {
            handleText("soldier", 3);

            soldierAction(3);
        });
        soldier5.setOnAction(actionEvent ->
        {
            handleText("soldier", 4);

            soldierAction(4);
        });

        horseman1.setOnAction(actionEvent ->
        {
            handleText("horseman", 0);

            horsemenAction(0);
        });
        horseman2.setOnAction(actionEvent ->
        {
            handleText("horseman", 1);

            horsemenAction(1);
        });
        horseman3.setOnAction(actionEvent ->
        {
            handleText("horseman", 2);

            horsemenAction(2);
        });

        castle1.setOnAction(actionEvent -> {
            handleText("castle", 0);

            castleAction(0);
        });

        castle2.setOnAction(actionEvent -> {
            handleText("castle", 1);

            castleAction(1);
        });
    }

    private boolean commandCenterIF(MediumTiles tile)
    {
        return !(tile.isChecked() || tiles.get(tileNumber + 1).isChecked()
                || tiles.get(tileNumber + x).isChecked() || tiles.get(tileNumber + x + 1).isChecked()
                || tiles.get(tileNumber + x - 1).isChecked() || tiles.get(tileNumber - 1).isChecked() || tiles.get(tileNumber - x - 1).isChecked()
                || tiles.get(tileNumber - x).isChecked() || tiles.get(tileNumber - x + 1).isChecked());
    }

    private void commandCenterColorSet(String color, int tileNumber)
    {
        tiles.get(tileNumber).getBorderOfTile().setFill(Paint.valueOf(color));
        tiles.get(tileNumber + 1).getBorderOfTile().setFill(Paint.valueOf(color));
        tiles.get(tileNumber + x).getBorderOfTile().setFill(Paint.valueOf(color));
        tiles.get(tileNumber + x + 1).getBorderOfTile().setFill(Paint.valueOf(color));
        tiles.get(tileNumber + x - 1).getBorderOfTile().setFill(Paint.valueOf(color));
        tiles.get(tileNumber - 1).getBorderOfTile().setFill(Paint.valueOf(color));
        tiles.get(tileNumber - x - 1).getBorderOfTile().setFill(Paint.valueOf(color));
        tiles.get(tileNumber - x).getBorderOfTile().setFill(Paint.valueOf(color));
        tiles.get(tileNumber - x + 1).getBorderOfTile().setFill(Paint.valueOf(color));

    }


    private void commandCenterAction()
    {
        for(MediumTiles tile : tiles)
        {
            tile.setOnMouseClicked(mouseEvent ->
            {
                tileNumber = tile.getTileNumber();

                if (tileNumber < SetMap.getNumOfTiles() - SetMap.getNumPerRow() && commandCenter.isCanAttack()
                        && ((tile.getTileNumber() + 1) % SetMap.NUM_PER_ROW != 0) && ThreadSave.isPlayersTurn()
                        && tile.getTileNumber() % SetMap.getNumPerRow() != 0 && tile.getTileNumber() > SetMap.getNumPerRow())
                {
                    if (commandCenterIF(tile))
                    {

                        commandCenterColorSet("blue", tile.getTileNumber());

                        tile.setChecked(true);
                        tiles.get(tileNumber + 1).setChecked(true);
                        tiles.get(tileNumber + x).setChecked(true);
                        tiles.get(tileNumber + x + 1).setChecked(true);
                        tiles.get(tileNumber + x - 1).setChecked(true);
                        tiles.get(tileNumber + x - 1).setChecked(true);
                        tiles.get(tileNumber - 1).setChecked(true);
                        tiles.get(tileNumber - x - 1).setChecked(true);
                        tiles.get(tileNumber - x).setChecked(true);
                        tiles.get(tileNumber - x + 1).setChecked(true);
                        commandCenter.attack();
                        nextTurn();
                        isFirstRound = false;
                        handleText("commandCenter", 0);
                    }
                }
            });

            tile.setOnMouseEntered(mouseEvent ->
            {
                tileNumber = tile.getTileNumber();

                if (tileNumber < SetMap.getNumOfTiles() - SetMap.getNumPerRow() && ((tile.getTileNumber() + 1) % SetMap.getNumPerRow() != 0)
                        && tile.getTileNumber() % 10 != 0 && tile.getTileNumber() > SetMap.getNumPerRow())
                {
                    if (commandCenterIF(tile))
                    {
                        commandCenterColorSet("lightblue", tile.getTileNumber());
                    }
                }
            });

            tile.setOnMouseExited(mouseEvent ->
            {
                tileNumber = tile.getTileNumber();

                if (tileNumber < SetMap.getNumOfTiles() - SetMap.getNumPerRow() && ((tile.getTileNumber() + 1) % SetMap.getNumPerRow() != 0)
                        && tile.getTileNumber() % 10 != 0 && tile.getTileNumber() > SetMap.getNumPerRow())
                {
                    if (commandCenterIF(tile))
                    {
                        tiles.get(tileNumber).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber + 1).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber + x).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber + x + 1).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber + x - 1).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber - 1).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber - x - 1).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber - x).getBorderOfTile().setFill(null);
                        tiles.get(tileNumber - x + 1).getBorderOfTile().setFill(null);
                    }
                }
            });
        }
    }
}
