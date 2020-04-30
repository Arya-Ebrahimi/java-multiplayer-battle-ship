package Server;

import Java.ThreadSave;
import Java.Units.Castle;
import Java.Units.CommandCenter;
import Java.Units.Horseman;
import Java.Units.Soldier;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerHandler extends Thread
{

    Socket socket = null;
    DataInputStream dis = null;
    DataOutputStream dos = null;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;

    String playerNumber;


    boolean[][] gameMap;
    int[] gameMapInt;

    int hitCount;

    private String troopstiles = "";

    public ServerHandler(Socket socket, String playerNumber) throws IOException
    {
        this.socket = socket;
        this.playerNumber = playerNumber;

        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());

        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());

        if (playerNumber.equals("player1"))
        {
            ServerSave.player1dos = dos;
        } else if (playerNumber.equals("player2"))
        {
            ServerSave.player2dos = dos;
        }
    }

    String key = null;

    @Override
    public void run()
    {
        super.run();

        System.out.println("thread started ...");

        while (true)
        {
            try
            {
                key = dis.readUTF();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            //System.out.println(key);

            if (key.equals("givePlayerNumber"))
            {
                try
                {
                    dos.writeUTF(playerNumber);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (key.equals("username"))
            {
                if (playerNumber.equals("player1"))
                {
                    try
                    {
                        ServerSave.player1Username = dis.readUTF();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                } else if (playerNumber.equals("player2"))
                {
                    try
                    {
                        ServerSave.player2Username = dis.readUTF();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            if (key.equals("mapSize"))
            {
                try
                {
                    ServerSave.mapSize = dis.readInt();

                    ServerSave.targetedTilesPlayer1 = new boolean[ServerSave.mapSize * ServerSave.mapSize];
                    ServerSave.targetedTilesPlayer2 = new boolean[ServerSave.mapSize * ServerSave.mapSize];
                    ServerSave.player1Map = new boolean[ServerSave.mapSize * ServerSave.mapSize];
                    ServerSave.player2Map = new boolean[ServerSave.mapSize * ServerSave.mapSize];

                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            if (key.equals("settingMap"))
            {
                gameMap = new boolean[ServerSave.mapSize][ServerSave.mapSize];
                gameMapInt = new int[28];
                int number = 0;

                for (int i = 0; i < 28; i++)
                {
                    try
                    {
                        number = dis.readInt();
                        gameMapInt[i] = number;
                        if (playerNumber.equals("player1"))
                        {
                            ServerSave.player1Map[number] = true;
                        } else if (playerNumber.equals("player2"))
                        {
                            ServerSave.player2Map[number] = true;
                        }
                        gameMap[number / ServerSave.mapSize][number % ServerSave.mapSize] = true;

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    System.out.println(number);

                }

                try
                {
                    troopstiles = dis.readUTF();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            if (key.equals("printMap"))
            {
                for (int k = 0; k < ServerSave.mapSize; k++)
                {
                    for (int j = 0; j < ServerSave.mapSize; j++)
                    {
                        System.out.print(gameMap[k][j] + "  ");
                    }
                    System.out.println();
                }
            }

            if (key.equals("givePlayerMap"))
            {

                for (int i = 0; i < 28; i++)
                {
                    try
                    {
                        dos.writeInt(gameMapInt[i]);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                try
                {
                    dos.writeUTF(troopstiles);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                if (playerNumber.equals("player1"))
                {
                    ServerSave.is1Connected = true;
                } else
                {
                    ServerSave.is2Connected = true;
                }
            }

            if (key.equals("giveStatus"))
            {
                if (ServerSave.is1Connected && ServerSave.is2Connected)
                {
                    try
                    {
                        dos.writeUTF("true");
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                } else
                {
                    try
                    {
                        dos.writeUTF("false");
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }
            }

            int count;
            if (key.equals("giveTurn"))
            {

                try
                {
                    dos.writeBoolean(ServerSave.doesPlayer1Won);
                    dos.writeBoolean(ServerSave.doesPlayer2Won);

                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                count = 0;

                if (ServerSave.turn)
                {
                    try
                    {
                        dos.writeUTF("player2");
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }
                else
                {
                    try
                    {
                        dos.writeUTF("player1");
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }

                if(playerNumber.equals("player1"))
                {
                    for(int i = 0; i < ServerSave.mapSize * ServerSave.mapSize; i++)
                    {
                        try
                        {
                            dos.writeBoolean(ServerSave.targetedTilesPlayer2[i]);
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }


                    for(int i = 0; i < ServerSave.mapSize * ServerSave.mapSize; i++)
                    {
                        if(ServerSave.player2Map[i] && ServerSave.targetedTilesPlayer1[i])
                        {
                            count++;
                        }
                    }

                    try
                    {
                        dos.writeInt(count);

                        for(int i = 0; i < ServerSave.mapSize * ServerSave.mapSize; i++)
                        {
                            if(ServerSave.player2Map[i] && ServerSave.targetedTilesPlayer1[i])
                            {
                                dos.writeInt(i);
                            }
                        }

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

                else
                {
                    for(int i = 0; i < ServerSave.mapSize * ServerSave.mapSize; i++)
                    {
                        try
                        {
                            dos.writeBoolean(ServerSave.targetedTilesPlayer1[i]);
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }


                    for(int i = 0; i < ServerSave.mapSize * ServerSave.mapSize; i++)
                    {
                        if(ServerSave.player1Map[i] && ServerSave.targetedTilesPlayer2[i])
                        {
                            count++;
                        }
                    }

                    try
                    {
                        dos.writeInt(count);

                        for(int i = 0; i < ServerSave.mapSize * ServerSave.mapSize; i++)
                        {
                            if(ServerSave.player1Map[i] && ServerSave.targetedTilesPlayer2[i])
                            {
                                dos.writeInt(i);
                            }
                        }

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            if(key.equals("refresh"))
            {
                    count = 0;

                    if(playerNumber.equals("player1"))
                    {
                        for(int i = 0; i < ServerSave.mapSize * ServerSave.mapSize; i++)
                        {
                            if(ServerSave.player1Map[i] && ServerSave.targetedTilesPlayer2[i])
                            {
                                count++;
                            }
                        }

                        try
                        {
                            dos.writeInt(count);
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
            }

            int number;

            if (key.equals("nextTurn"))
            {
                ServerSave.turn = !ServerSave.turn;
                try
                {
                    number = dis.readInt();
                    System.out.println(number);

                    if (number != 0)
                    {
                        if (playerNumber.equals("player1"))
                        {
                            for (int i = 0; i < number; i++)
                            {

                                int number2 = dis.readInt();
                                ServerSave.targetedTilesPlayer1[number2] = true;

                                System.out.println(number2);
                            }

                        } else
                        {
                            for (int i = 0; i < number; i++)
                            {
                                int number2 = dis.readInt();
                                ServerSave.targetedTilesPlayer2[number2] = true;

                                System.out.println(number2);
                            }

                        }
                    }

                    hitCount = dis.readInt();

                    if(hitCount == 28)
                    {
                        if(playerNumber.equals("player1"))
                        {
                            ServerSave.doesPlayer1Won = true;
                        }
                        else
                        {
                            ServerSave.doesPlayer2Won = true;
                        }
                    }

                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }


        }
    }
}
