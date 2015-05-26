package rio.server.data;

import java.util.HashMap;
import java.util.Map;

/**
 * This Class represent request type enum
 */
public class RequestType {

    public static final RequestType PING = new RequestType("PING");

    private static final Map<String, RequestType> typeMap = new HashMap<String, RequestType>();

    static {
        typeMap.put(PING.toString(), PING);
    }

    private final String type;

    public RequestType(String type) {
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

    public static RequestType valueOf(String type) {
        if (type == null) {
            throw new NullPointerException("type");
        }

        type = type.trim();
        if (type.length() == 0) {
            throw new IllegalArgumentException("empty type");
        }

        RequestType result = typeMap.get(type);
        if (result != null) {
            return result;
        } else {
            return new RequestType(type);
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
        if (!(o instanceof RequestType)) {
            return false;
        }

        RequestType that = (RequestType) o;
        return getType().equals(that.getType());
    }

    @Override
    public String toString() {
        return getType();
    }
}
