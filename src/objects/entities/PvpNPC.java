package objects.entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class PvpNPC extends NPC{
    public PvpNPC(int worldX, int worldY) {
        super("PVP",worldX, worldY, null);
        try {
            this.sprite = ImageIO.read(getClass().getResource("/Maps/Pvp/PvpNPC.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
