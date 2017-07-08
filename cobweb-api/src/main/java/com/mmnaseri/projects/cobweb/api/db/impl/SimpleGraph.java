package com.mmnaseri.projects.cobweb.api.db.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mmnaseri.projects.cobweb.api.db.Graph;
import com.mmnaseri.projects.cobweb.api.fs.PathResolver;
import com.mmnaseri.projects.cobweb.domain.content.*;
import com.mmnaseri.projects.cobweb.domain.id.Identifier;
import com.mmnaseri.projects.cobweb.domain.id.IdentifierFactory;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mohammad Milad Naseri (mmnaseri@programmer.net)
 * @since 1.0 (7/5/17, 10:43 PM)
 */
public class SimpleGraph<I extends Serializable> implements Graph<I> {

    private final String name;
    private final PathResolver pathResolver;
    private final ObjectMapper objectMapper;
    private final IdentifierFactory<I> identifierFactory;

    public SimpleGraph(String name, PathResolver pathResolver, IdentifierFactory<I> identifierFactory) {
        this.name = name;
        this.pathResolver = pathResolver;
        this.identifierFactory = identifierFactory;
        objectMapper = new ObjectMapper();
        final SimpleModule module = new SimpleModule();
        module.addDeserializer(Identifier.class, new JsonDeserializer<Identifier>() {
            @Override
            public Identifier deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                final String string = jsonParser.readValueAs(String.class);
                return identifierFactory.fromString(string);
            }
        });
        module.addSerializer(Identifier.class, new JsonSerializer<Identifier>() {
            @Override
            public void serialize(Identifier identifier, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                //noinspection unchecked
                jsonGenerator.writeString(identifierFactory.toString(identifier));
            }
        });
        module.setMixInAnnotation(Persistent.class, IgnoreObjectType.class);
        module.setMixInAnnotation(Persistent.class, IdPropertyNameAlias.class);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(module);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PathResolver getPathResolver() {
        return pathResolver;
    }

    @Override
    public Set<Tag<I>> getTags() {
        final Path pathToTags = pathResolver.resolve(this, ObjectType.TAG);
        final File tagsDir = pathToTags.toFile();
        if (!tagsDir.exists()) {
            if (!tagsDir.mkdirs()) {
                throw new IllegalStateException("Failed to create directory for tags at " + tagsDir.getAbsolutePath());
            }
        }
        final File[] files = tagsDir.listFiles();
        if (files == null) {
            throw new IllegalStateException("Error listing tags");
        }
        //noinspection unchecked
        return Arrays.stream(files)
                .map(file -> read(file, Tag.class))
                .map(tag -> (Tag<I>) tag)
                .collect(Collectors.toSet());
    }

    @Override
    public Tag<I> createTag(String name, String description) {
        checkTagNameDuplication(name);
        final Tag<I> tag = new Tag<>();
        tag.setId(nextId());
        tag.setName(name);
        tag.setDescription(description);
        return write(tag);
    }

    @Override
    public Vertex<I> createVertex() {
        final Vertex<I> vertex = new Vertex<>();
        vertex.setAttachments(new HashMap<>());
        vertex.setTags(new HashSet<>());
        vertex.setId(nextId());
        return write(vertex);
    }

    @Override
    public Attachment<I> createAttachment() {
        return null;
    }

    @Override
    public Edge<I> createEdge(Vertex<I> from, Vertex<I> to) {
        final Edge<I> edge = new Edge<>();
        edge.setId(nextId());
        edge.setFrom(from);
        edge.setTo(to);
        edge.setAttachments(new HashMap<>());
        edge.setTags(new HashSet<>());
        return write(edge);
    }

    @Override
    public <P extends Persistent<I>> P save(P persistent) {
        return write(persistent);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <P extends Persistent<? extends I>> P findById(Identifier<I> identifier, Class<P> type) {
        if (type == null) {
            Persistent<I> found = findById(identifier, Document.class);
            if (found == null) {
                found = findById(identifier, Tag.class);
            }
            if (found == null) {
                found = findById(identifier, Attachment.class);
            }
            return (P) found;
        } else {
            final ObjectType objectType;
            if (Document.class.isAssignableFrom(type)) {
                objectType = ObjectType.DOCUMENT;
            } else if (Attachment.class.isAssignableFrom(type)) {
                objectType = ObjectType.ATTACHMENT;
            } else if (Tag.class.isAssignableFrom(type)) {
                objectType = ObjectType.TAG;
            } else {
                throw new IllegalArgumentException("Unknown object type " + type);
            }
            final Path path = pathResolver.resolve(this, objectType, identifier);
            return read(path.toFile(), type);
        }
    }

    @Override
    public List<Object> query(String query) {
        return null;
    }

    private Identifier<I> nextId() {
        Identifier<I> id;
        do {
            id = identifierFactory.produce();
        } while (findById(id) != null);
        return id;
    }

    @SuppressWarnings("unchecked")
    private <T> T read(File file, Class<T> type) {
        ensurePath(file);
        if (!file.exists()) {
            return null;
        }
        try {
            final T value = objectMapper.readValue(file, type);
            if (value instanceof Document) {
                Document<I> document = (Document<I>) value;
                final Set<Tag<I>> tags = new HashSet<>();
                for (Tag<I> tag : document.getTags()) {
                    tags.add(findById(tag.getId(), Tag.class));
                }
                document.getTags().clear();
                document.getTags().addAll(tags);
            }
            return value;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read type " + type + " from file " + file.getAbsolutePath(), e);
        }
    }

    private <P extends Persistent<I>> P write(P persistent) {
        final Path path = pathResolver.resolve(this, persistent);
        final P copy = cloneQueietly(persistent);
        if (copy instanceof Document) {
            //noinspection unchecked
            Document<I> document = (Document<I>) copy;
            final Set<Tag<I>> tags = document.getTags().stream()
                    .map(tag -> {
                        final Tag<I> replacement = new Tag<>();
                        replacement.setId(tag.getId());
                        return replacement;
                    })
                    .collect(Collectors.toSet());
            document.setTags(tags);
        }
        write(path.toFile(), copy);
        return persistent;
    }

    private void write(File file, Object value) {
        ensurePath(file);
        try {
            objectMapper.writeValue(file, value);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write " + value + " into file " + file.getAbsolutePath());
        }
    }

    private void ensurePath(File file) {
        final File parent = file.getParentFile();
        if (!parent.exists()) {
            if (!parent.mkdirs()) {
                throw new IllegalStateException("Failed to make path " + parent.getAbsolutePath());
            }
        }
    }

    private void checkTagNameDuplication(String name) {
        boolean duplicate = getTags().stream()
                .filter(tag -> tag.getName().equals(name))
                .count() > 0;
        if (duplicate) {
            throw new IllegalArgumentException("Tag <" + name + "> already exists");
        }
    }

    private <O> O cloneQueietly(O original) {
        try {
            return clone(original);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private <O> O clone(O original) throws Exception {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        final ObjectOutputStream outputStream = new ObjectOutputStream(stream);
        outputStream.writeObject(original);
        outputStream.close();
        final ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(stream.toByteArray()));
        final Object value = inputStream.readObject();
        inputStream.close();
        //noinspection unchecked
        return (O) value;
    }

    @JsonIgnoreProperties({"objectType"})
    private static class IgnoreObjectType {
    }

    private static class IdPropertyNameAlias {

        @JsonProperty("_id")
        private String id;

    }

}
