package com.mmnaseri.projects.cobweb.api.common;

import org.testng.annotations.Test;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/12/17, 11:55 PM)
 */
public abstract class BaseSerializableTypeTest {

    @Test
    public void testSerializationAndDeserialization() throws Exception {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        final Serializable original = getObject();
        outputStream.writeObject(original);
        outputStream.close();
        final byte[] bytes = byteArrayOutputStream.toByteArray();
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        final ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
        final Object read = inputStream.readObject();
        inputStream.close();
        assertThat(read, is(original));
    }

    protected abstract Serializable getObject();

}
