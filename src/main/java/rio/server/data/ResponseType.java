package rio.server.data;

import java.util.HashMap;
import java.util.Map;

/**
 * This Class represent response type enum
 */
public class ResponseType {

    public static final ResponseType PONG = new ResponseType("PONG");

    private static final Map<String, ResponseType> typeMap = new HashMap<String, ResponseType>();

    static {
        typeMap.put(PONG.toString(), PONG);
    }

    private final String type;

    public ResponseType(String type) {
        if (type == null) {
            throw new NullPointerException("type");
        }

        type = type.trim();
        if (type.length() == 0) {
            throw new IllegalArgumentException("empty type");
        }

        for (int i = 0; i < type.length(); i++) {
            if (Character.isISOControl(type.charAt(i)) ||
                    Character.isWhitespace(type.charAt(i))) {
                throw new IllegalArgumentException("invalid character in type");
            }
        }

        this.type = type;
    }

    public static ResponseType valueOf(String type) {
        if (type == null) {
            throw new NullPointerException("type");
        }

        type = type.trim();
        if (type.length() == 0) {
            throw new IllegalArgumentException("empty type");
        }

        ResponseType result = typeMap.get(type);
        if (result != null) {
            return result;
        } else {
            return new ResponseType(type);
        }
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return getType().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ResponseType)) {
            return false;
        }

        ResponseType that = (ResponseType) o;
        return getType().equals(that.getType());
    }

    @Override
    public String toString() {
        return getType();
    }
}
