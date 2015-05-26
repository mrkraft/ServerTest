package rio.db;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import rio.util.Prop;

/**
 * This class contains initial DB configuration
 */
public class ConnectionManager {

    private static volatile Cluster cluster;
    private static volatile Session session;

    private ConnectionManager() {
    }

    /**
     * Create cluster connection and db working session
     *
     * @return cluster session
     */
    public static Session getSessionInstance() {
        if (cluster == null) {
            synchronized (ConnectionManager.class) {
                String address = Prop.getProp("db.cassandra.address");
                cluster = Cluster.builder().addContactPoint(address).build();
                session = cluster.connect();
                initDb(session);
            }
        }
        return session;
    }

    /**
     * For testing purpose only.
     * Method creates keyspace and table
     *
     * @param session
     */
    private static void initDb(Session session) {
        String keyspace = Prop.getProp("db.cassandra.keyspace");
        session.execute("CREATE KEYSPACE IF NOT EXISTS " + keyspace + " WITH replication " + "= {'class':'SimpleStrategy', 'replication_factor':3};");
        session.execute(
                "CREATE TABLE IF NOT EXISTS testdata.ping (" +
                        "userId text PRIMARY KEY," +
                        "requestCount int" +
                        ");");
    }
}
