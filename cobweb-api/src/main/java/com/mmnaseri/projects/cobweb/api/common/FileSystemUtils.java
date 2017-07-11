package com.mmnaseri.projects.cobweb.api.common;

import java.nio.file.FileSystem;
import java.nio.file.Path;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:33 PM)
 */
public final class FileSystemUtils {

    private FileSystemUtils() {
        throw new UnsupportedOperationException();
    }

    public static Path getAbsolutePath(FileSystem fileSystem, Path relativeToRoot) {
        return fileSystem.getRootDirectories().iterator().next().resolve(relativeToRoot);
    }

    public static Path getAbsolutePath(FileSystem fileSystem, String first, String... rest) {
        return fileSystem.getRootDirectories().iterator().next().resolve(
                fileSystem.getPath(first, rest)
        );
    }

}
