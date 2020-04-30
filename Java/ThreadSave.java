package Java;

import Java.Units.Castle;
import Java.Units.CommandCenter;
import Java.Units.Horseman;
import Java.Units.Soldier;

import java.util.ArrayList;

public class ThreadSave
{
    private static boolean isPlayersTurn = false;

    public static boolean isPlayersTurn()
    {
        return isPlayersTurn;
    }

    public static void setIsPlayersTurn(boolean isPlayersTurn)
    {
        ThreadSave.isPlayersTurn = isPlayersTurn;
    }

    public static boolean jesus = true;

    public static ArrayList<Soldier> soldiers;

    public static ArrayList<Horseman> horsemen;

    public static CommandCenter commandCenter;

    public static ArrayList<Castle> castles;

}

