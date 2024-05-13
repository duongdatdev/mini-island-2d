package maps;

import imageRender.ImageHandler;
import main.GameScene;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Map {
    private Tile[] tiles;
    private int width = 32;
    private int height = 32;
    private BufferedImage[] tileSet;
    private int[][] mapTileNum;
    private GameScene gameScene;
    private int mapTileCol = 52;
    private int mapTileRow = 40;

    public Map(GameScene gameScene) {
        this.gameScene = gameScene;
        mapTileNum = new int[mapTileCol][mapTileRow];
        loadMap("/Maps/Map_tiles.png");
        readMap();
    }
    private void loadMap(String mapPath) {
        tileSet = ImageHandler.loadAssets2(mapPath, width, height);
        tiles = new Tile[tileSet.length];
        for (int i = 0; i < tileSet.length; i++) {

            tiles[i] = new Tile();

            tiles[i].setImage(tileSet[i]);

            if(i == 16){
                tiles[i].setType(TileType.Grass);
            }
            else if(i == 36){
                tiles[i].setType(TileType.Water);
            }
            else if(i == 0){
                tiles[i].setType(TileType.Wall);
            }
        }
    }

    public void draw(Graphics2D g2d, int tileSize) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < mapTileCol&& worldRow < mapTileRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * tileSize;
            int worldY = worldRow * tileSize;

            int screenX = worldX - gameScene.getPlayer().getWorldX() + gameScene.getPlayer().getScreenX();
            int screenY = worldY - gameScene.getPlayer().getWorldY() + gameScene.getPlayer().getScreenY();

            if (worldX > gameScene.getPlayer().getWorldX() - gameScene.getPlayer().getScreenX() - tileSize*2
            && worldX < gameScene.getPlayer().getWorldX() + gameScene.getPlayer().getScreenX() + tileSize*2
            && worldY > gameScene.getPlayer().getWorldY() - gameScene.getPlayer().getScreenY() - tileSize*2
            && worldY < gameScene.getPlayer().getWorldY() + gameScene.getPlayer().getScreenY()+ tileSize*2) {
                g2d.drawImage(tiles[tileNum].getImage(), screenX, screenY, tileSize, tileSize, null);
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
                    mapTileNum[col][row] = Integer.parseInt(numbers[col]);
                    System.out.print(mapTileNum[col][row] + " ");
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

    public Tile[] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage[] getTileSet() {
        return tileSet;
    }

    public void setTileSet(BufferedImage[] tileSet) {
        this.tileSet = tileSet;
    }

    public int[][] getMapTileNum() {
        return mapTileNum;
    }

    public void setMapTileNum(int[][] mapTileNum) {
        this.mapTileNum = mapTileNum;
    }

    public GameScene getGameScene() {
        return gameScene;
    }

    public void setGameScene(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    public int getMapTileCol() {
        return mapTileCol;
    }

    public void setMapTileCol(int mapTileCol) {
        this.mapTileCol = mapTileCol;
    }

    public int getMapTileRow() {
        return mapTileRow;
    }

    public void setMapTileRow(int mapTileRow) {
        this.mapTileRow = mapTileRow;
    }
}
