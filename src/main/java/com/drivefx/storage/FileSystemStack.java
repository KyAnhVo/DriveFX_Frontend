package com.drivefx.storage;

import java.util.Stack;

class FileSystemStack {
    FileSystemNode homeDir;
    Stack<FileSystemNode> stack;

    public FileSystemStack(FileSystemNode homeDir) {
        this.homeDir = homeDir;
        stack = new Stack<>();
    }

    public String getCurrentAbsolutePath() {
        String currentAbsolutePath = homeDir.getName();
        for (FileSystemNode node : stack) {
            currentAbsolutePath += node.getName();
        }
        return currentAbsolutePath;
    }
}
