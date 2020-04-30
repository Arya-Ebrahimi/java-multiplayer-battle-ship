package Java;

import Java.Units.Castle;
import Java.Units.Horseman;
import Java.Units.Soldier;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import javax.security.auth.login.CredentialNotFoundException;
import java.io.IOException;
import java.util.List;

public class ClientHandler extends Thread
{
    Text text;
    boolean isGameStarted = false;

    boolean[] hit = new boolean[SetMap.NUM_OF_TILES];

    boolean iWon = false;
    boolean opponentWon = false;

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
            //System.out.println("thread is running");
            if (!isGameStarted && ThreadSave.jesus)
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

            if (isGameStarted && ThreadSave.jesus)
            {
                String turn = null;
                try
                {
                    MainMenu.getDataOutputStream().writeUTF("giveTurn");

                    if(MainMenu.getPlayerNumber().equals("player1"))
                    {
                        iWon = MainMenu.getDataInputStream().readBoolean();

                        opponentWon = MainMenu.getDataInputStream().readBoolean();

                        if(iWon)
                        {
                            text.setText("You Won!");
                            break;
                        }
                        else if(opponentWon)
                        {
                            text.setText("Opponent Won!");
                            break;
                        }
                    }

                    if(MainMenu.getPlayerNumber().equals("player2"))
                    {
                        opponentWon = MainMenu.getDataInputStream().readBoolean();

                        iWon = MainMenu.getDataInputStream().readBoolean();

                        if(iWon)
                        {
                            text.setText("You Won!");
                            break;
                        }
                        else if(opponentWon)
                        {
                            text.setText("Opponent Won!");
                            break;
                        }
                    }


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

                if(ThreadSave.isPlayersTurn() && jesus)
                {
                    for(Soldier soldier : ThreadSave.soldiers)
                    {
                        soldier.refresh();

                    }

                    for(Horseman horseman : ThreadSave.horsemen)
                    {
                        horseman.refresh();
                    }

                    for(Castle castle : ThreadSave.castles)
                    {
                        castle.refresh();
                    }

                    ThreadSave.commandCenter.refresh();
                    System.out.println("refresh");


                    jesus = false;
                }


            }

        }

    }
}
