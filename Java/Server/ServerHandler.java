package Java.Server;

import java.io.*;
import java.net.Socket;

public class ServerHandler extends Thread
{

    Socket socket = null;
    DataInputStream dis = null;
    DataOutputStream dos = null;

    String playerNumber;


    boolean[][] gameMap;
    int[] gameMapInt;

    int hitCount = 0;

    private String troopstiles = "";

    public ServerHandler(Socket socket, String playerNumber) throws IOException
    {
        this.socket = socket;
        this.playerNumber = playerNumber;

        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());

        if (playerNumber.equals("player1"))
        {
            ServerSave.setPlayer1dos(dos);
        } else if (playerNumber.equals("player2"))
        {
            ServerSave.setPlayer2dos(dos);
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

            if(key.equals("disconnect"))
            {
                break;
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
                        ServerSave.setPlayer1Username(dis.readUTF());
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                } else if (playerNumber.equals("player2"))
                {
                    try
                    {
                        ServerSave.setPlayer2Username(dis.readUTF());
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
                    ServerSave.setMapSize(dis.readInt());

                    ServerSave.setTargetedTilesPlayer1(new boolean[ServerSave.getMapSize() * ServerSave.getMapSize()]);
                    ServerSave.setTargetedTilesPlayer2(new boolean[ServerSave.getMapSize() * ServerSave.getMapSize()]);
                    ServerSave.setPlayer1Map(new boolean[ServerSave.getMapSize() * ServerSave.getMapSize()]);
                    ServerSave.setPlayer2Map(new boolean[ServerSave.getMapSize() * ServerSave.getMapSize()]);

                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            if (key.equals("settingMap"))
            {
                gameMap = new boolean[ServerSave.getMapSize()][ServerSave.getMapSize()];
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
                            ServerSave.getPlayer1Map()[number] = true;
                        } else if (playerNumber.equals("player2"))
                        {
                            ServerSave.getPlayer2Map()[number] = true;
                        }
                        gameMap[number / ServerSave.getMapSize()][number % ServerSave.getMapSize()] = true;

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
                for (int k = 0; k < ServerSave.getMapSize(); k++)
                {
                    for (int j = 0; j < ServerSave.getMapSize(); j++)
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
                    ServerSave.setIs1Connected(true);
                } else
                {
                    ServerSave.setIs2Connected(true);
                }
            }

            if (key.equals("giveStatus"))
            {
                if (ServerSave.isIs1Connected() && ServerSave.isIs2Connected())
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
                count = 0;

                try
                {
                    dos.writeBoolean(ServerSave.isDoesPlayer1Won());
                    dos.writeBoolean(ServerSave.isDoesPlayer2Won());

                } catch (IOException e)
                {
                    e.printStackTrace();
                }


                if (ServerSave.isTurn())
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
                    for(int i = 0; i < ServerSave.getMapSize() * ServerSave.getMapSize(); i++)
                    {
                        try
                        {
                            dos.writeBoolean(ServerSave.getTargetedTilesPlayer2()[i]);
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }


                    for(int i = 0; i < ServerSave.getMapSize() * ServerSave.getMapSize(); i++)
                    {
                        if(ServerSave.getPlayer2Map()[i] && ServerSave.getTargetedTilesPlayer1()[i])
                        {
                            count++;
                        }
                    }

                    try
                    {
                        dos.writeInt(count);

                        for(int i = 0; i < ServerSave.getMapSize() * ServerSave.getMapSize(); i++)
                        {
                            if(ServerSave.getPlayer2Map()[i] && ServerSave.getTargetedTilesPlayer1()[i])
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
                    for(int i = 0; i < ServerSave.getMapSize() * ServerSave.getMapSize(); i++)
                    {
                        try
                        {
                            dos.writeBoolean(ServerSave.getTargetedTilesPlayer1()[i]);
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }


                    for(int i = 0; i < ServerSave.getMapSize() * ServerSave.getMapSize(); i++)
                    {
                        if(ServerSave.getPlayer1Map()[i] && ServerSave.getTargetedTilesPlayer2()[i])
                        {
                            count++;
                        }
                    }

                    try
                    {
                        dos.writeInt(count);

                        for(int i = 0; i < ServerSave.getMapSize() * ServerSave.getMapSize(); i++)
                        {
                            if(ServerSave.getPlayer1Map()[i] && ServerSave.getTargetedTilesPlayer2()[i])
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
                        for(int i = 0; i < ServerSave.getMapSize() * ServerSave.getMapSize(); i++)
                        {
                            if(ServerSave.getPlayer1Map()[i] && ServerSave.getTargetedTilesPlayer2()[i])
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
                ServerSave.setTurn(!ServerSave.isTurn());
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

                    System.out.println(hitCount);

                    if(hitCount == 28)
                    {
                        if(playerNumber.equals("player1"))
                        {
                            ServerSave.setDoesPlayer1Won(true);
                        }
                        else
                        {
                            ServerSave.setDoesPlayer2Won(true);
                        }
                    }

                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }


        }

        try
        {
            Server.closeConnection();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
