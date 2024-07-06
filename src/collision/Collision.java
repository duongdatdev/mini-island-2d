package collision;

import main.GameScene;
import maps.TileType;
import objects.entities.Entity;

public class Collision {
    private GameScene gameScene;

    public Collision(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    /**
     * Check collision with tiles
     */
    public void checkTile(Entity entity, Runnable process) {
        int entityLeftWorldX = entity.getWorldX() + entity.getHitBox().x;
        int entityRightWorldX = entity.getWorldX() + entity.getHitBox().x + entity.getHitBox().width;
        int entityTopWorldY = entity.getWorldY() + entity.getHitBox().y;
        int entityBottomWorldY = entity.getWorldY() + entity.getHitBox().y + entity.getHitBox().height;

        int entityLeftCol = entityLeftWorldX / gameScene.getTileSize();
        int entityRightCol = entityRightWorldX / gameScene.getTileSize();
        int entityTopRow = entityTopWorldY / gameScene.getTileSize();
        int entityBottomRow = entityBottomWorldY / gameScene.getTileSize();

        int tileNum1, tileNum2;

        System.out.println(entity.getDirection());
        switch (entity.getDirection()) {
            case "UP":
                entityTopRow = (entityTopWorldY - entity.getSpeed()) / gameScene.getTileSize();
                tileNum1 = gameScene.getMap().getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gameScene.getMap().getMapTileNum()[entityRightCol][entityTopRow];

                break;
            case "DOWN":
                entityBottomRow = (entityBottomWorldY + entity.getSpeed()) / gameScene.getTileSize();
                tileNum1 = gameScene.getMap().getMapTileNum()[entityLeftCol][entityBottomRow];
                tileNum2 = gameScene.getMap().getMapTileNum()[entityRightCol][entityBottomRow];

                break;
            case "LEFT":
                entityLeftCol = (entityLeftWorldX - entity.getSpeed()) / gameScene.getTileSize();
                tileNum1 = gameScene.getMap().getMapTileNum()[entityLeftCol][entityTopRow];
                tileNum2 = gameScene.getMap().getMapTileNum()[entityLeftCol][entityBottomRow];

                break;
            case "RIGHT":
                entityRightCol = (entityRightWorldX + entity.getSpeed()) / gameScene.getTileSize();
                tileNum1 = gameScene.getMap().getMapTileNum()[entityRightCol][entityTopRow];
                tileNum2 = gameScene.getMap().getMapTileNum()[entityRightCol][entityBottomRow];
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + entity.getDirection());
        }

        handleCollision(entity, tileNum1, tileNum2);
        process.run();
    }

    private void handleCollision(Entity entity, int tileNum1, int tileNum2) {
        if (checkTile(tileNum1, tileNum2, TileType.Wall)) {
            entity.setCollision(true);
            entity.setFlagUpdate(false);
        } else if (checkTile(tileNum1, tileNum2, TileType.Water)) {
            handleCollisionWater(entity);
            entity.setFlagUpdate(false);
        } else if (checkTile(tileNum1, tileNum2, TileType.FinishLine)) {
            gameScene.winMaze();
        } else if (checkTile(tileNum1, tileNum2, TileType.Hole)) {
//            gameScene.loseMaze();
        }
    }

    private boolean checkTile(int tileNum1, int tileNum2, TileType tileType) {
        return gameScene.getMap().getTiles()[tileNum1].getType() == tileType || gameScene.getMap().getTiles()[tileNum2].getType() == tileType;
    }

    /*
     * Handle collision with water
     */
    private void handleCollisionWater(Entity entity) {
        gameScene.getPlayer().setDefaultPosition();

        gameScene.getPlayerMP().updatePlayerInServer();
    }

    public void checkCollision(Entity entity, Entity[] entities) {
        // Check if the entity collides with any other entity
        for (Entity otherEntity : entities) {

            if (entity.getHitBox().intersects(otherEntity.getHitBox())) {
                entity.setCollision(true);
            }

        }
    }
}
