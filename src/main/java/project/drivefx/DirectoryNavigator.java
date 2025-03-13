package project.drivefx;
import java.io.*;
import java.net.*;
import java.util.HashSet;

import org.json.JSONObject;


public class DirectoryNavigator {
    UserManager user;
    final static String awsDirTreeAPI = "https://99xoiya6k2.execute-api.us-east-2.amazonaws.com/default/getDirectoryTree";

    public static void main(String[] args) {
        try {
            UserManager user = UserManager.login("tommyvo0406@gmail.com", "Aavn!12345");
            System.out.println(user.toString());

            URI uri = new URI(awsDirTreeAPI);
            URL url = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("homeDir", user.getHomeDir());
            OutputStream os = conn.getOutputStream();
            os.write(json.toString().getBytes());
            os.close();

            System.out.println("JSON sent");

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = br.readLine()) != null) {
                    content.append(inputLine);
                }
                br.close();
                System.out.println(content.toString());
            }
            else {
                System.out.println("Connection failed");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

class DirectoryTree {
    class Node {
        String name;
        Node parent;
        HashSet<Node> children;
    }
}