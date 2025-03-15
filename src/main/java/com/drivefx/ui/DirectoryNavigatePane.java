package com.drivefx.ui;

import com.drivefx.authentication.AuthenticationService;
import com.drivefx.storage.FileSystemManager;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.nio.file.FileSystem;

public class DirectoryNavigatePane extends TilePane {
    private FileSystemManager manager;

    public DirectoryNavigatePane(AuthenticationService user) throws Exception {
        super();
        this.manager = FileSystemManager.getUserDirectoryManager(user);
    }
}
