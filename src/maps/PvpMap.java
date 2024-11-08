package maps;

import main.GameScene;
import network.client.Client;

import java.awt.*;

public class PvpMap extends Map {
    private int startX;
    private int startY;

    private Client client;

    public PvpMap(GameScene gameScene) {
        super(gameScene);
        mapTileCol = 50;
        mapTileRow = 50;
        mapTileNum = new int[mapTileCol][mapTileRow];
        loadMap("/Maps/Pvp/pvpMap.png");
        readMap("/Maps/Pvp/pvpMap.csv");
    }

    @Override
    protected void renderNPC(Graphics2D g2d) {
        gameScene.getLobbyMap().getPvpNPC().checkDraw(gameScene.getPlayer(), g2d);
    }

    @Override
    public void setTileType(int i) {
        if(i == 13) {
            tiles[i].setType(TileType.Wall);
        }
    }
}
