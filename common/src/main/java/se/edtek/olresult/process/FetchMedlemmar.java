package se.edtek.olresult.process;

import se.edtek.olresult.db.LopareDAO;
import se.edtek.olresult.internalmodel.Lopare;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FetchMedlemmar extends AbstractProcess {


    public void run() {
        LopareDAO dao = getDBI().onDemand(LopareDAO.class);
        List<Lopare> jokLopare = getEventorClient().getLopare("198");


        String erikB = "35963";
        String jonatan = "108347";
        String adam = "178211";
        String meja = "139245";
        String y = "145878";
        String z = "149028";
        String m = "159210";
        String kasperAberg = "161637";
        List<String> samples = Arrays.asList(erikB);

        List<Lopare> jokare = jokLopare.stream().filter(lopare -> samples.contains(lopare.eventorId)).collect(Collectors.toList());

        jokLopare.stream().filter(l -> l.fodelseDatum.isAfter(FetchResultat.OLDEST_BIRTH_DATE_LD)).forEach(l -> {
            dao.create(
                    UUID.randomUUID().toString(),
                    l.fornamn,
                    l.efternamn,
                    l.eventorId,
                    l.fodelseDatum.toString());

                System.out.println("EventorId: " + l.eventorId +
                        "  - Namn: " + l.fornamn + " " + l.efternamn +
                        "  - Fodd: " + l.fodelseDatum
                );
        });

    }

    public static void main(String[] args) {
        new FetchMedlemmar().run();
    }
}
