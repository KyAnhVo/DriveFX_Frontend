package project.drivefx.backend.directoryNavigator;

import java.io.IOException;

record DirectoryTree(Node root) {

    public void addFile(String name, boolean isDir) throws IOException {
        if (isDir && !name.endsWith("/"))
            throw new IOException("Directory name must end with '/'");
        if (!isDir && name.endsWith("/"))
            throw new IOException("Non-directory name must not end with '/'");

        // TODO: Implement method.
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringRecursive(root, 0, sb);
        return sb.toString();
    }

    private void toStringRecursive(Node node, int tab, StringBuilder sb) {
        sb.append("\t".repeat(Math.max(0, tab)));
        sb.append(node).append("\n");

        for (Node child : node.children) {
            toStringRecursive(child, tab + 1, sb);
        }
    }
}
