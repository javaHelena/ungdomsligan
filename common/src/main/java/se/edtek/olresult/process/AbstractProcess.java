package se.edtek.olresult.process;

import org.skife.jdbi.v2.DBI;
import se.edtek.olresult.EventorClient;

public abstract class AbstractProcess {

    public DBI getDBI() {
        return new DBI("jdbc:postgresql:eventor");
    }

    public EventorClient getEventorClient() {
        return new EventorClient();
    }
}
