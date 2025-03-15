package com.drivefx.storage;

import org.json.JSONArray;
import org.json.JSONObject;
import com.drivefx.network.APIHandler;
import com.drivefx.authentication.AuthenticationService;


public class FileSystemManager {
    final static String awsDirTreeAPI = "https://99xoiya6k2.execute-api.us-east-2.amazonaws.com/default/getDirectoryTree";

    final FileSystemTree fileSystemTree;
    final FileSystemStack fileSystemStack;

    /**
     * USED ONLY FOR DEBUGGING AND TESTING
     * @param args NO NEED
     */
    public static void main(String[] args) {
        try {
            FileSystemManager fileSystemManager = getUserDirectoryTreeJSON(
                    new AuthenticationService("tommyvo0406@gmail.com", "tommyvo0406@gmail.com/")
            );
            fileSystemManager.printDirectoryTree();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public FileSystemManager(FileSystemTree fileSystemTree, FileSystemStack fileSystemStack) {
        this.fileSystemTree = fileSystemTree;
        this.fileSystemStack = fileSystemStack;
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
    public static FileSystemManager getUserDirectoryTreeJSON (AuthenticationService usr) throws Exception {
        JSONObject json = APIHandler.getServerResponse(
                APIHandler.sendHttpRequest(usr, awsDirTreeAPI, "POST")
        );
        FileSystemTree tree = getDirectoryTree(json);
        // TODO: change init after definition
        FileSystemStack stack = new FileSystemStack(tree.root());

        return new FileSystemManager(tree, stack);
    }

    /**
     * Convert a JSON file into a tree
     * @param obj obj returned from AWS, a directory tree with the recursion form {'name': name, 'child': [obj]}
     * @return the directory tree based on obj
     * @throws Exception getJSONArray or getJSONObj not working
     */
    private static FileSystemTree getDirectoryTree (JSONObject obj) throws Exception {
        return new FileSystemTree(getDirectorySubtree(obj));
    }
    private static FileSystemNode getDirectorySubtree (JSONObject obj) throws Exception {
        JSONArray childrenArray = obj.getJSONArray("children");
        String name = obj.getString("name");
        FileSystemNode fileSystemNode = new FileSystemNode(name);

        if (childrenArray.length() > 0) {
            for (int i = 0; i < childrenArray.length(); i++) {
                fileSystemNode.children.add(getDirectorySubtree(childrenArray.getJSONObject(i)));
            }
        }

        return fileSystemNode;
    }
}

