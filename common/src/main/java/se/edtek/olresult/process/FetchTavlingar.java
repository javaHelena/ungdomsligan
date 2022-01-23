package se.edtek.olresult.process;

import org.skife.jdbi.v2.DBI;
import se.edtek.olresult.EventorClient;
import se.edtek.olresult.db.ResultatDAO;
import se.edtek.olresult.db.TavlingDAO;
import se.edtek.olresult.internalmodel.Resultat;
import se.edtek.olresult.internalmodel.Tavling;

import java.util.List;
import java.util.UUID;

public class FetchTavlingar extends AbstractProcess {

    public void run() {
        System.out.println("Start - Running FetchTävlingar!");

        DBI dbi = getDBI();
        TavlingDAO dao = dbi.onDemand(TavlingDAO.class);

        List<Tavling> tavlingar = dao.findAll();

        EventorClient client = getEventorClient();

        for (Tavling tavling : tavlingar) {

            Tavling t = client.getTavling(tavling.eventorId);

            dao.update(t.eventorId, t.disciplineId);
            //System.out.println("FETCHTAVLINGAR: Tävling: " + tavling.namn + " - status: " + tavling.eventStatusId + " - form: " + tavling.eventForm) ;
        }
        System.out.println("Done - Running FetchTävlingar!");
    }

    public static void main(String[] args) {
        new FetchTavlingar().run();
    }
}
