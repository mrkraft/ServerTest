package rio.db.service;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import rio.db.ConnectionManager;
import rio.db.entity.PingEntity;

/**
 * Service for handling PING requests
 */
public class PingServiceImpl {

    /**
     * Get PING request count for userId, increase counter and save
     *
     * @param userId
     * @return ING request count
     */
    public static int getAndUpdatePingCount(String userId) {
        int result = 1;

        Mapper<PingEntity> mapper = new MappingManager(ConnectionManager.getSessionInstance()).mapper(PingEntity.class);
        PingEntity entity = mapper.get(userId);

        if (entity != null) {
            result = entity.getRequestCount() + 1;
            entity.setRequestCount(result);
        } else {
            entity = new PingEntity(userId, result);
        }

        mapper.saveAsync(entity);

        return result;
    }
}
