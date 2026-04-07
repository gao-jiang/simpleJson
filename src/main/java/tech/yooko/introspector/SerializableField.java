package tech.yooko.introspector;

import java.lang.reflect.Field;

public class SerializableField {
    public final Field field;
    public String serializeName;
    public boolean ignoreNull;

    public SerializableField(Field field, String serializeName, boolean ignoreNull) {
        this.field = field;
        this.serializeName = serializeName;
        this.ignoreNull = ignoreNull;
    }

    public Object getValue(Object obj) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to get field: " + field.getName());
        }
    }

    public void setValue(Object obj, Object value) {
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to set field: " + field.getName());
        }
    }

}
