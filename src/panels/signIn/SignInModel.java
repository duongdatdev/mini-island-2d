package panels.signIn;

public class SignInModel {
    private int id;
    private String username;
    private String password;

    public SignInModel() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean signIn() {


        return true;
    }

    public boolean signUp() {
        return true;
    }
}
