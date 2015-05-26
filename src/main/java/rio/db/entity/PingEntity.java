package rio.db.entity;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 * DB Entity for PING data
 */
@Table(keyspace = "testdata", name = "ping")
public class PingEntity {

    @PartitionKey
    private String userId;

    private int requestCount;

    public PingEntity() {
    }

    public PingEntity(String userId, int requestCount) {
        this.userId = userId;
        this.requestCount = requestCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof PingEntity)) {
            return false;
        }
        PingEntity that = (PingEntity) other;
        return this.userId.equals(that.getUserId()) && this.requestCount == that.getRequestCount();
    }

    @Override
    public int hashCode() {
        int result = getUserId() != null ? getUserId().hashCode() : 0;
        result = 31 * result + getRequestCount();
        return result;
    }
}
