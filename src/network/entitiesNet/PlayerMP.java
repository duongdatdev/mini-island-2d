package network.entitiesNet;

import objects.entities.Player;

public class PlayerMP {
    private Player player;
    private int x, y;
    private int id;
    public PlayerMP(Player player) {
        this.player = player;
        this.x = player.getWorldX();
        this.y = player.getWorldY();
        this.id = player.getId();
    }


}
