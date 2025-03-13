package project.drivefx.backend;
import java.io.*;
import java.net.*;
import java.util.List;

import org.json.JSONObject;


public class DirectoryNavigator {
    UserManager user;
    final static String awsDirTreeAPI = "https://99xoiya6k2.execute-api.us-east-2.amazonaws.com/default/getDirectoryTree";

    /**
     * USED ONLY FOR DEBUGGING AND TESTING
     * @param args
     */
    public static void main(String[] args) {
        try {
            long startTotalTime = System.nanoTime(); // start timer
            for (int i = 0; i < 10; i++) {
                long startTime = System.nanoTime();
                JSONObject json =
                        getUserDirectoryTreeJSON(new UserManager("tommyvo0406@gmail.com", "tommyvo0406@gmail.com/"));
                System.out.println(json.toString());
                long endTime = System.nanoTime();
                System.out.printf("Time take for this call: %d\n\n", endTime - startTime);
            }
            long endTotalTime = System.nanoTime();
            long avr = (endTotalTime - startTotalTime) / 10;
            System.out.printf("Average time for each call: %d\n", avr);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public DirectoryNavigator() {}

    /**
     * Get user directory tree in JSON Object
     * @param usr
     * @return
     * @throws Exception
     */
    public static JSONObject getUserDirectoryTreeJSON (UserManager usr) throws Exception {
        HttpURLConnection conn = sendAPIRequest(usr);
        return receiveJsonDirTree(conn);
    }

    /**
     * Sends API request based on UserManager input to AWS
     * @param usr
     * @return
     * @throws Exception
     */
    private static HttpURLConnection sendAPIRequest(UserManager usr) throws Exception{
        HttpURLConnection conn = APIHandler.setupConnection(awsDirTreeAPI, "POST", true);

        JSONObject json = new JSONObject();
        json.put("homeDir", usr.getHomeDir());

        OutputStream os = conn.getOutputStream();
        os.write(json.toString().getBytes());
        os.close();

        return conn;
    }

    /**
     * Retrieve JSON Dir Tree based on output
     * @param conn
     * @return
     * @throws Exception
     */
    private static JSONObject receiveJsonDirTree(HttpURLConnection conn) throws Exception {
        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                content.append(inputLine);
            }
            br.close();

            return new JSONObject(content.toString());
        } else {
            throw new Exception("Retreive tree failed");
        }
    }
}

class DirectoryTree {
    class Node {
        String name;
        Node parent;
        List<Node> children;
    }
}

class DirectoryStack {

}