package com.drivefx.storage;

import org.json.JSONArray;
import org.json.JSONObject;
import com.drivefx.network.APIHandler;
import com.drivefx.authentication.AuthenticationService;

import java.util.ArrayList;
import java.util.List;


public class FileSystemManager {
    final static String awsDirTreeAPI = "https://99xoiya6k2.execute-api.us-east-2.amazonaws.com/default/getDirectoryTree";

    final FileSystemTree fileSystemTree;

    /**
     * USED ONLY FOR DEBUGGING AND TESTING
     * @param args NO NEED
     */
    public static void main(String[] args) {
        try {
            FileSystemManager fileSystemManager = getUserDirectoryManager(
                    new AuthenticationService("tommyvo0406@gmail.com", "tommyvo0406@gmail.com/")
            );
            fileSystemManager.printDirectoryTree();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public FileSystemManager(FileSystemTree fileSystemTree) {
        this.fileSystemTree = fileSystemTree;
    }

    public void printDirectoryTree() {
        System.out.println(this.fileSystemTree.toString());
    }

    /**
     * Get user directory tree in JSON Object
     * @param usr  should get this after user successfully logs in.
     * @return FileSystemManager that manages file stack and overall file tree
     * @throws Exception in sending request or receiving responds
     */
    public static FileSystemManager getUserDirectoryManager(AuthenticationService usr) throws Exception {
        JSONObject json = APIHandler.getServerResponse(
                APIHandler.sendHttpRequest(usr, awsDirTreeAPI, "POST")
        );
        FileSystemTree tree = createFileSystemTree(json);

        return new FileSystemManager(tree);
    }

    /**
     * Convert a JSON file into a tree
     * @param obj obj returned from AWS, a directory tree with the recursion form {'name': name, 'child': [obj]}
     * @return the directory tree based on obj
     * @throws Exception getJSONArray or getJSONObj not working
     */
    private static FileSystemTree createFileSystemTree(JSONObject obj) throws Exception {
        return new FileSystemTree(createFileSystemSubtree(obj, null));
    }

    /**
     * Create subtree with root node of that subtree returned
     * @param obj json object that has contains the root node of the current subtree
     * @param parent parent of the subtree, parent = null -> first call
     * @return root node of the subtree that the obj represents
     * @throws Exception obj is either null or does not have the form { 'name' : name, 'children' : [children]}
     */
    private static FileSystemNode createFileSystemSubtree(JSONObject obj, FileSystemNode parent) throws Exception {
        if (obj == null) throw new Exception("JSON Object is null");

        JSONArray childrenArray = obj.getJSONArray("children");
        String name;
        boolean isDirectory;

        // parent is null <=> home <=> name is "~"
        if (parent == null) name = "~/"; // to be consistent with the next isDirectory logic
        else  name = obj.getString("name");

        // name ends with '/' <=> is directory (per AWS)
        if (name.endsWith("/")) {
            isDirectory = true;
            name = name.substring(0, name.length() - 1); // remove the last '/' for all dir
        }
        else isDirectory = false;

        FileSystemNode fileSystemNode = new FileSystemNode(name, isDirectory, parent);

        for (int i = 0; i < childrenArray.length(); i++) {
            fileSystemNode.children.add(createFileSystemSubtree(childrenArray.getJSONObject(i), fileSystemNode));
        }

        return fileSystemNode;
    }

    /**
     * CD to the given path if possible.
     * @param path
     */
    public void cd(String path) {
        List<String> pathList = createPathList(path);
        fileSystemTree.cd(pathList);
    }

    private ArrayList<String> createPathList(String path) {
        StringBuilder builder = new StringBuilder();
        ArrayList<String> pathList = new ArrayList<>();

        for (char c : path.toCharArray()) {
            if (c == '/') {
                if (builder.isEmpty()) {
                    if (pathList.isEmpty()) throw new RuntimeException("Cannot access root");
                    else throw new RuntimeException("Path cannot have multiple consecutive (/) slashes");
                }
                pathList.add(builder.toString());
                builder = new StringBuilder();
            }
            else {
                builder.append(c);
            }
        }
        // Add final
        pathList.add(builder.toString());

        return pathList;
    }
}

