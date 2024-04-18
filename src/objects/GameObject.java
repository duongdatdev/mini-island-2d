package objects;

import main.GameScene;

import java.awt.*;

public abstract class GameObject {

    public int x, y, width, height;

    public GameScene gameScene;

    /**
     * Updates game logic of the game object.
     *
     * @param delta how much seconds passed from the update
     */
    public abstract void update(float delta);

    /**
     * Renders game object on the screen.
     *
     * @param graphics Graphics object used for custom game object rendering.
     */
    public abstract void render(Graphics graphics);
}
