package com.drivefx.network;
import java.io.*;
import java.net.*;

import com.drivefx.State;
import org.json.JSONObject;

public class APIHandler {

    /**
     * Send http request with given data
     * @param object object that can be turned into JSON
     * @param url api/url of endpoint
     * @param requestMethod HTTP Request (GET, POST, ...)
     * @return connection
     * @throws Exception sending request error (HTTP error)
     */
    public static HttpURLConnection sendHttpRequest(Jsonable object, String url, String requestMethod) throws Exception {
        HttpURLConnection conn = setupConnection(url, requestMethod);
        JSONObject request = object.toJSON();
        sendRequest(conn, request);
        return conn;
    }

    /**
     * Get response from conn in the form of a JSONObject.
     * @param conn
     * @return
     * @throws Exception
     */
    public static JSONObject getServerResponse(HttpURLConnection conn) throws Exception {
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
     * @param apiEndpoint URL of the server that is to be communicated
     * @param httpMethod HTTP Method: GET, POST, PUT, etc.
     * @return
     */
    private static HttpURLConnection setupConnection(String apiEndpoint, String httpMethod) throws Exception {
        URI uri = new URI(apiEndpoint);
        URL url = uri.toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(httpMethod);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        return conn;
    }

    /**
     * Send the JSONObject to the api established by conn
     * @param conn
     * @param jsonPackage
     */
    private static void sendRequest(HttpURLConnection conn, JSONObject jsonPackage) throws IOException {
        OutputStream os = conn.getOutputStream();
        os.write(jsonPackage.toString().getBytes());
        os.close();
    }

    public static String getPresignedURLDownload(String filename, String functionUrl) throws Exception {
        String result = "";

        HttpURLConnection conn = APIHandler.setupConnection(functionUrl, "GET");

        JSONObject jsonPackage = new JSONObject();
        jsonPackage.put("filename", filename);

        APIHandler.sendRequest(conn, jsonPackage);

        JSONObject response = new JSONObject(APIHandler.getServerResponse(conn));
        result = response.getString("url");


        return result;
    }

    /**
     * Download file from a URL to a dest path
     * @param presignedUrl presigned url of the file waiting to be downloaded
     * @param name path, relative to project file (DriveFX)
     */
    public static void downloadFile(String presignedUrl, String name) throws Exception {
        URI uri = new URI(presignedUrl);
        HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        switch (responseCode) {
            case HttpURLConnection.HTTP_OK:
                InputStream inpStream = conn.getInputStream();
                FileOutputStream outStream = new FileOutputStream(new File(
                        State.tempFilesDirPath + name));

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inpStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }

                outStream.flush();
                outStream.close();
                inpStream.close();
                break;

            case HttpURLConnection.HTTP_NOT_FOUND:
                throw new FileNotFoundException("File not found");

            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                throw new RuntimeException("Server error");

            case HttpURLConnection.HTTP_UNAUTHORIZED:
                throw new RuntimeException("Login credentials incorrect");

            default:
                throw new Exception("Unhandled Error: " + responseCode);
        }
    }
}
