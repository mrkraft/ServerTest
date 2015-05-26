package rio.server.handler;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpResponse;
import rio.db.service.PingServiceImpl;
import rio.server.data.RequestEntity;
import rio.server.data.ResponseType;
import rio.server.map.Mapped;
import rio.server.map.MappedMethod;
import rio.server.util.JSONConverter;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Class, that contains handlers for '/handler' uri
 */
@Mapped(uri = "/handler")
public class PingRequestHandler extends UriHandlerBased {

    @MappedMethod(type = "POST/PING")
    public void processPingRequest(RequestEntity requestEntity, HttpResponse response) {
        String usirId = (String) requestEntity.getParam("userId");
        int requestCount = PingServiceImpl.getAndUpdatePingCount(usirId);

        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, getContentType());
        Map<String, Object> responseContentMap = new HashMap<String, Object>();
        responseContentMap.put("type", ResponseType.PONG.getType());
        responseContentMap.put("requestCount", requestCount);
        String responseContent = JSONConverter.getJSON(responseContentMap);

        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, getContentType());
        response.setContent(ChannelBuffers.copiedBuffer(responseContent, Charset.forName("UTF-8")));
    }

    @MappedMethod(type = "GET/PING")
    public void processPingRequestTest(RequestEntity requestEntity, HttpResponse response) {
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, getContentType());

        PingServiceImpl.getAndUpdatePingCount("1e7b05a0-0285-48ea-885e-5cc3b56fe05f");
    }
}
