package rio.db;

import com.couchbase.client.CouchbaseClient;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ConnectionManager {

    private static CouchbaseClient client;

    public static synchronized CouchbaseClient getClientInstance() {
        if (client == null) {
            List<URI> uris = new ArrayList<URI>();
            uris.add(URI.create("http://127.0.0.1:8091/pools"));

            try {
                client = new CouchbaseClient(uris, "default", "");
            } catch (Exception e) {
                System.err.println("Error connecting to Couchbase: " + e.getMessage());
                System.exit(0);
            }
        }
        return client;
    }
}
