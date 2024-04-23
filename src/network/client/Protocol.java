package network.client;

public class Protocol {


    /**
     * Creates a new instance of Protocol
     */
    private String message = "";

    public Protocol() {

    }

    public String RegisterPacket(int x, int y) {
        message = "Hello" + x + "," + y;
        return message;
    }

    public String UpdatePacket(int x, int y, int id, int dir) {
        System.out.println("Update" + x + "," + y + "-" + dir + "|" + id);
        message = "Update" + x + "," + y + "-" + dir + "|" + id;
        return message;
    }

    public String LoginPacket(String username, String password,int x,int y) {
        message = "Login" + username + "," + password + "-" + x + "|" + y;
        return message;
    }

    public String ShotPacket(int id) {
        message = "Shot" + id;
        return message;
    }

    public String RemoveClientPacket(int id) {
        message = "Remove" + id;
        return message;
    }

    public String ExitMessagePacket(int id) {
        message = "Exit" + id;
        return message;
    }
}