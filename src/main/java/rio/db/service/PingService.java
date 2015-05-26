package rio.db.service;

/**
 * Service interface
 */
public interface PingService {

    int getAndUpdatePingCount(String userId);
}
