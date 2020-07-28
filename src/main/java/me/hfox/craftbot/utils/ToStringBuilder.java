package me.hfox.craftbot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToStringBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToStringBuilder.class);
    private static final Map<Object, ToStringBuilder> ongoing = new HashMap<>();

    private final Object object;

    /**
     * Use simple name for classes
     */
    private boolean simpleName;

    /**
     * Collect fields automatically using reflection
     */
    private boolean reflective;

    /**
     * Call Field#setAccessible(true) on all fields
     */
    private boolean overrideAccess;

    /**
     * When reflection is enabled, collect fields from parent classes
     */
    private boolean parents;

    /**
     * When reflection is enabled, include static fields in population
     */
    private boolean includeStatic;

    /**
     * Show detailed arrays or use {class}[len({length})]
     */
    private boolean detailedArray;
    private final Map<Object, String> objects;
    /**
     * When dealing with multi-depth arrays, create a detailed array for all depths
     */
    private boolean deepArray;

    /**
     * Excludes this array from the list of reflective fields
     */
    private final List<String> excludes;

    /**
     * Built automatically when using reflection.
     * Elements are supplied by the user is reflective is false
     */
    private final Map<String, Object> fields;
    /**
     * Label shown when recursion takes place
     */
    private String recursiveLabel;

    private ToStringBuilder(Object object) {
        this.object = object;
        this.simpleName = true;
        this.reflective = false;
        this.overrideAccess = true;
        this.parents = false;
        this.includeStatic = false;
        this.detailedArray = true;
        this.recursiveLabel = "...";
        this.excludes = new ArrayList<>();
        this.fields = new HashMap<>();
        this.objects = new HashMap<>();

        excludes("$jacocoData");
    }

    public ToStringBuilder simpleName(boolean simpleName) {
        this.simpleName = simpleName;
        return this;
    }

    public ToStringBuilder reflective(boolean reflective) {
        this.reflective = reflective;
        return this;
    }

    public ToStringBuilder overrideAccess(boolean overrideAccess) {
        this.overrideAccess = overrideAccess;
        return this;
    }

    public ToStringBuilder parents(boolean parents) {
        this.parents = parents;
        return this;
    }

    public ToStringBuilder includeStatic(boolean includeStatic) {
        this.includeStatic = includeStatic;
        return this;
    }

    public ToStringBuilder detailedArray(boolean detailedArray) {
        this.detailedArray = detailedArray;
        return this;
    }

    public ToStringBuilder deepArray(boolean deepArray) {
        this.deepArray = deepArray;
        return this;
    }

    public ToStringBuilder excludes(String fieldName) {
        if (this.excludes.contains(fieldName)) {
            return this;
        }

        this.excludes.add(fieldName);
        return this;
    }

    public ToStringBuilder excludes(String... fieldNames) {
        for (String fieldName : fieldNames) {
            excludes(fieldName);
        }

        return this;
    }

    public ToStringBuilder clearExcludes() {
        this.excludes.clear();
        return this;
    }

    public ToStringBuilder add(String fieldName, Object value) {
        this.fields.put(fieldName, value);
        return this;
    }

    private void populate() {
        if (reflective) {
            this.fields.clear();

            List<Field> list = new ArrayList<>();

            Class<?> cls = object.getClass();
            while (!cls.isAssignableFrom(Object.class)) {
                collect(list, cls);
                if (!parents) {
                    break;
                }

                cls = cls.getSuperclass();
            }

            populate(list);
        }
    }

    private void collect(List<Field> list, Class<?> cls) {
        for (Field field : cls.getDeclaredFields()) {
            if (excludes.contains(field.getName()) || (!includeStatic && Modifier.isStatic(field.getModifiers()))) {
                continue;
            }

            list.add(field);
        }
    }

    private void populate(List<Field> list) {
        for (Field field : list) {
            try {
                if (overrideAccess) {
                    field.setAccessible(true);
                }

                add(field.getName(), field.get(object));
            } catch (Exception ex) {
                LOGGER.warn("Unable to populate fields", ex);
            }
        }
    }

    @Override
    public String toString() {
        populate();

        String name;
        if (simpleName) {
            name = object.getClass().getSimpleName();
        } else {
            name = object.getClass().getName();
        }

        StringBuilder builder = new StringBuilder(name);
        builder.append("{");

        boolean running;
        synchronized (ongoing) {
            running = ongoing.containsKey(object);
        }

        if (running) {
            builder.append(recursiveLabel);
        } else {
            synchronized (ongoing) {
                ongoing.put(object, this);
            }

            int i = 0;
            int length = fields.size();
            for (Map.Entry<String, Object> entry : fields.entrySet()) {
                builder.append(entry.getKey());
                builder.append("=");

                Object val = entry.getValue();
                builder.append(toString(val, 0));

                if (++i < length) {
                    builder.append(", ");
                }
            }

            synchronized (ongoing) {
                ongoing.remove(object);
            }
        }

        builder.append("}");

        return builder.toString();
    }

    /**
     * Converts the supplied object (array) into a string
     *
     * @param object The object to be converted, should be an array, eg; int[], byte[][], Object[], etc
     * @param depth  The depth of our current position, if this is > 1 it means we're in a multi-dimensional array
     * @return A string representing the supplied array, for example [1, 4, 2, 4] or ['hello', 'i', 'am', 'an', 'array']
     */
    private String toStringArray(Object object, int depth) {
        StringBuilder builder = new StringBuilder();

        if (detailedArray && (depth == 0 || deepArray)) {
            builder.append("[");
            String[] array = toSimpleArray(object, depth);

            for (int i = 0; i < array.length; i++) {
                builder.append(array[i]);

                if (i + 1 < array.length) {
                    builder.append(", ");
                }
            }

            builder.append("]");
        } else {
            builder.append(object.getClass().getComponentType().getSimpleName())
                    .append("[len(")
                    .append(Array.getLength(object))
                    .append(")]");
        }

        return builder.toString();
    }

    private String[] toSimpleArray(Object object, int depth) {
        String[] array = new String[Array.getLength(object)];
        for (int i = 0; i < array.length; i++) {
            // Here we add 1 to depth so that we are able to determine if the array should be detailed for deepArray
            array[i] = toString(Array.get(object, i), depth + 1);
        }

        return array;
    }

    private String toString(Object object, int depth) {
        if (object == null) {
            return "null";
        } else {
            String out = objects.get(object);
            if (out != null) {
                return out;
            }

            if (object instanceof String) {
                out = "'" + object + "'";
            } else if (object.getClass().isArray()) {
                out = toStringArray(object, depth);
            } else {
                out = object.toString();
            }

            objects.put(object, out);
            return out;
        }
    }

    public static ToStringBuilder build(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("object can't be null");
        }

        return new ToStringBuilder(object);
    }

}