package collision;

import main.GameScene;
import maps.TileType;
import objects.entities.Entity;

public class Collision {
    private GameScene gameScene;

    public Collision(GameScene gameScene){
        this.gameScene = gameScene;

    }
    public void checkTile(Entity entity){
        int entityLeftWorldX = entity.getWorldX() + entity.getHitBox().x;
        int entityRightWorldX = entity.getWorldX() + entity.getHitBox().x + entity.getHitBox().width;
        int entityTopWorldY = entity.getWorldY() + entity.getHitBox().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getHitBox().y + entity.getHitBox().height;

        int entityLeftCol = entityLeftWorldX / gameScene.getTileSize();
        int entityRightCol = entityRightWorldX / gameScene.getTileSize();
        int entityTopRow = entityTopWorldY / gameScene.getTileSize();
        int entityBottomRow = entityBottomWorldY / gameScene.getTileSize();

        int tileNum1, tileNum2;

        switch (entity.direction){
            case "UP":
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gameScene.getTileSize();
                tileNum1 = gameScene.getMap().getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gameScene.getMap().getMapTileNum()[entityRightCol][entityTopRow];

                if(checkTile(tileNum1, tileNum2, TileType.Wall)) {
                    entity.setCollision(true);
                }
                else if(checkTile(tileNum1, tileNum2, TileType.Water)){
                    handlerCollisionWater(entity, tileNum1, tileNum2);
                }
                break;
            case "DOWN":
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gameScene.getTileSize();
                tileNum1 = gameScene.getMap().getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNum2 = gameScene.getMap().getMapTileNum()[entityRightCol][entityBottomRow];

                if(checkTile(tileNum1, tileNum2, TileType.Wall)) {
                    entity.setCollision(true);
                }
                else if(checkTile(tileNum1, tileNum2, TileType.Water)){
                    handlerCollisionWater(entity, tileNum1, tileNum2);
                }
                break;
            case "LEFT":
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gameScene.getTileSize();
                tileNum1 = gameScene.getMap().getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gameScene.getMap().getMapTileNum()[entityLeftCol][entityBottomRow];

                if(checkTile(tileNum1, tileNum2, TileType.Wall)) {
                    entity.setCollision(true);
                }
                else if(checkTile(tileNum1, tileNum2, TileType.Water)){
                    handlerCollisionWater(entity, tileNum1, tileNum2);
                }
                break;
            case "RIGHT":
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gameScene.getTileSize();
                tileNum1 = gameScene.getMap().getMapTileNum()[entityRightCol][entityTopRow];
                tileNum2 = gameScene.getMap().getMapTileNum()[entityRightCol][entityBottomRow];

                if(checkTile(tileNum1, tileNum2, TileType.Wall)) {
                    entity.setCollision(true);
                }
                else if(checkTile(tileNum1, tileNum2, TileType.Water)){
                    handlerCollisionWater(entity, tileNum1, tileNum2);
                }
                break;
        }
    }
    private boolean checkTile(int tileNum1, int tileNum2, TileType tileType){
        return gameScene.getMap().getTiles()[tileNum1].getType() == tileType || gameScene.getMap().getTiles()[tileNum2].getType() == tileType;
    }
    private void handlerCollisionWater(Entity entity, int tileNum1, int tileNum2){
        entity.setWorldX(1000);
        entity.setWorldY(1000);
        gameScene.getPlayerMP().updatePlayerInServer();
    }
}
