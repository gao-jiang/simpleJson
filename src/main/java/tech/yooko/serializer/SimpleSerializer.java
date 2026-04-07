package tech.yooko.serializer;

import lombok.var;
import tech.yooko.introspector.FieldIntrospector;
import tech.yooko.introspector.SerializableField;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SimpleSerializer {

    public static String serialize(Object obj) {
        if (obj == null) {
            return "null";
        }

        Class<?> clazz = obj.getClass();

        if (isPrimitive(clazz)) {
            return serializePrimitive(obj);
        }
        if (obj instanceof Collection) {
            return serializeCollection((Collection<?>) obj);
        }
        if (obj instanceof Map) {
            return serializeMap((Map<?, ?>) obj);
        }

        return serializeObject(obj, clazz);

    }

    public static String serializeObject(Object obj, Class<?> clazz) {
        StringBuilder json = new StringBuilder("{");

        List<SerializableField> fields = FieldIntrospector.getSerializableFields(clazz);

        boolean first = true;
        for (SerializableField sf : fields) {
            Object value = sf.getValue(obj);

            if(value == null && sf.ignoreNull) {
                continue;
            }

            if (!first) {
                json.append(",");
            }
            first = false;

            json.append("\"").append(sf.serializeName).append("\":");
            json.append(serialize(value));
        }

        json.append("}");
        return json.toString();

    }

    private static String serializeCollection(Collection<?> col) {
        StringBuilder json = new StringBuilder("[");
        boolean first = true;

        for (Object item : col) {
            if (!first) json.append(",");
            first = false;
            json.append(serialize(item));
        }

        json.append("]");
        return json.toString();
    }

    private static String serializeMap(Map<?, ?> map) {
        StringBuilder json = new StringBuilder("{");
        boolean first = true;

        for (var entry : map.entrySet()) {
            if (!first) json.append(",");
            first = false;

            json.append("\"").append(entry.getKey()).append("\":");
            json.append(serialize(entry.getValue()));
        }

        json.append("}");
        return json.toString();
    }

    private static String escapeString(String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    private static String serializePrimitive(Object obj) {
        if (obj instanceof String) {
            return "\"" + escapeString((String) obj) + "\"";
        } else if (obj instanceof Boolean || obj instanceof Number) {
            return obj.toString();
        }

        return "\"" + obj.toString() + "\"";
    }

    private static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() ||
                clazz == String.class ||
                clazz == Boolean.class ||
                Number.class.isAssignableFrom(clazz);
    }


}
