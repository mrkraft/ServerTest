package rio.server;

import com.twitter.finagle.Service;
import com.twitter.util.Future;
import org.jboss.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rio.server.data.RequestEntity;
import rio.server.map.util.HandlerSearchTool;
import rio.server.util.HandlerPait;
import rio.server.util.JSONConverter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * This class contains methods, that handle server requests.
 */
public class RequestHandler extends Service {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    //Handlers map, that correspond to: uri, request type, request content type
    private Map<String, HandlerPait> handlers = new HashMap<String, HandlerPait>();

    public RequestHandler() {
        super();
        handlers = HandlerSearchTool.getHandlerMethods("rio.server");
    }

    /**
     * Initial method for request handling
     */
    @Override
    public Future apply(Object req) {
        //Get request
        HttpRequest request = (HttpRequest) req;
        RequestEntity requestEntity = getEntityByJSON(request);

        //Get handler name, that consist of uri ('/handler'), request type (POST), request content type (PING)
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
        String context = queryStringDecoder.getPath();
        String fullContext = context + "/" + request.getMethod().getName() + "/" + requestEntity.getTypeName();
        HandlerPait handler = handlers.get(fullContext);

        //Invoke handler and get response
        HttpResponse response = null;
        if (handler != null) {
            response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);

            try {
                Method method = handler.getMethod();
                method.invoke(handler.getClassObject(), requestEntity, response);
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage());
            } catch (InvocationTargetException e) {
                logger.error(e.getMessage());
            }
        } else {
            response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
        }

        return Future.value(response);
    }

    /**
     * Create object from request JSON content
     *
     * @param request HttpRequest
     * @return  object (RequestEntity), that represent request content
     */
    private RequestEntity getEntityByJSON(HttpRequest request) {
        RequestEntity result = null;
        String content = request.getContent().toString(Charset.forName("UTF-8"));
        result = new RequestEntity(JSONConverter.getMap(content));

        return result;
    }
}
