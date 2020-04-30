package Java;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane{

    public static final int tileSides = 600 / MainMenu.numberPerRow;
    private  int tileNumber;
    private boolean isChecked = false;
    private Rectangle border;
    private boolean isMouseOnThisTile = false;

    public Tile(int tileNumber)
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
