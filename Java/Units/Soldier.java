package Java.Units;

import Java.MediumTiles;
import Java.SmallTiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Soldier implements Serializable
{

    //private static ArrayList<Soldier> soldiers = new ArrayList<>();
    private List<SmallTiles> tiles;
    private boolean canAttack = true;
    private int roundsLeftToAttack = 0;
    private boolean isAlive = true;

    private int tile;

    private String tilesOfThisSoldier;


    public Soldier(String tilesOfThisSoldier, List<SmallTiles> tiles)
    {
        this.tilesOfThisSoldier = tilesOfThisSoldier;
        this.tiles = tiles;
    }
    public void refresh()
    {
        if(isAlive)
        {
            if(tiles.get(Integer.parseInt(tilesOfThisSoldier)).isAttacked())
            {
                isAlive = false;
            }
        }
        if(!canAttack)
        {
            if(roundsLeftToAttack == 0)
            {
                canAttack = true;
            }
            else
            {
                roundsLeftToAttack--;

                if(roundsLeftToAttack == 0)
                {
                    canAttack = true;
                }
            }
        }
    }
    public void attack()
    {
        if(canAttack && isAlive)
        {
            roundsLeftToAttack = 2;
            canAttack = false;
        }
    }

    public int getTile()
    {
        return tile;
    }

    public void setTile(int tile)
    {
        this.tile = tile;
    }


    public boolean isCanAttack()
    {
        return canAttack && isAlive;
    }

    public void setCanAttack(boolean canAttack)
    {
        this.canAttack = canAttack;
    }

    public int getRoundsLeftToAttack()
    {
        return roundsLeftToAttack;
    }

    public void setRoundsLeftToAttack(int roundsLeftToAttack)
    {
        this.roundsLeftToAttack = roundsLeftToAttack;
    }

    public boolean isAlive()
    {
        return isAlive;
    }

    public void setAlive(boolean alive)
    {
        isAlive = alive;
    }

    public String getTilesOfThisSoldier()
    {
        return tilesOfThisSoldier;
    }

    public void setTilesOfThisSoldier(String tilesOfThisSoldier)
    {
        this.tilesOfThisSoldier = tilesOfThisSoldier;
    }
}
