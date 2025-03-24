package com.drivefx.authentication;
import com.drivefx.State;
import org.json.JSONObject;
import com.drivefx.network.APIHandler;
import com.drivefx.network.Jsonable;

public record AuthenticationService(String email, String homeDir) implements Jsonable {

    /**
     * FOR DEBUG AND TESTING ONLY
     *
     * @param args no args necessary
     */
    public static void main(String[] args) {
        try {
            AuthenticationService user = AuthenticationService.login("tommyvo0406@gmail.com", "Aavn!12345");
            System.out.println(user.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Login using user's email and password. If credentials are good, return. If credentials are bad,
     * throw error.
     *
     * @param email email
     * @param password password
     * @return AuthenticationService that has email and root dir
     * @throws Exception if API Handler throws error (Web error mostly)
     */
    public static AuthenticationService login(String email, String password) throws Exception {
        JSONObject json = APIHandler.getServerResponse(
                APIHandler.sendHttpRequest(new LoginInfo(email, password), State.awsLoginAPI, "POST")
        );
        String finalEmail = json.getString("email");
        String homeDir = json.getString("homeDir");
        return new AuthenticationService(finalEmail, homeDir);
    }

    // mundanes
    public String toString() {
        return String.format("Email: %s, HomeDir: %s\n", email, homeDir);
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("email", email);
        json.put("homeDir", homeDir);
        return json;
    }

    /**
     * Used to parse Jsonable object into APIHandler's methods
     * @param email    email of user
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
