package maps.maze;

import main.GameScene;
import maps.Map;
import maps.TileType;

import java.io.*;

public class MazeMap extends Map {
    public MazeMap(GameScene gameScene) {
        super(gameScene);

        loadMap("/Maps/Maze/mazeTile.png");
    }

    public void readMap(String map, Runnable process) {
        int row = 0;
        int col = 0;

        char wall = '#';
        char space = ' ';
        char star = '*';
        char hole = 'O';

        try {
            String[] lines = map.split("/");
            mapTileCol = lines[0].length();
            mapTileRow = lines.length;
            System.out.println(mapTileCol + " " + mapTileRow);
            while (col < mapTileCol && row < mapTileRow) {
                while (col < mapTileCol) {
                    char[] charArray = lines[row].toCharArray();
                    if (charArray[col] == wall) {
                        mapTileNum[col][row] = 0;
                        System.out.print(mapTileNum[col][row] + " ");
                        col++;
                    } else if (charArray[col] == space) {
                        mapTileNum[col][row] = 2;
                        System.out.print(mapTileNum[col][row] + " ");
                        col++;
                    } else if (charArray[col] == star) {
                        mapTileNum[col][row] = 2;
                        System.out.print(mapTileNum[col][row] + " ");
                        col++;
                    }
                    else if (charArray[col] == hole) {
                        mapTileNum[col][row] = 3;
                        System.out.print(mapTileNum[col][row] + " ");
                        col++;
                    }
                    else {
                        mapTileNum[col][row] = 2;
                        System.out.print(mapTileNum[col][row] + " ");
                        col++;
                    }

                }
                if (col == mapTileCol) {
                    System.out.println("hhh");
                    col = 0;
                    row++;
                }
        }
        process.run();
    } catch(
    Exception e)

    {
        e.printStackTrace();
    }
}

@Override
public void setTileType(int i) {
    if (i == 3) {
        tiles[i].setType(TileType.Hole);
    }
}

public void clear() {
    for (int i = 0; i < mapTileCol; i++) {
        for (int j = 0; j < mapTileRow; j++) {
            mapTileNum[i][j] = 0;
        }
    }
}
}
