package mapGenerate;

public enum TileTypeNum {
    GRASS(0),
    WATER(1),
    DIRT(2),
    WALL(3),
    NULL(4);

    private int num;

    TileTypeNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

}
