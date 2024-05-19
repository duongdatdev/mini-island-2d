package maps.maze;

import maps.Tile;

import java.awt.*;

public class MazeTile extends Tile {
    private Rectangle[] collisionRects;

    public MazeTile(){

    }

    public void setCollisionRects(Rectangle[] collisionRects) {
        this.collisionRects = collisionRects;
    }

    public Rectangle[] getCollisionRects() {
        return collisionRects;
    }
}
