package network.client;

public class Protocol {


    /**
     * Creates a new instance of Protocol
     */
    private String message = "";

    public Protocol() {

    }

    public String UpdatePacket(String username, int x, int y, int dir) {
        message = "Update," + username + "," + x + "," + y + "," + dir;
        return message;
    }

    public String chatPacket(String username, String message) {
        message = "Chat," + username + "," + message;
        return message;
    }

    public String RegisterPacket(String username, String password, String email) {
        message = "Register" + username + "," + password + "-" + email;
        return message;
    }

    public String LoginPacket(String username, String password) {
        message = "Login" + username + "," + password;
        return message;
    }

    public String HelloPacket(String username) {
        message = "Hello" + username;
        return message;
    }

    public String ShotPacket(String username) {
        message = "Shot" + username;
        return message;
    }

    public String teleportPacket(String username, String map, int x, int y) {
        message = "TeleportToMap," + username + "," + map + "," + x + "," + y;
        return message;
    }

   public String enterMazePacket(String username) {
        message = "EnterMaze" + username;
        return message;
    }

    public String winMazePacket(String username) {
        message = "WinMaze" + username;
        return message;
    }

    public String PlayerExitMapPacket(String username, String map) {
        message = "ExitMap" + username + "," + map;
        return message;
    }

    public String RemoveClientPacket(String username) {
        message = "Remove" + username;
        return message;
    }

    public String ExitMessagePacket(String username) {
        message = "Exit" + username;
        return message;
    }
}