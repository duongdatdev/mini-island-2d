package maps;

import imageRender.ImageHandler;
import main.GameScene;
import network.entitiesNet.PlayerMP;
import objects.entities.Entity;
import objects.entities.NPC;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class Map {
    protected Tile[] tiles;
    protected int width = 16;
    protected int height = 16;
    protected BufferedImage[] tileSet;
    protected int[][] mapTileNum;
    protected GameScene gameScene;
    protected int mapTileCol = 70;
    protected int mapTileRow = 50;

    public ArrayList<PlayerMP> players;
    public PlayerMP player;

    private Entity[] npcs;
    private NPC pvpNPC;
    private NPC topNPC;
    private NPC mazeNPC;

    protected boolean render = true;

    public Map(GameScene gameScene) {
        this.gameScene = gameScene;
        players = new ArrayList<PlayerMP>();
        npcs = new Entity[2];
        mapTileNum = new int[mapTileCol][mapTileRow];

        player = gameScene.getPlayerMP();

        try {
            pvpNPC = new NPC("PvP", 1000, 1000, ImageIO.read(getClass().getResource("/Maps/Pvp/PvpNPC.png")), gameScene.getTileSize());
            topNPC = new NPC("Top 20", 1693, 535, ImageIO.read(getClass().getResource("/NPC/top20NPC.png")), gameScene.getTileSize());
            mazeNPC = new NPC("Maze", 2092, 1075, ImageIO.read(getClass().getResource("/Maps/Maze/mazeNPC.png")), gameScene.getTileSize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        npcs[0] = pvpNPC;
        npcs[0] = topNPC;
        npcs[1] = mazeNPC;

        setHitBox();

//        loadMap("/Maps/Map_tiles.png");
        loadMap("/Maps/tileSet.png");
        readMap("/Maps/map_1.csv");
    }

    public void setHitBox() {
        for (Entity npc : npcs) {
            Rectangle hitBox = new Rectangle(0, 0, gameScene.getTileSize(), gameScene.getTileSize());
            npc.setHitBox(hitBox);
        }
    }

    public void loadMap(String mapPath) {
        tileSet = ImageHandler.loadAssets2(mapPath, width, height);
        tiles = new Tile[tileSet.length];
        for (int i = 0; i < tileSet.length; i++) {

            tiles[i] = new Tile();

            tiles[i].setImage(tileSet[i]);

            /* Tile num
             * 9 -> water
             */
            setTileType(i);
        }
    }

    public void setTileType(int i) {
        if (i == 3) {
            tiles[i].setType(TileType.Grass);
        } else if (i == 153) {
            tiles[i].setType(TileType.Water);
        } else if (i == 78) {
            tiles[i].setType(TileType.Wall);
        }
    }

    public void draw(Graphics2D g2d, int tileSize) {
        int worldCol = 0;
        int worldRow = 0;
        if (render) {


            while (worldCol < mapTileCol && worldRow < mapTileRow) {
                int tileNum = mapTileNum[worldCol][worldRow];

                int worldX = worldCol * tileSize;
                int worldY = worldRow * tileSize;

                int screenX = worldX - gameScene.getPlayer().getWorldX() + gameScene.getPlayer().getScreenX();
                int screenY = worldY - gameScene.getPlayer().getWorldY() + gameScene.getPlayer().getScreenY();

                if (worldX > gameScene.getPlayer().getWorldX() - gameScene.getPlayer().getScreenX() - tileSize * 2
                        && worldX < gameScene.getPlayer().getWorldX() + gameScene.getPlayer().getScreenX() + tileSize * 2
                        && worldY > gameScene.getPlayer().getWorldY() - gameScene.getPlayer().getScreenY() - tileSize * 2
                        && worldY < gameScene.getPlayer().getWorldY() + gameScene.getPlayer().getScreenY() + tileSize * 2) {
                    g2d.drawImage(tiles[tileNum].getImage(), screenX, screenY, tileSize, tileSize, null);

                }

                worldCol++;
                if (worldCol == mapTileCol) {
                    worldCol = 0;
                    worldRow++;
                }


            }

            renderNPC(g2d);

            for (PlayerMP playerMP : players) {

                if (playerMP != null) {
                    int worldX = playerMP.getX();
                    int worldY = playerMP.getY();

                    int screenX = worldX - gameScene.getPlayer().getWorldX() + gameScene.getPlayer().getScreenX();
                    int screenY = worldY - gameScene.getPlayer().getWorldY() + gameScene.getPlayer().getScreenY();

                    if (worldX > gameScene.getPlayer().getWorldX() - gameScene.getPlayer().getScreenX() - tileSize * 2
                            && worldX < gameScene.getPlayer().getWorldX() + gameScene.getPlayer().getScreenX() + tileSize * 2
                            && worldY > gameScene.getPlayer().getWorldY() - gameScene.getPlayer().getScreenY() - tileSize * 2
                            && worldY < gameScene.getPlayer().getWorldY() + gameScene.getPlayer().getScreenY() + tileSize * 2) {
                        g2d.drawImage(playerMP.getPlayer().currentSprite(), screenX, screenY, tileSize, tileSize, null);
                        g2d.drawString(playerMP.getUsername(), screenX, screenY - 10);

                        BufferedImage chatImage = playerMP.getChatImage();
                        if (chatImage != null) {
                            System.out.println("Drawing chat image");
                            int chatImageWidth = chatImage.getWidth();
                            int chatImageHeight = chatImage.getHeight();
                            g2d.drawImage(chatImage, screenX, screenY - 20 - chatImageHeight, chatImageWidth, chatImageHeight, null);
                        }
                    }


//                    if (gameScene.getChatPanel().getChatImage() != null && gameScene.drawChat < 120) {
//                        int chatImageWidth = gameScene.getChatPanel().getChatImage().getWidth();
//                        int chatImageHeight = gameScene.getChatPanel().getChatImage().getHeight();
//                        g2d.drawImage(gameScene.getChatPanel().getChatImage(), screenX, screenY - 20, chatImageWidth, chatImageHeight, null);
//                        gameScene.drawChat++;
//                    } else {
//                        gameScene.getChatPanel().setChatImage(null);
//                        gameScene.drawChat = 0;
//                    }
                }
            }
        }
    }


    /*
     * This method is used to render the NPC on the map
     * @param g2d
     * @param tileSize
     */
    protected void renderNPC(Graphics2D g2d) {
        pvpNPC.checkDraw(player.getPlayer(), g2d);
        topNPC.checkDraw(player.getPlayer(), g2d);
        mazeNPC.checkDraw(player.getPlayer(), g2d);
    }

    public void stopRenderingMap() {
        render = false;
    }

    public void startRenderingMap() {
        render = true;
    }

    public void readMap(String mapPath) {
        InputStream ip = getClass().getResourceAsStream(mapPath);
        BufferedReader br = new BufferedReader(new InputStreamReader(ip));

        int row = 0;
        int col = 0;

        try {
            while (col < mapTileCol && row < mapTileRow) {
                String line = br.readLine();
                while (col < mapTileCol) {
                    String[] numbers = line.split(",");
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

    public void addPlayer(PlayerMP player) {
        players.add(player);
    }

    public PlayerMP getPlayer(String username) {
        try {
            for (PlayerMP mp : players) {
                if (mp != null && mp.getUsername().equals(username)) {
                    return mp;
                }
            }
        } catch (Exception e) {
            System.out.println("Player not found");
        }
        return null;
    }

    public void removePlayer(String username) {
        for (PlayerMP mp : players) {
            if (mp.getUsername().equals(username)) {
                System.out.println("Player " + mp.getUsername() + " has left the lobby.");
                players.remove(mp);
                break;
            }
        }
    }

    public void removeAllPlayers() {
        players.clear();
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

    public NPC getTopNPC() {
        return topNPC;
    }

    public void setTopNPC(NPC topNPC) {
        this.topNPC = topNPC;
    }

    public NPC getPvpNPC() {
        return pvpNPC;
    }

    public void setPvpNPC(NPC pvpNPC) {
        this.pvpNPC = pvpNPC;
    }

    public Entity[] getNpcs() {
        return npcs;
    }

    public void setNpcs(Entity[] npcs) {
        this.npcs = npcs;
    }

    public NPC getMazeNPC() {
        return mazeNPC;
    }

    public void setMazeNPC(NPC mazeNPC) {
        this.mazeNPC = mazeNPC;
    }
}
