package se.edtek.olresult.process;

import org.skife.jdbi.v2.DBI;
import se.edtek.olresult.EventorClient;
import se.edtek.olresult.db.LopareDAO;
import se.edtek.olresult.db.ResultatDAO;
import se.edtek.olresult.db.TavlingDAO;
import se.edtek.olresult.internalmodel.Lopare;
import se.edtek.olresult.internalmodel.Resultat;
import se.edtek.olresult.internalmodel.Tavling;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FetchResultat extends AbstractProcess {
    public static final LocalDate OLDEST_BIRTH_DATE_LD = LocalDate.of(2002, 12,31);

    public static final LocalDate YEAR_START_DATE_LD = LocalDate.of(2019, 1,1);  ///FOR TEST
    public static final LocalDate YEAR_END_DATE_LD = LocalDate.of(2019, 12,31);

    public void run() {

        LocalDate from = YEAR_START_DATE_LD;
        LocalDate to = YEAR_END_DATE_LD;

        LocalDate oldestRunnerNotIncluded = OLDEST_BIRTH_DATE_LD;

        DBI dbi = getDBI();
        LopareDAO dao = dbi.onDemand(LopareDAO.class);

        List<Lopare> jokare = dao.findAll();

        List<String> samples = Arrays.asList("35963", "108347","139245", "178211", "145878","149028", "159210"  );
        jokare.stream().map(lopare -> samples.contains(lopare.eventorId)).collect(Collectors.toList());

        EventorClient client = getEventorClient();

        int i = 0;

        for (Lopare jokLopare : jokare) {
            if (jokLopare.fodelseDatum.isAfter(oldestRunnerNotIncluded)) {
                System.out.println("Persisting result for Jokare: " + jokLopare.fornamn + " " + jokLopare.efternamn);
                List<Resultat> resultat = client.getResultat(jokLopare.eventorId, from, to);
                resultat.stream().forEach(r -> persistResultat(dbi, r, jokLopare));
            }


        }
    }

    private void persistResultat(DBI dbi, Resultat resultat, Lopare lopare) {

        resultat.tavling.id = persistTavling(dbi, resultat.tavling);

        if (resultat.tavling.eventForm.equals("RelaySingleDay") || resultat.tavling.eventForm.equals("PatrolSingleDay")) {
            // TODO borde kanske lagra att individen ställt upp i tävlingen

            return;
        }

        ResultatDAO dao = dbi.onDemand(ResultatDAO.class);

        dao.create(
                UUID.randomUUID().toString(),
                lopare.id,
                resultat.tavling.id,
                resultat.status,
                resultat.placering,
                resultat.timeDiff,
                resultat.poangReduktion,
                resultat.maxpoang,
                resultat.poang,
                resultat.klass,
                resultat.baseClass.name(),
                resultat.classTypeId,
                resultat.baseClassId);

    }

    private String persistTavling(DBI dbi, Tavling tavling) {
        TavlingDAO dao = dbi.onDemand(TavlingDAO.class);

        Tavling storedTavling = dao.findByEventorId(tavling.eventorId);

        if (storedTavling == null) {
            dao.create(
                    UUID.randomUUID().toString(),
                    tavling.eventorId,
                    tavling.namn,
                    tavling.eventClassificationId,
                    tavling.eventStatusId,
                    tavling.eventForm);

            storedTavling = dao.findByEventorId(tavling.eventorId);
        }

        return storedTavling.id;
    }

    public static void main(String[] args) {
        new FetchResultat().run();
    }
}
