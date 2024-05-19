package maps.maze;

import main.GameScene;
import maps.Map;

public class MazeMap extends Map {
    private int startX;
    private int startY;

    public MazeMap(GameScene gameScene) {
        super(gameScene);

        loadMap("/Maps/Maze/maze_tile.png");


    }

    // Getter and Setter methods
    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

}
