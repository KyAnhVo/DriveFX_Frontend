package com.drivefx.storage;

import java.util.ArrayList;
import java.util.List;

class FileSystemNode {
    String name;
    final List<FileSystemNode> children;

    public FileSystemNode(String name) {
        this.name = name;
        children = new ArrayList<>();
    }

    public FileSystemNode getChild(String name) {
        for (FileSystemNode child : children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

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
