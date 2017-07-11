package com.mmnaseri.projects.cobweb.api.data.impl.nio.impl;

import com.google.common.jimfs.Jimfs;
import com.mmnaseri.projects.cobweb.api.common.FileSystemUtils;
import com.mmnaseri.projects.cobweb.api.data.impl.nio.ObjectInputOutputManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/10/17, 6:44 PM)
 */
public abstract class BaseObjectInputOutputManagerTest<O> {

    private FileSystem fileSystem;

    protected abstract ObjectInputOutputManager<O> getManager();

    protected abstract O getSubject();

    @BeforeMethod
    public void setUp() throws Exception {
        fileSystem = Jimfs.newFileSystem();
        final Path path = FileSystemUtils.getAbsolutePath(fileSystem, "tmp");
        Files.createDirectory(path);
    }

    @Test
    public void testReadingWhatIsWritten() throws Exception {
        final O subject = getSubject();
        final Path path = FileSystemUtils.getAbsolutePath(fileSystem, "tmp", "file.bin");
        getManager().getObjectWriter().write(path, subject);
        final O read = getManager().getObjectReader().read(path, subject.getClass());
        assertThat(read, is(subject));
    }

    @Test
    public void testDaisyChaining() throws Exception {
        final O subject = getSubject();
        final Path path = FileSystemUtils.getAbsolutePath(fileSystem, "tmp", "file.bin");
        getManager().getObjectWriter().write(path, subject);
        getManager().getObjectWriter().write(path, getManager().getObjectReader().read(path, subject.getClass()));
        final O read = getManager().getObjectReader().read(path, subject.getClass());
        assertThat(read, is(subject));
    }

}