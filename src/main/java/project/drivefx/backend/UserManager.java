package project.drivefx.backend;
import java.io.*;
import java.net.*;
import org.json.JSONObject;

public class UserManager {
    final String email, homeDir;
    String currDir;

    final static String awsLoginInfoAPI =
            "https://xdrp5nonz8.execute-api.us-east-2.amazonaws.com/default/getUserLoginInfo";

    /**
     * FOR DEBUG AND TESTING ONLY
     * @param args
     */
    public static void main(String[] args) {
        try {
            UserManager user = UserManager.login("tommyvo0406@gmail.com", "Aavn!12345");
            System.out.println(user.toString());
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public UserManager(String email, String homeDir) {
        this.email = email;
        this.homeDir = homeDir;
        currDir = homeDir;
    }

    public String getEmail() {
        return email;
    }

    public String getHomeDir() {
        return homeDir;
    }

    /**
     * Login using user's email and password. If credentials are good, return. If credentials are bad,
     * throw error.
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    public static UserManager login(String email, String password) throws Exception {
        HttpURLConnection conn = sendLoginInfo(email, password);
        return getUserVerification(conn);
    }

    /**
     * Send user's login info to AWS API.
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    private static HttpURLConnection sendLoginInfo(String email, String password) throws Exception {
        URI uri = new URI(awsLoginInfoAPI);

        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        JSONObject jsonLoginInfo = new JSONObject();
        jsonLoginInfo.put("email", email);
        jsonLoginInfo.put("password", password);

        OutputStream os = connection.getOutputStream();
        os.write(jsonLoginInfo.toString().getBytes());
        os.close();

        return connection;
    }

    /**
     * Receive AWS API's respons accordingly
     * @param conn
     * @return
     * @throws Exception
     */
    private static UserManager getUserVerification(HttpURLConnection conn) throws Exception {
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // get info response into a JSON object
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            JSONObject jsonLoginInfo = new JSONObject(response.toString());

            // return new user manager

            String email = jsonLoginInfo.getString("email");
            String homeDir = jsonLoginInfo.getString("homeDir");
            return new UserManager(email, homeDir);
        }

        // Error handling

        else if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
            throw new Exception("Login Credentials are incorrect");
        } else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
            throw new Exception("Internal Server Error");
        }
        else {
            throw new Exception("Unexpected response code: " + responseCode);
        }
    }

    public String toString() {
        return String.format("Email: %s, HomeDir: %s\n", email, currDir);
    }
}
