package se.edtek.olresult.process;

import org.skife.jdbi.v2.DBI;
import se.edtek.olresult.db.LopareDAO;
import se.edtek.olresult.db.ResultatDAO;
import se.edtek.olresult.internalmodel.Lopare;
import se.edtek.olresult.internalmodel.Resultat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalculateResult extends AbstractProcess {

    public void run() {

        DBI dbi = getDBI();

        ResultatDAO resultatDAO = dbi.onDemand(ResultatDAO.class);
        LopareDAO lopareDAO = dbi.onDemand(LopareDAO.class);

        List<Lopare> jokare = lopareDAO.findAll();

        jokare.stream().forEach(j -> {
            calculatePoints(resultatDAO, lopareDAO, j);
           // System.out.println("Calculating points for " + j.fornamn + " " + j.efternamn);
        });
    }

    private void calculatePoints(ResultatDAO resultatDAO, LopareDAO lopareDAO, Lopare lopare) {

        List<Resultat> resultat = resultatDAO.findByLopare(lopare.id);

        int numVictories = 0;
        int numHundredPointers = 0;

        List<Integer> rr = new ArrayList<>();

        for (Resultat r : resultat) {

            if (r.poang == 100) {
                numHundredPointers++;
            }

            if (r.placering == 1) {
                if (r.poang > 0) {  // räkna endast vinster för godkända tävlingar
                    numVictories++;
                }
            }

            if (r.poang > 0) {
                rr.add(r.poang);
            }
        }

        int tot = sumPoints(resultat);

        resultat.stream().forEach(r -> {
            if (r.lopare == null) {
            } else {
//                System.out.println("EventorId: " + r.lopare.eventorId +
//                        " - Person: " + lopare.fornamn + " " + lopare.efternamn +
//                        " - tävling: " + r.tavling.namn +
//                        " - klass??? : " + r.tavling.eventClassificationName +
//                        " - poäng: " + r.poang +
//                        " - plats: " + r.placering +
//                        " - ålder: " + lopare.fodelseDatum.toString());
            }

        });

        lopareDAO.setPoints(lopare.id, numVictories, numHundredPointers, tot);
    }

    private int sumPoints(List<Resultat> results) {


        if (results == null || results.isEmpty()) {
            return 0;
        }

        List<Integer> points = results.stream().map(r -> r.poang).collect(Collectors.toList());


        points.sort((Integer i1, Integer i2) -> -i1.compareTo(i2));

        int tot = points.stream().limit(6).mapToInt(p -> p.intValue()).sum();

        return tot;
    }

    public static void main(String[] args) {
        new CalculateResult().run();
    }
}
