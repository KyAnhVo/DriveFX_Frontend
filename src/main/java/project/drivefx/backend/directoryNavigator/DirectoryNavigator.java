package project.drivefx.backend.directoryNavigator;

import org.json.JSONArray;
import org.json.JSONObject;
import project.drivefx.backend.apiHandler.APIHandler;
import project.drivefx.backend.login.LoginManager;


public class DirectoryNavigator {
    LoginManager user;
    final static String awsDirTreeAPI = "https://99xoiya6k2.execute-api.us-east-2.amazonaws.com/default/getDirectoryTree";

    final DirectoryTree directoryTree;
    final DirectoryStack directoryStack;

    /**
     * USED ONLY FOR DEBUGGING AND TESTING
     * @param args
     */
    public static void main(String[] args) {
        try {
            DirectoryNavigator directoryNavigator = getUserDirectoryTreeJSON(
                    new LoginManager("tommyvo0406@gmail.com", "tommyvo0406@gmail.com/")
            );
            directoryNavigator.printDirectoryTree();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public DirectoryNavigator(DirectoryTree directoryTree, DirectoryStack directoryStack) {
        this.directoryTree = directoryTree;
        this.directoryStack = directoryStack;
    }

    public void printDirectoryTree() {
        System.out.println(this.directoryTree.toString());
    }

    /**
     * Get user directory tree in JSON Object
     * @param usr
     * @return
     * @throws Exception
     */
    public static DirectoryNavigator getUserDirectoryTreeJSON (LoginManager usr) throws Exception {
        JSONObject json = APIHandler.getServerResponse(
                APIHandler.sendHttpRequest(usr, awsDirTreeAPI, "POST")
        );
        DirectoryTree tree = getDirectoryTree(json);
        // TODO: change init after definition
        DirectoryStack stack = new DirectoryStack();

        return new DirectoryNavigator(tree, stack);
    }

    /**
     * Convert a JSON file into a tree
     * @param obj obj returned from AWS, a directory tree with the recursion form {'name': name, 'child': [obj]}
     * @return the directory tree based on obj
     * @throws Exception getJSONArray or getJSONObj not working
     */
    private static DirectoryTree getDirectoryTree (JSONObject obj) throws Exception {
        return new DirectoryTree(getDirectorySubtree(obj));
    }
    private static Node getDirectorySubtree (JSONObject obj) throws Exception {
        JSONArray childrenArray = obj.getJSONArray("children");
        String name = obj.getString("name");
        Node node = new Node(name);

        if (childrenArray.length() > 0) {
            for (int i = 0; i < childrenArray.length(); i++) {
                node.children.add(getDirectorySubtree(childrenArray.getJSONObject(i)));
            }
        }

        return node;
    }
}

