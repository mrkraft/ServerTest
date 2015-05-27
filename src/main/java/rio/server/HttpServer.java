package rio.server;

import com.twitter.finagle.builder.ServerBuilder;
import com.twitter.finagle.http.Http;
import rio.util.Prop;

import java.net.InetSocketAddress;

/**
 * Application initial point. This Class provides starting of server
 */
public class HttpServer {

    public static void main(String[] args) {

        RequestHandler handler = new RequestHandler();

        String hostname = Prop.getProp("server.hostname");
        int port = Integer.parseInt(Prop.getProp("server.port"));

        ServerBuilder.safeBuild(handler, ServerBuilder.get().codec(Http.get()).name("HttpServer").bindTo(new InetSocketAddress(hostname, port)));
    }
}