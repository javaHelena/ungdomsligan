package se.edtek.olresult;

import se.edtek.olresult.internalmodel.Lopare;

import java.time.LocalDate;
import java.util.List;

public class EventorTest {


    public static void main(String[] args) throws Exception {


        EventorClient ec = new EventorClient();
        String eventorId = "178211";

        //System.out.println("Fetching ");

        //Uppdatera datum varje år
        ec.getResultat(eventorId, LocalDate.of(2021,1, 1), LocalDate.of(2021, 12, 1));

        List<Lopare> lopare = ec.getLopare("198");
        //lopare.stream().forEach(l -> System.out.println("Id: " + l.eventorId + " -  Namn: "  + l.fornamn + " " + l.efternamn));

    }

    public static void fetchMedlemmar() {
        EventorClient ec = new EventorClient();

        List<Lopare> ls = ec.getLopare("198");
        for (Lopare l : ls) {
            //System.out.println(l.eventorId + l.fodelseDatum);
        }
    }

    public static void fetchResultat(String loparId) {
        //Uppdatera datum varje år
        LocalDate from = LocalDate.of(2021,1,1);
        LocalDate to = LocalDate.of(2021,12,31);

        EventorClient client = new EventorClient();
        client.getResultat(loparId, from, to);

    }
}
