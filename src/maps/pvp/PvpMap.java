package maps.pvp;

import main.GameScene;
import maps.Map;
import maps.TileType;
import network.client.Client;

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
    public void setTileType(int i) {
        if(i == 13) {
            tiles[i].setType(TileType.Wall);
        }
    }
}
