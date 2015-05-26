package rio.server.handler;

import org.jboss.netty.handler.codec.http.HttpHeaders;
import rio.db.entity.RequestJSONEntity;
import rio.db.service.PingService;
import rio.server.map.Mapped;
import org.jboss.netty.handler.codec.http.HttpResponse;
import rio.server.map.MappedMethod;

import javax.inject.Inject;

@Mapped(uri = "/handler")
public class PingRequestHandler extends UriHandlerBased {

    @Inject PingService pingService;

    @MappedMethod(type = "POST/PING")
    public void processPingRequest(RequestJSONEntity requestEntity, HttpResponse response) {
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, getContentType());

        pingService.getUpdatePingDataByUserId("100500");
    }
}
