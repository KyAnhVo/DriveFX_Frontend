package com.drivefx.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class FileSystemTree {
    private final FileSystemNode home;
    private FileSystemNode currentNode;

    /**
     * Initialize a FileSystemTree, where a node is a file, and its children are nodes inside the file.
     * @param home home directory
     */
    FileSystemTree(FileSystemNode home) {
        this.home = home;
        this.currentNode = home;
    }

    FileSystemNode getHome() {
        return home;
    }

    FileSystemNode getCurrentNode() {
        return currentNode;
    }

    /**
     * Get a list of children names for the current node
     * @return List of names in String format
     */
    public List<String> getChildrenFiles() {
        ArrayList<String> childrenFileNames = new ArrayList<>();
        for (FileSystemNode child : currentNode.getChildren()) {
            childrenFileNames.add(child.getName());
        }
        return childrenFileNames;
    }

    /**
     * cd to the stated path, or throws IOException if path does not exist. If path is empty, currentNode = home.
     * @param pathFileList the string of path aim to go to
     */
    public void cd(List<String> pathFileList) throws RuntimeException {


        if (pathFileList.getFirst().equals("~")) {
            pathFileList.removeFirst();
            cdFrom(pathFileList, home); // effectively absolute path cd
        }
        else
            cdFrom(pathFileList, currentNode); // effectively relative path cd
    }

    private void cdFrom(List<String> path, FileSystemNode node) throws RuntimeException {
        FileSystemNode cdNode = node;

        for (String pathElement : path) {
            if (pathElement.equals("..")) {
                if (cdNode == null) throw new RuntimeException();
                cdNode = cdNode.getParent();
            }
            else if (pathElement.equals("~")) {
                throw new RuntimeException("No file named '~'");
            }
            else if (!pathElement.equals(".")) {
                cdNode = cdNode.getChild(pathElement);
            }
        }

        updateCurrentNode(cdNode);
    }

    private void updateCurrentNode(FileSystemNode node) {
        if (node == null) throw new RuntimeException("File cannot be null");
        if (!node.isDirectory) throw new RuntimeException("File must be a directory");
        this.currentNode = node;
    }

    public void addFile(String name, boolean isDir) throws IOException {
        if (isDir && !name.endsWith("/"))
            throw new IOException("Directory name must end with '/'");
        if (!isDir && name.endsWith("/"))
            throw new IOException("Non-directory name must not end with '/'");

        // TODO: Implement method.
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringRecursive(home, 0, sb);
        return sb.toString();
    }

    private void toStringRecursive(FileSystemNode fileSystemNode, int tab, StringBuilder sb) {
        sb.append("\t".repeat(Math.max(0, tab)));
        sb.append(fileSystemNode).append("\n");

        for (FileSystemNode child : fileSystemNode.children) {
            toStringRecursive(child, tab + 1, sb);
        }
    }


}
