package rio.db.entity;

import java.util.Map;

public class RequestJSONEntity {

    private String type;
    private Map<String, String> arguments;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, String> arguments) {
        this.arguments = arguments;
    }
}
