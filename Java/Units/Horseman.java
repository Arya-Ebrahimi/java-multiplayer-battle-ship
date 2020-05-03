package Java.Units;

import Java.Tiles.SmallTiles;

import java.io.Serializable;
import java.util.List;

public class Horseman implements Serializable
{
    private boolean canAttack = true;

    private boolean isAlive = true;

    private int roundsLeft = 0;

    String troopsTiles;

    private List<SmallTiles> tiles;

    private String[] splitted;

    public Horseman(String troopsTiles, List<SmallTiles> tiles)
    {
        this.troopsTiles = troopsTiles;
        this.tiles = tiles;


        splitted = troopsTiles.split(",");
    }

    public void attack()
    {
        if(canAttack && isAlive)
        {
            roundsLeft = 3;
            canAttack = false;
        }
    }

    public void refresh()
    {
        if(isAlive)
        {
            if(tiles.get(Integer.parseInt(splitted[0])).isAttacked() && tiles.get(Integer.parseInt(splitted[1])).isAttacked())
            {
                System.out.println(splitted[0] + splitted[1]);
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

    public boolean isCanAttack()
    {
        return canAttack && isAlive;
    }

    public boolean canAttack()
    {
        return canAttack;
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
