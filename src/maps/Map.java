package maps;

import imageRender.ImageHandler;
import main.GameScene;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Map {
    private Tile[] titles;
    private int width = 32;
    private int height = 32;
    private BufferedImage[] tileSet;
    private int[][] mapNumbers;
    private GameScene gameScene;
    private int mapTileCol = 52;
    private int mapTileRow = 40;

    public Map(GameScene gameScene) {
        this.gameScene = gameScene;
        mapNumbers = new int[mapTileCol][mapTileRow];
        loadMap("/Maps/Map_tiles.png");
        readMap();
    }
    private void loadMap(String mapPath) {
        tileSet = ImageHandler.loadAssets(mapPath, width, height);
        titles = new Tile[tileSet.length];
        for (int i = 0; i < tileSet.length; i++) {
            titles[i] = new Tile();
            titles[i].setImage(tileSet[i]);
        }
    }

    public void draw(Graphics2D g2d, int tileSize) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < mapTileCol&& worldRow < mapTileRow) {
            int tileNum = mapNumbers[worldCol][worldRow];

            int worldX = worldCol * tileSize;
            int worldY = worldRow * tileSize;

            int screenX = worldX - gameScene.getPlayer().getWorldX() + gameScene.getPlayer().getScreenX();
            int screenY = worldY - gameScene.getPlayer().getWorldY() + gameScene.getPlayer().getScreenY();

            if (worldX > gameScene.getPlayer().getWorldX() - gameScene.getPlayer().getScreenX() - tileSize*2
            && worldX < gameScene.getPlayer().getWorldX() + gameScene.getPlayer().getScreenX() + tileSize*2
            && worldY > gameScene.getPlayer().getWorldY() - gameScene.getPlayer().getScreenY() - tileSize*2
            && worldY < gameScene.getPlayer().getWorldY() + gameScene.getPlayer().getScreenY()+ tileSize*2) {
                g2d.drawImage(titles[tileNum].getImage(), screenX, screenY, tileSize, tileSize, null);
            }

            worldCol++;
            if (worldCol == mapTileCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    private void readMap() {
        InputStream ip = getClass().getResourceAsStream("/Maps/Map_01.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(ip));

        int row = 0;
        int col = 0;

        try {
            while (col < mapTileCol && row < mapTileRow) {
                String line = br.readLine();
                while (col < mapTileCol) {
                    String[] numbers = line.split(" ");
                    mapNumbers[col][row] = Integer.parseInt(numbers[col]);
                    System.out.print(mapNumbers[col][row] + " ");
                    col++;
                }
                if (col == mapTileCol) {
                    System.out.println();
                    col = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
