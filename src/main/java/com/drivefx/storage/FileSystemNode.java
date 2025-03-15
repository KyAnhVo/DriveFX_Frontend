package com.drivefx.storage;

import java.util.ArrayList;
import java.util.List;

class FileSystemNode {
    String name;
    final List<FileSystemNode> children;
    FileSystemNode parent;
    final boolean isDirectory;

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
