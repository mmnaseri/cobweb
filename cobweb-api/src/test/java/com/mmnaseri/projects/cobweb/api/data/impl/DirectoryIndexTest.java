package com.mmnaseri.projects.cobweb.api.data.impl;

import com.google.common.jimfs.Jimfs;
import com.mmnaseri.projects.cobweb.api.data.impl.io.impl.BinaryObjectInputOutputManager;
import org.testng.annotations.BeforeMethod;

import java.io.Serializable;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.UUID;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 5:26 PM)
 */
public class DirectoryIndexTest {

    private FileSystem fileSystem;
    private DirectoryIndex<UUID, Document> index;

    @BeforeMethod
    public void setUp() throws Exception {
        fileSystem = Jimfs.newFileSystem();
        final Path root = fileSystem.getPath("data", "index");
        index = new DirectoryIndex<>(root, new BinaryObjectInputOutputManager());
    }

    private static class Document implements Serializable {

        private static final long serialVersionUID = 4074301356394146508L;

    }

}