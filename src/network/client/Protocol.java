package network.client;

public class Protocol {


    /**
     * Creates a new instance of Protocol
     */
    private String message = "";

    public Protocol() {

    }

    public String UpdatePacket(String username,int x, int y, int id, int dir) {
        message = "Update,"+ username+ "," + x + "," + y + "," + dir + "," + id;
        return message;
    }

    public String chatPacket(String message) {
        message = "Chat" + message ;
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