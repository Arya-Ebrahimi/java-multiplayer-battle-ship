package Java.GameClasses;

import Java.Units.Castle;
import Java.Units.CommandCenter;
import Java.Units.Horseman;
import Java.Units.Soldier;

import java.util.ArrayList;

public class ThreadSave
{
    private static boolean isPlayersTurn = false;

    private static boolean isPlayersTurn()
    {
        return isPlayersTurn;
    }

    public static void setIsPlayersTurn(boolean isPlayersTurn)
    {
        ThreadSave.isPlayersTurn = isPlayersTurn;
    }

    private static boolean jesus = true;

    private static ArrayList<Soldier> soldiers;

    private static ArrayList<Horseman> horsemen;

    private static CommandCenter commandCenter;

    private static ArrayList<Castle> castles;

    public static boolean isIsPlayersTurn()
    {
        return isPlayersTurn;
    }

    public static boolean isJesus()
    {
        return jesus;
    }

    public static void setJesus(boolean jesus)
    {
        ThreadSave.jesus = jesus;
    }

    public static ArrayList<Soldier> getSoldiers()
    {
        return soldiers;
    }

    public static void setSoldiers(ArrayList<Soldier> soldiers)
    {
        ThreadSave.soldiers = soldiers;
    }

    public static ArrayList<Horseman> getHorsemen()
    {
        return horsemen;
    }

    public static void setHorsemen(ArrayList<Horseman> horsemen)
    {
        ThreadSave.horsemen = horsemen;
    }

    public static CommandCenter getCommandCenter()
    {
        return commandCenter;
    }

    public static void setCommandCenter(CommandCenter commandCenter)
    {
        ThreadSave.commandCenter = commandCenter;
    }

    public static ArrayList<Castle> getCastles()
    {
        return castles;
    }

    public static void setCastles(ArrayList<Castle> castles)
    {
        ThreadSave.castles = castles;
    }
}

