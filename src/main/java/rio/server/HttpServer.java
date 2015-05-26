package rio.server;

import com.twitter.finagle.builder.ServerBuilder;
import com.twitter.finagle.http.Http;
import org.jboss.netty.handler.codec.http.HttpRequest;

import java.net.InetSocketAddress;

public class HttpServer {

    public static int Calculate(String query) throws NumberFormatException {
        int sum = 0;
        System.out.println(query + " query");
        for (String ints : query.split(",")) {
            System.out.println(ints);
            try {
                sum = sum + Integer.parseInt(ints.toString());
            } catch (NumberFormatException e) {
                System.out.println(e);
                return -1;
            }
        }
        return sum;
    }

    public static int RemoteMethod(HttpRequest request) throws StringIndexOutOfBoundsException {
        try {
            String xx = request.getUri();
            xx = xx.substring(2);
            return Calculate(xx);
        } catch (StringIndexOutOfBoundsException s) {
            System.out.println(s);
            return -1;
        }
    }

    public static void main(String[] args) {

        RequestHandler handler = new RequestHandler();
        ServerBuilder.safeBuild(handler, ServerBuilder.get().codec(Http.get()).name("HttpServer").bindTo(new InetSocketAddress("localhost", 80))); //10.20.12.185
    }
}