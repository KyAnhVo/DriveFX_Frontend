package com.drivefx.storage;

import com.drivefx.State;

import java.util.ArrayList;
import java.util.List;

public class FileSystemNode {
    String name;
    final List<FileSystemNode> children;
    FileSystemNode parent;
    public final boolean isDirectory;

    public FileSystemNode(String name, boolean isDirectory, FileSystemNode parent) {
        this.name = name;
        children = new ArrayList<>();
        this.isDirectory = isDirectory;
        this.parent = parent;
    }

    public FileSystemNode getChild(String name) throws RuntimeException {
        for (FileSystemNode child : children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        throw new RuntimeException("No such child: " + name);
    }

    public String getAwsPath() throws Exception {
        FileSystemNode currentNode = this.parent;

        // manage this file
        String awsPath = this.name;
        if (this.isDirectory) {
            awsPath += "/";
        }

        // if this is root, do not run.
        while (currentNode != null) {
            if (currentNode.isDirectory) { // parent folder, non-root
                awsPath = currentNode.getName() + "/" + awsPath;
            }
            else { // parent "file", error
                throw new RuntimeException("No such awsPath: " + awsPath);
            }
            currentNode = currentNode.parent;
        }

        // replace '~' with actual root name
        awsPath = awsPath.replaceFirst("~", State.authenticationService.homeDir());

        return awsPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setParent(FileSystemNode parent) { this.parent = parent; }

    public FileSystemNode getParent() { return parent; }

    public List<FileSystemNode> getChildren() {
        return children;
    }

    public void addChild(FileSystemNode child) {
        children.add(child);
    }

    public void removeChild(FileSystemNode child) {
        children.remove(child);
    }

    public String toString() {
        return name;
    }
}
