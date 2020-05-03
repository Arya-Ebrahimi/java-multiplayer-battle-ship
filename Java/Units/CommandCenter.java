package Java.Units;

import Java.Tiles.SmallTiles;

import java.io.Serializable;
import java.util.List;

public class CommandCenter implements Serializable
{
    private boolean canAttack = true;

    private boolean isAlive = true;

    private int roundsLeft = 0;

    private static CommandCenter commandCenters;

    private String troopsTiles;

    private String[] splitted;

    private List<SmallTiles> tiles;

    public CommandCenter(String troopsTiles, List<SmallTiles> tiles)
    {
        this.troopsTiles = troopsTiles;
        this.tiles = tiles;

        splitted = troopsTiles.split(",");
    }

    public void attack()
    {
        if(canAttack && isAlive)
        {
            roundsLeft = 5;
            canAttack = false;
        }
    }

    public void refresh()
    {
        if(isAlive)
        {
            if(tiles.get(Integer.parseInt(splitted[0])).isAttacked() && tiles.get(Integer.parseInt(splitted[1])).isAttacked()
                    && tiles.get(Integer.parseInt(splitted[2])).isAttacked() && tiles.get(Integer.parseInt(splitted[3])).isAttacked()
                    && tiles.get(Integer.parseInt(splitted[4])).isAttacked() && tiles.get(Integer.parseInt(splitted[5])).isAttacked()
                    && tiles.get(Integer.parseInt(splitted[6])).isAttacked() && tiles.get(Integer.parseInt(splitted[7])).isAttacked()
                    && tiles.get(Integer.parseInt(splitted[8])).isAttacked())
            {
                isAlive = false;
            }
        }

        if(!canAttack && isAlive)
        {
            if(roundsLeft == 0)
            {
                canAttack = true;
            }
            else
            {
                roundsLeft--;
                if(roundsLeft == 0)
                {
                    canAttack = true;
                }
            }
        }
    }

    public static CommandCenter getCommandCenters()
    {
        return commandCenters;
    }

    public static void setCommandCenters(CommandCenter commandCenters)
    {
        CommandCenter.commandCenters = commandCenters;
    }

    public boolean isCanAttack()
    {
        return canAttack && isAlive;
    }

    public void setCanAttack(boolean canAttack)
    {
        this.canAttack = canAttack;
    }

    public boolean isAlive()
    {
        return isAlive;
    }

    public void setAlive(boolean alive)
    {
        isAlive = alive;
    }

    public int getRoundsLeft()
    {
        return roundsLeft;
    }

    public void setRoundsLeft(int roundsLeft)
    {
        this.roundsLeft = roundsLeft;
    }

}
