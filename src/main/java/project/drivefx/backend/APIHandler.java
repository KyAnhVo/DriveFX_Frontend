package project.drivefx.backend;
import java.io.*;
import java.net.*;
import org.json.JSONObject;

public class APIHandler {

    /**
     * Get response from conn in the form of a JSONObject.
     * @param conn
     * @return
     * @throws Exception
     */
    public static JSONObject getJSONObject (HttpURLConnection conn) throws Exception {
        int responseCode = conn.getResponseCode();

        switch (responseCode) {
            case HttpURLConnection.HTTP_OK:
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = reader.readLine()) != null) {
                    content.append(inputLine);
                }
                reader.close();
                return new JSONObject(content.toString());
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                throw new SecurityException("Login credentials incorrect");
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                throw new RuntimeException("Server error");
            default:
                throw new Exception("Unhandled Error: " + responseCode);
        }
    }

    /**
     * Set up connection to a website with provided endpoint, method, and doOutput boolean.
     * @param apiEndpoint
     * @param httpMethod
     * @param doOutput
     * @return
     */
    public static HttpURLConnection setupConnection(String apiEndpoint, String httpMethod, boolean doOutput) throws Exception {
        URI uri = new URI(apiEndpoint);
        URL url = uri.toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(httpMethod);
        conn.setDoOutput(doOutput);
        conn.setRequestProperty("Content-Type", "application/json");

        return conn;
    }

    /**
     * Send the JSONObject to the api established by conn
     * @param conn
     * @param jsonPackage
     */
    public static void sendJSONObject(HttpURLConnection conn, JSONObject jsonPackage) throws Exception {
        OutputStream os = conn.getOutputStream();
        os.write(jsonPackage.toString().getBytes());
        os.close();
    }
}
