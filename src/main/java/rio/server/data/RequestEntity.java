package rio.server.data;

import java.util.Map;

/**
 * This Class represent request information
 */
public class RequestEntity {

    public static final String TYPE_FIELD_NAME = "type";

    private Map<String, Object> params;

    public RequestEntity(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void addParam(String key, Object value) {
        params.put(key, value);
    }

    public Object getParam(String key) {
        return params.get(key);
    }

    public RequestType getType() {
        String type = (String) params.get(TYPE_FIELD_NAME);
        return RequestType.valueOf(type);
    }

    public String getTypeName() {
        String type = (String) params.get(TYPE_FIELD_NAME);
        return RequestType.valueOf(type).getType();
    }
}
