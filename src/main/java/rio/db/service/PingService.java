package rio.db.service;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.DesignDocument;
import com.couchbase.client.protocol.views.ViewDesign;
import rio.db.ConnectionManager;

import javax.inject.Inject;
import javax.swing.text.View;

public class PingService {

    @Inject
    public PingService() {}

    public int getUpdatePingDataByUserId(String userId) {
        CouchbaseClient client = ConnectionManager.getClientInstance();

        DesignDocument designDoc = new DesignDocument("ping_data");
        String viewName = "by_user_id";

        String mapFunction =
                "function (doc, meta) {\n" +
                        "  if(doc.type && doc.type == \"beer\") {\n" +
                        "    emit(doc.name);\n" +
                        "  }\n" +
                        "}";

        ViewDesign viewDesign = new ViewDesign(viewName, mapFunction);
        designDoc.getViews().add(viewDesign);
        client.createDesignDoc(designDoc);

        return 0;
    }
}
