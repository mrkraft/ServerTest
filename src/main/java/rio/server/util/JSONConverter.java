package rio.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Static class - Helper. Methods of this class convert JSON to Map and vice versa
 */
public class JSONConverter {

    private static final Logger logger = LoggerFactory.getLogger(JSONConverter.class);

    private static class MapperHolder {
        private final static ObjectMapper objectMapper = new ObjectMapper();
    }

    private static ObjectMapper getMapper() {
        return MapperHolder.objectMapper;
    }

    public static Map<String, Object> getMap(String content) {
        Map<String, Object> result = null;
        try {
            result = getMapper().readValue(content, new TypeReference<HashMap<String, Object>>() {
            });
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return result;
    }

    public static String getJSON(Map<String, Object> params) {
        String result = null;
        try {
            result = getMapper().writeValueAsString(params);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }

        return result;
    }
}
