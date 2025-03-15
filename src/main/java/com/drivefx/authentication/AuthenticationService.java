package com.drivefx.authentication;
import org.json.JSONObject;
import com.drivefx.network.APIHandler;
import com.drivefx.network.Jsonable;

public class AuthenticationService implements Jsonable {
    final String email, homeDir;
    String currDir;

    final static String awsLoginInfoAPI =
            "https://xdrp5nonz8.execute-api.us-east-2.amazonaws.com/default/getUserLoginInfo";

    /**
     * FOR DEBUG AND TESTING ONLY
     * @param args no args necessary
     */
    public static void main(String[] args) {
        try {
            AuthenticationService user = AuthenticationService.login("tommyvo0406@gmail.com", "Aavn!12345");
            System.out.println(user.toString());
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public AuthenticationService(String email, String homeDir) {
        this.email = email;
        this.homeDir = homeDir;
        currDir = homeDir;
    }

    public String getEmail() { return email; }

    public String getHomeDir() { return homeDir; }

    /**
     * Login using user's email and password. If credentials are good, return. If credentials are bad,
     * throw error.
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    public static AuthenticationService login(String email, String password) throws Exception {
        JSONObject json = APIHandler.getServerResponse(
                APIHandler.sendHttpRequest(new LoginInfo(email, password), awsLoginInfoAPI, "POST")
        );
        String finalEmail = json.getString("email");
        String homeDir = json.getString("homeDir");
        return new AuthenticationService(finalEmail, homeDir);
    }

    // mundanes
    public String toString() {
        return String.format("Email: %s, HomeDir: %s\n", email, currDir);
    }
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("email", email);
        json.put("homeDir", homeDir);
        return json;
    }

    /**
     * Used to parse Jsonable object into APIHandler's methods
     * @param email email of user
     * @param password password of user
     */
    protected record LoginInfo(String email, String password) implements Jsonable {
        @Override
        public JSONObject toJSON() {
            JSONObject json = new JSONObject();
            json.put("email", this.email);
            json.put("password", this.password);
            return json;
        }
    }
}
