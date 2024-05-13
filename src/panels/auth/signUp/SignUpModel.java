package panels.auth.signUp;

import network.client.Client;
import network.client.Protocol;
import panels.auth.AuthAction;
import panels.auth.AuthHandler;

public class SignUpModel extends AuthAction {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private Client client;

    public SignUpModel() {
        client = Client.getGameClient();
    }

    /**
     * Creates a new account
     */
    public void signUp(Runnable onSuccess) {

        try {
            if (!password.equals(confirmPassword)) {
                throw new Exception("Passwords do not match");
            }
            String loginRequest = "Register," + username + "," + confirmPassword + "," + email;

            AuthHandler authHandler = new AuthHandler(onSuccess);
            authHandler.start();

            // Send login request to server
            authHandler.sendToServer(loginRequest);

        } catch (Exception e) {
            // Handle IOException
            e.printStackTrace();
        }

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
