package Java.Tiles;

import Java.GameClasses.MainMenu;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SmallTiles extends StackPane{

    private static  int tileSides = 300 / MainMenu.getNumberPerRow();
    private  int tileNumber;
    private boolean isChecked = false;
    private Rectangle border;
    private boolean isMouseOnThisTile = false;
    private boolean isAttacked = false;

    public boolean isAttacked()
    {
        return isAttacked;
    }

    public void setAttacked(boolean attacked)
    {
        isAttacked = attacked;
    }

    public SmallTiles(int tileNumber)
    {
        this.tileNumber = tileNumber;
        border = new Rectangle(tileSides, tileSides);
        border.setFill(null);
        border.setStroke(Color.BLACK);

        setAlignment(Pos.CENTER);
        getChildren().addAll(border);
    }

    public boolean isChecked()
    {
        return isChecked;
    }

    public void setChecked(boolean checked)
    {
        isChecked = checked;
    }

    public int getTileNumber()
    {
        return tileNumber;
    }

    public Rectangle getBorderOfTile()
    {
        return border;
    }

    public void setTileNumber(int tileNumber)
    {
        this.tileNumber = tileNumber;
    }

    public boolean isMouseOnThisTile()
    {
        return isMouseOnThisTile;
    }

    public void setMouseOnThisTile(boolean mouseOnThisTile)
    {
        isMouseOnThisTile = mouseOnThisTile;
    }

    public static int getTileSides()
    {
        return tileSides;
    }
}
