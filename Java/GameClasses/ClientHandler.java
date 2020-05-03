package Java.GameClasses;

import Java.Tiles.MediumTiles;
import Java.Tiles.SmallTiles;
import Java.Units.Castle;
import Java.Units.Horseman;
import Java.Units.Soldier;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class ClientHandler extends Thread
{
    private Text text;
    private boolean isGameStarted = false;
    private boolean doesPlayer1Won = false;
    private boolean doesPlayer2Won = false;
    private boolean[] hit = new boolean[SetMap.NUM_OF_TILES];
    private List<SmallTiles> myTiles;
    private List<MediumTiles> targetTiles;

    public ClientHandler(Text text, List<SmallTiles> myTiles, List<MediumTiles> targetTiles)
    {
        this.myTiles = myTiles;
        this.text = text;
        this.targetTiles = targetTiles;
    }

    public static boolean jesus = true;

    @Override
    public void run()
    {
        super.run();
        while (true)
        {
            try
            {
                Thread.sleep(750);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            if (!isGameStarted && ThreadSave.isJesus())
            {
                try
                {
                    MainMenu.getDataOutputStream().writeUTF("giveStatus");

                    String status = MainMenu.getDataInputStream().readUTF();

                    if (status.equals("true"))
                    {
                        isGameStarted = true;
                    } else
                    {
                        text.setText("waiting for another player");
                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            if (isGameStarted && ThreadSave.isJesus())
            {
                String turn = null;
                try
                {
                    MainMenu.getDataOutputStream().writeUTF("giveTurn");

                    doesPlayer1Won = MainMenu.getDataInputStream().readBoolean();
                    doesPlayer2Won = MainMenu.getDataInputStream().readBoolean();



                    turn = MainMenu.getDataInputStream().readUTF();
                    text.setText(turn + "'s turn");
                    ThreadSave.setIsPlayersTurn(turn.equals(MainMenu.getPlayerNumber()));

                } catch (IOException e)
                {
                    e.printStackTrace();
                }


                for(int i = 0; i < hit.length; i++)
                {
                    try
                    {
                        hit[i] = MainMenu.getDataInputStream().readBoolean();

                        if(hit[i])
                        {
                            myTiles.get(i).setAttacked(true);

                            if(myTiles.get(i).isChecked())
                            {
                                myTiles.get(i).getBorderOfTile().setFill(Color.web("red"));

                            }
                            else {
                                myTiles.get(i).getBorderOfTile().setFill(Color.web("#ebdb34"));
                            }

                        }

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                try
                {
                    int count = MainMenu.getDataInputStream().readInt();

                    for(int i = 0; i < count; i++)
                    {
                        int number = MainMenu.getDataInputStream().readInt();
                        targetTiles.get(number).getBorderOfTile().setFill(Paint.valueOf("red"));

                    }
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                if(ThreadSave.isIsPlayersTurn() && jesus)
                {
                    int count = 0;
                    for(Soldier soldier : ThreadSave.getSoldiers())
                    {
                        soldier.refresh();
                        if(soldier.isCanAttack())
                            count++;

                    }

                    for(Horseman horseman : ThreadSave.getHorsemen())
                    {
                        horseman.refresh();
                        if(horseman.isCanAttack())
                            count++;
                    }

                    for(Castle castle : ThreadSave.getCastles())
                    {
                        castle.refresh();
                        if(castle.isCanAttack())
                            count++;
                    }

                    ThreadSave.getCommandCenter().refresh();

                    if(ThreadSave.getCommandCenter().isCanAttack())
                        count++;


                    System.out.println("refresh" + count);

                    if(count == 0)
                    {
                        text.setText("u cant attack");

                        if(doesPlayer1Won)
                        {

                            if(MainMenu.getPlayerNumber().equals("player2"))
                            {
                                text.setText("YOU WON :)");
                            }
                            else
                            {
                                text.setText("YOU LOST :(");
                            }
                            break;
                        }
                        if(doesPlayer2Won)
                        {
                            if(MainMenu.getPlayerNumber().equals("player1"))
                            {
                                text.setText("YOU WON :)");
                            }
                            else
                            {
                                text.setText("YOU LOST :(");
                            }

                            break;
                        }
                    }

                    jesus = false;
                }

                if(doesPlayer1Won)
                {

                    if(MainMenu.getPlayerNumber().equals("player2"))
                    {
                        text.setText("YOU WON :)");
                    }
                    else
                    {
                        text.setText("YOU LOST :(");
                    }
                    break;
                }
                if(doesPlayer2Won)
                {
                    if(MainMenu.getPlayerNumber().equals("player1"))
                    {
                        text.setText("YOU WON :)");
                    }
                    else
                    {
                        text.setText("YOU LOST :(");
                    }

                    break;
                }

            }

        }

        try
        {
            MainMenu.getDataOutputStream().writeUTF("disconnect");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        MainMenu.closeConnection();

    }

}
