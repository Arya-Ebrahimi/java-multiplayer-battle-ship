package Java.Server;

import java.io.DataOutputStream;

public class ServerSave
{

    private static String player1Username = null;
    private static String player2Username = null;
    private static int mapSize = 0;
    private static boolean is1Connected = false;
    private static boolean is2Connected = false;
    private static boolean turn = false;
    static boolean[] targetedTilesPlayer1;
    static boolean[] targetedTilesPlayer2;

    private static boolean[] player1Map;
    private static boolean[] player2Map;

    private static DataOutputStream player1dos;
    private static DataOutputStream player2dos;

    private static boolean doesPlayer1Won = false;
    private  static boolean doesPlayer2Won = false;

    public static String getPlayer1Username()
    {
        return player1Username;
    }

    public static void setPlayer1Username(String player1Username)
    {
        ServerSave.player1Username = player1Username;
    }

    public static String getPlayer2Username()
    {
        return player2Username;
    }

    public static void setPlayer2Username(String player2Username)
    {
        ServerSave.player2Username = player2Username;
    }

    public static int getMapSize()
    {
        return mapSize;
    }

    public static void setMapSize(int mapSize)
    {
        ServerSave.mapSize = mapSize;
    }

    public static boolean isIs1Connected()
    {
        return is1Connected;
    }

    public static void setIs1Connected(boolean is1Connected)
    {
        ServerSave.is1Connected = is1Connected;
    }

    public static boolean isIs2Connected()
    {
        return is2Connected;
    }

    public static void setIs2Connected(boolean is2Connected)
    {
        ServerSave.is2Connected = is2Connected;
    }

    public static boolean isTurn()
    {
        return turn;
    }

    public static void setTurn(boolean turn)
    {
        ServerSave.turn = turn;
    }

    public static boolean[] getTargetedTilesPlayer1()
    {
        return targetedTilesPlayer1;
    }

    public static void setTargetedTilesPlayer1(boolean[] targetedTilesPlayer1)
    {
        ServerSave.targetedTilesPlayer1 = targetedTilesPlayer1;
    }

    public static boolean[] getTargetedTilesPlayer2()
    {
        return targetedTilesPlayer2;
    }

    public static void setTargetedTilesPlayer2(boolean[] targetedTilesPlayer2)
    {
        ServerSave.targetedTilesPlayer2 = targetedTilesPlayer2;
    }

    public static boolean[] getPlayer1Map()
    {
        return player1Map;
    }

    public static void setPlayer1Map(boolean[] player1Map)
    {
        ServerSave.player1Map = player1Map;
    }

    public static boolean[] getPlayer2Map()
    {
        return player2Map;
    }

    public static void setPlayer2Map(boolean[] player2Map)
    {
        ServerSave.player2Map = player2Map;
    }

    public static DataOutputStream getPlayer1dos()
    {
        return player1dos;
    }

    public static void setPlayer1dos(DataOutputStream player1dos)
    {
        ServerSave.player1dos = player1dos;
    }

    public static DataOutputStream getPlayer2dos()
    {
        return player2dos;
    }

    public static void setPlayer2dos(DataOutputStream player2dos)
    {
        ServerSave.player2dos = player2dos;
    }

    public static boolean isDoesPlayer1Won()
    {
        return doesPlayer1Won;
    }

    public static void setDoesPlayer1Won(boolean doesPlayer1Won)
    {
        ServerSave.doesPlayer1Won = doesPlayer1Won;
    }

    public static boolean isDoesPlayer2Won()
    {
        return doesPlayer2Won;
    }

    public static void setDoesPlayer2Won(boolean doesPlayer2Won)
    {
        ServerSave.doesPlayer2Won = doesPlayer2Won;
    }
}
