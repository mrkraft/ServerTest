package rio.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.finagle.Service;
import com.twitter.util.Future;
import rio.db.entity.RequestJSONEntity;
import rio.server.handler.UriHandlerBased;
import rio.server.map.Mapped;
import rio.server.map.util.HandlerSearchTool;
import org.jboss.netty.handler.codec.http.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler extends Service {

    private Map<String, Method> handlers = new HashMap<String, Method>();

    public RequestHandler() {
        super();
        handlers = HandlerSearchTool.getHandlerMethods("rio.server");
    }

    @Override
    public Future apply(Object req) {
        HttpRequest request = (HttpRequest)req;
        RequestJSONEntity requestEntity = getEntityByJSON(request);

        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
        String context = queryStringDecoder.getPath();
        String fullContext = context + "/" + request.getMethod().getName() + "/" + requestEntity.getType();
        Method handler = handlers.get(fullContext);

        HttpResponse response = null;
        if (handler != null) {
            response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);

            try {
                handler.invoke(request, response);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
        }

        return Future.value(response);
    }

    private RequestJSONEntity getEntityByJSON(HttpRequest request) {
        RequestJSONEntity result = null;

        String content = "{\"type\":\"PING\",\"arguments\":{\"userId\":\"1e7b05a0-0285-48ea-885e-5cc3b56fe05f\"}}";     //request.getContent().toString(Charset.forName("UTF-8"));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            result = objectMapper.readValue(content, RequestJSONEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
