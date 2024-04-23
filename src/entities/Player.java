package entities;

import imageRender.ImageHandler;
import input.KeyHandler;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    private int screenX;
    private int screenY;
    private int scale = 1;
    private KeyHandler keyHandler;
    private int id;
    private String direction = "DOWN";
    private int spriteIndex = 0;
    private int countFrames = 0;
    private BufferedImage[] standingImages;
    private GamePanel gamePanel;

    public Player(GamePanel gamePanel,KeyHandler keyHandler) {
        super();
        this.keyHandler = keyHandler;
        this.gamePanel = gamePanel;

        hitBox = new Rectangle();
        hitBox.width = gamePanel.getTileSize() - 16;
        hitBox.height = gamePanel.getTileSize() - 16;
        hitBox.x = 8;
        hitBox.y = 16;

        setDefaultPosition();
        setDefaultSpeed();
        loadAssets();
    }

    public void loadAssets() {
        upImages = ImageHandler.loadAssets("/player/Character_Up.png", 32, 32);
        downImages = ImageHandler.loadAssets("/player/Character_Down.png", 32, 32);
        leftImages = ImageHandler.loadAssets("/player/Character_Left.png", 32, 32);
        rightImages = ImageHandler.loadAssets("/player/Character_Right.png", 32, 32);
        standingImages = ImageHandler.loadAssets("/player/Character_Stand.png", 32, 32);
    }

    private void setDefaultPosition() {
        screenX = gamePanel.getScreenWidth() / 2 - gamePanel.getTileSize()*scale / 2;
        screenY = gamePanel.getScreenHeight() / 2 - gamePanel.getTileSize()*scale / 2;
        worldX = 1000;
        worldY = 1000;
    }

    private void setDefaultSpeed() {
        speed = 5;
    }

    public void update() {
        countFrames++;
        updatePosition();
    }
    private void updatePosition(){
        if (isMove()) {

            if (keyHandler.isUp()) {
                direction = "UP";
                worldY -= speed;
            }
            if (keyHandler.isDown()) {
                direction = "DOWN";
                worldY += speed;
            }
            if (keyHandler.isLeft()) {
                direction = "LEFT";
                worldX -= speed;
            }
            if (keyHandler.isRight()) {
                direction = "RIGHT";
                worldX += speed;
            }
        } else {
            direction = "STAND";
        }
        if (countFrames > 11) {
            spriteIndex++;
            if (spriteIndex > 3) {
                spriteIndex = 0;
            }
            countFrames = 0;
        }
    }

    public void draw(Graphics2D g2d, int tileSize) {
        BufferedImage playerImage = null;
        switch (direction) {
            case "UP":
                if (spriteIndex == 0) {
                    playerImage = upImages[0];
                }
                if (spriteIndex == 1) {
                    playerImage = upImages[1];
                }
                if (spriteIndex == 2) {
                    playerImage = upImages[2];
                }
                if (spriteIndex == 3) {
                    playerImage = upImages[3];
                }
                break;
            case "DOWN":
                if (spriteIndex == 0) {
                    playerImage = downImages[0];
                }
                if (spriteIndex == 1) {
                    playerImage = downImages[1];
                }
                if (spriteIndex == 2) {
                    playerImage = downImages[2];
                }
                if (spriteIndex == 3) {
                    playerImage = downImages[3];
                }
                break;
            case "LEFT":
                if (spriteIndex == 0) {
                    playerImage = leftImages[0];
                }
                if (spriteIndex == 1) {
                    playerImage = leftImages[1];
                }
                if (spriteIndex == 2) {
                    playerImage = leftImages[2];
                }
                if (spriteIndex == 3) {
                    playerImage = leftImages[3];
                }
                break;
            case "RIGHT":
                if (spriteIndex == 0) {
                    playerImage = rightImages[0];
                }
                if (spriteIndex == 1) {
                    playerImage = rightImages[1];
                }
                if (spriteIndex == 2) {
                    playerImage = rightImages[2];
                }
                if (spriteIndex == 3) {
                    playerImage = rightImages[3];
                }
                break;
            case "STAND":
                if (spriteIndex == 0) {
                    playerImage = standingImages[0];
                }
                if (spriteIndex == 1) {
                    playerImage = standingImages[1];
                }
                if (spriteIndex == 2) {
                    playerImage = standingImages[2];
                }
                if (spriteIndex == 3) {
                    playerImage = standingImages[3];
                }
                break;
        }
        g2d.drawImage(playerImage, screenX, screenY, tileSize*scale, tileSize*scale, null);
    }

    public boolean isMove() {
        return (keyHandler.isUp() || keyHandler.isDown() || keyHandler.isLeft() || keyHandler.isRight());
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    @Override
    public int getWorldX() {
        return super.getWorldX();
    }

    @Override
    public void setWorldX(int worldX) {
        super.setWorldX(worldX);
    }

    @Override
    public int getWorldY() {
        return super.getWorldY();
    }

    @Override
    public void setWorldY(int worldY) {
        super.setWorldY(worldY);
    }

    @Override
    public int getSpeed() {
        return super.getSpeed();
    }

    @Override
    public void setSpeed(int speed) {
        super.setSpeed(speed);
    }

}
//import java.awt.*;
//import java.util.*;
//import java.util.List;

/**
 * Created on 24.2.2017.
 */
//public class Player extends Entity {
//
//    private final int SPEED = 60;
//
////    public Inventory inventory = new Backpack(this, 530, 280, 8);
////    public CraftingInventory craftingInventory = new CraftingInventory(this, 20, 150);
////    public HotBarInventory hotBarInventory = new HotBarInventory(this, 240, 360);
//
//    private boolean wasPressedMoveButton;
//
//    private int xStep = 0;
//    private int yStep = 0;
//
//    public Player(GameScene gameScene){
//        super(gameScene, 50, 50, newAnimation(), Textures.getTexture("dead_skeleton"));
//    }
//
//    private static EntityAnimation newAnimation(){
//        return new EntityAnimation(new Image[][]{
//                new Image[]{Textures.getTexture("player_down"), Textures.getTexture("player_down_2"),
//                        Textures.getTexture("player_down_3"), Textures.getTexture("player_down_4")},
//                new Image[]{Textures.getTexture("player_up"), Textures.getTexture("player_up_2"),
//                        Textures.getTexture("player_up_3"), Textures.getTexture("player_up_4")},
//                new Image[]{Textures.getTexture("player_left"), Textures.getTexture("player_left_2"),
//                        Textures.getTexture("player_left_3"), Textures.getTexture("player_left_4")},
//                new Image[]{Textures.getTexture("player_right"), Textures.getTexture("player_right_2"),
//                        Textures.getTexture("player_right_3"), Textures.getTexture("player_right_4")},
//        });
//    }
//
//    @Override
//    public void update(float delta) {
//        if (isDead()){
//            onPlayerDeath();
//        }
//        updatePosition(delta);
//        updatePsychiology(delta);
//        handleClick(delta);
//        handleKeys(delta);
//    }
//
//    /**
//     * Called when player dies in-game.
//     */
//    private void onPlayerDeath() {
//        gameScene.gamePanel.changeScene(new DeathScene(gameScene.gamePanel));
//    }
//
//    /**
//     * Updates health and hunger status.
//     *
//     * @param delta how much seconds passed since last game loop update
//     */
//    private void updatePsychiology(float delta) {
//        double hungerModifier;
//        if (isWounded()){
//            hungerModifier = HUNGER_DRAIN_WOUNDED;
//            if (!isHungry()) heal(delta*HUNGER_HEALING);
//        }
//        else if (isRunning()) hungerModifier =  HUNGER_DRAIN_RUNNING;
//        else if (isMoving()) hungerModifier = HUNGER_DRAIN_WALKING;
//        else hungerModifier = HUNGER_DRAIN_IDLE;
//        currentHunger -= delta*hungerModifier;
//        if (isStarving()){
//            currentHunger = 0.0d;
//            applyDamage(delta*HUNGER_DAMAGE);
//        }
//
//    }
//
//    /**
//     * Checks whether is player starving.
//     *
//     * @return whether is player starving
//     */
//    private boolean isStarving(){
//        return currentHunger <= 0.0d;
//    }
//
//    /**
//     * Checks whether is player hungry.
//     *
//     * @return whether is player hungry
//     */
//    private boolean isHungry() {
//        return currentHunger < maxHunger / 2.0d;
//    }
//
//    /**
//     * Checks whether is player trying to run.
//     *
//     * @return whether is player trying to run
//     */
//    private boolean isTryingToRun(){
//        return gameScene.keyboardListener.isPressedShift() && isMoving();
//    }
//
//    /**
//     * Checks whether is player running.
//     *
//     * @return whether is player running
//     */
//    private boolean isRunning(){
//        return isTryingToRun() && (currentHunger >= maxHunger/2d);
//    }
//
//    /**
//     * Checks whether is some move button pressed.
//     *
//     * @return whether is some move button pressed
//     */
//    private boolean isMoving(){
//        return wasPressedMoveButton;
//    }
//
//    /**
//     * Feeds player with specified amount of food.
//     *
//     * @param amount amount of food
//     */
//
//    /**
//     * Updates position of player based on pressed buttons and resolves collisions.
//     *
//     * @param delta time in seconds passed since last game loop update
//     */
//    private void updatePosition(float delta){
//        wasPressedMoveButton = false;
//        int old_position_x = x;
//        int old_position_y = y;
//
//        if (gameScene.keyboardListener.isPressed('w')){
//            wasPressedMoveButton = true;
//            move_up(delta);
//            if (hasAnyCollision()){
//                //y++;
//                y -= yStep;
//            }
//            if (!gameScene.keyboardListener.isPressed('a') && !gameScene.keyboardListener.isPressed('d')) entityAnimation.changeState(1, isRunning());
//
//        }
//        else if (gameScene.keyboardListener.isPressed('s')){
//            wasPressedMoveButton = true;
//            move_down(delta);
//            if (hasAnyCollision()){
//                //y--;
//                y -= yStep;
//            }
//            if (!gameScene.keyboardListener.isPressed('a') && !gameScene.keyboardListener.isPressed('d')) entityAnimation.changeState(0, isRunning());
//        }
//
//        if (gameScene.keyboardListener.isPressed('a')){
//            wasPressedMoveButton = true;
//            move_left(delta);
//            if (hasAnyCollision()){
//                //x++;
//                x -= xStep;
//            }
//            entityAnimation.changeState(2, isRunning());
//        }
//        else if (gameScene.keyboardListener.isPressed('d')){
//            wasPressedMoveButton = true;
//            move_right(delta);
//            if (hasAnyCollision()){
//                //x--;
//                x -= xStep;
//            }
//            entityAnimation.changeState(3, isRunning());
//        }
//
//        if (!wasPressedMoveButton){
//            entityAnimation.resetFrame();
//        }
//        else{
//            PlayerMoveEvent playerMoveEvent = new PlayerMoveEvent(this, x - old_position_x, y - old_position_y);
//            playerMoveEvent.emit();
//        }
//    }
//
//    /**
//     * Does move left.
//     *
//     * @param delta time passed since last game loop update
//     */
//    private void move_left(float delta){
//        xPosAcc -= isRunning() ? delta*(SPEED+40) : delta*SPEED;
//        xStep = (int) xPosAcc;
//        xPosAcc -= xStep;
//        x += xStep;
//        /*if (xPosAcc < -1){
//            xPosAcc = (xPosAcc + 1) % 1f;
//            x--;
//        }
//        else if (xPosAcc > 1) xPosAcc = 0;*/
//    }
//
//    /**
//     * Does move right.
//     *
//     * @param delta time passed since last game loop update
//     */
//    private void move_right(float delta){
//        xPosAcc += isRunning() ? delta*(SPEED+40) : delta*SPEED;
//        xStep = (int) xPosAcc;
//        xPosAcc -= xStep;
//        x += xStep;
//        /*if (xPosAcc > 1){
//            xPosAcc = (xPosAcc - 1) % 1f;
//            x++;
//        }
//        else if (xPosAcc < -1) xPosAcc = 0;*/
//    }
//
//    /**
//     * Does move up.
//     *
//     * @param delta time passed since last game loop update
//     */
//    private void move_up(float delta){
//        yPosAcc -= isRunning() ? delta*(SPEED+40) : delta*SPEED;
//        yStep = (int) yPosAcc;
//        yPosAcc -= yStep;
//        y += yStep;
//        /*if (yPosAcc < -1){
//            yPosAcc = (yPosAcc + 1) % 1f;
//            y--;
//        }
//        else if (yPosAcc > 1) yPosAcc = 0;*/
//    }
//
//    /**
//     * Does move down.
//     *
//     * @param delta time passed since last game loop update
//     */
//    private void move_down(float delta){
//        yPosAcc += isRunning() ? delta*(SPEED+40) : delta*SPEED;
//        yStep = (int) yPosAcc;
//        yPosAcc -= yStep;
//        y += yStep;
//        /*if (yPosAcc > 1){
//            yPosAcc = (yPosAcc - 1) % 1f;
//            y++;
//        }
//        else if (yPosAcc < -1) yPosAcc = 0;*/
//    }
//
//    /**
//     * Gets distance from another game object.
//     *
//     * @param obj game object
//     * @return distance from another game object
//     */
//    private double getDistanceToGameObject(GameObject obj){
//        return Math.hypot(Math.abs(x - obj.x - obj.width/2), Math.abs(y - obj.y - obj.height/2));
//    }
//
//    /**
//     * Returns whether is game object in reachable area.
//     *
//     * @param obj game object
//     * @param distance distance from mouse
//     * @return whether is game object in reachable area
//     */
//    private boolean isReacheable(GameObject obj, double distance) {
//        return getDistanceToGameObject(obj) <= distance;
//    }
//
//    /**
//     * Returns whether is game object reachable within default distance.
//     *
//     * @param obj game object
//     * @return whether is game object reachable within default distance
//     */
//    private boolean isReacheable(GameObject obj) {
//        return isReacheable(obj, 50.0d);
//    }
//
//    /**
//     * Gets list of all reachable objects within specified distance.
//     *
//     * @param distance max distance from mouse
//     * @return list of all reachable objects within specified distance
//     */
////    private List<GameObject> getNearbyGameObjects(double distance){
////        List<GameObject> result = new ArrayList<>();
////        for (Chunk chunk: GameScene.active){
////            for (GameObject obj: chunk.collidables){
////                if (isReacheable(obj, distance)) result.add(obj);
////            }
////            for (GameObject obj: chunk.nonCollidables){
////                if (isReacheable(obj, distance)) result.add(obj);
////            }
////        }
////        for (GameObject obj: gameScene.collidables){
////            if (isReacheable(obj, distance)) result.add(obj);
////        }
////        return result;
////    }
//
//    @Override
//    public void render(Graphics graphics) {
//        super.render(graphics);
//        //graphics.drawString(getTotalArmor() + "", scene.camera.recalculateX(x), scene.camera.recalculateY(y));
//    }
//
//
//    @Override
//    public int getCenterX() {
//        return x + width/2;
//    }
//
//    @Override
//    public int getCenterY() {
//        return y + height/2;
//    }
//
//    @Override
//    public int getCollidableX() {
//        return x;
//    }
//
//    @Override
//    public int getCollidableY() {
//        return y;
//    }
//
//    @Override
//    public int getCollidableWidth() {
//        return width;
//    }
//
//    @Override
//    public int getCollidableHeight() {
//        return height;
//    }
//}

