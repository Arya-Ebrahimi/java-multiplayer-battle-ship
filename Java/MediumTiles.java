package Java;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class MediumTiles extends StackPane{

    public static  int tileSides = 500 / MainMenu.numberPerRow;
    private  int tileNumber;
    private boolean isChecked = false;
    private Rectangle border;
    private boolean isMouseOnThisTile = false;

    public MediumTiles(int tileNumber)
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
