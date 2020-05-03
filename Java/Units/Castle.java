package Java.Units;

import Java.Tiles.SmallTiles;

import java.io.Serializable;
import java.util.List;

public class Castle implements Serializable
{
    private boolean canAttack = true;

    private boolean isAlive = true;

    private int roundsLeft = 0;

    private String troopsTiles;
    private String[] splitted;

    private List<SmallTiles> tiles;

    private int hitCount = 0;


    public Castle(String troopsTiles, List<SmallTiles> tiles)
    {
        this.tiles = tiles;
        this.troopsTiles = troopsTiles;

        splitted = troopsTiles.split(",");
    }

    public void attack()
    {
        if(canAttack && isAlive)
        {
            roundsLeft = 4;
            canAttack = false;
        }
    }

    public void refresh()
    {
        if(isAlive)
        {
            if(tiles.get(Integer.parseInt(splitted[0])).isAttacked() && tiles.get(Integer.parseInt(splitted[1])).isAttacked()
                    && tiles.get(Integer.parseInt(splitted[2])).isAttacked() && tiles.get(Integer.parseInt(splitted[3])).isAttacked())
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
