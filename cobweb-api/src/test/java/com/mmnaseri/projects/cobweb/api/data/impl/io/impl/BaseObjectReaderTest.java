package com.mmnaseri.projects.cobweb.api.data.impl.io.impl;

import com.google.common.jimfs.Jimfs;
import com.mmnaseri.projects.cobweb.api.common.FileSystemUtils;
import com.mmnaseri.projects.cobweb.api.data.impl.io.ObjectReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 6:00 PM)
 */
public abstract class BaseObjectReaderTest {

    private FileSystem fileSystem;

    @BeforeMethod
    public void setUp() throws Exception {
        fileSystem = Jimfs.newFileSystem();
        final Path path = FileSystemUtils.getAbsolutePath(fileSystem, "tmp");
        Files.createDirectory(path);
    }

    @Test
    public void testReadingAnObject() throws Exception {
        final Path path = FileSystemUtils.getAbsolutePath(fileSystem, "tmp", "file.bin");
        final String written = UUID.randomUUID().toString();
        write(path, written);
        final ObjectReader<String> reader = getObjectReader();
        final String read = reader.read(path, String.class);
        assertThat(read, is(written));
    }

    protected abstract ObjectReader<String> getObjectReader();

    protected abstract void write(Path path, String written) throws IOException;

}
