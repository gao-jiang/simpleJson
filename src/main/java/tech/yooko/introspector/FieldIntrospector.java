package tech.yooko.introspector;


import tech.yooko.annotation.Ignore;
import tech.yooko.annotation.Serialize;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import tech.yooko.introspector.SerializableField;


public class FieldIntrospector {

    public static List<SerializableField> getSerializableFields(Class<?> clazz) {
        List<SerializableField> fields = new ArrayList<>();

        for (Field field : getAllFields(clazz)) {
            if (field.isAnnotationPresent(Ignore.class)) {
                continue;
            }

            Serialize serialize = field.getAnnotation(Serialize.class);
            if (serialize != null || isPublicSerialized(field)) {
                field.setAccessible(true);

                String serializeName = serialize != null && !serialize.name().isEmpty()
                        ? serialize.name()
                        : field.getName();

                boolean ignoreNull = serialize != null && serialize.ignoreNull();
                fields.add(new SerializableField(field, serializeName, ignoreNull));
            }
        }

        return fields;
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Class<?> current = clazz;

        while (current != null && current != Object.class) {
            fields.addAll(Arrays.asList(current.getDeclaredFields()));
            current = current.getSuperclass();
        }

        return fields;

    }

    private static boolean isPublicSerialized(Field field) {
        return java.lang.reflect.Modifier.isPublic(field.getModifiers()) ||
                field.isAnnotationPresent(Serialize.class);
    }

}

