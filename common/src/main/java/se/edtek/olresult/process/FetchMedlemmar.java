package se.edtek.olresult.process;

import se.edtek.olresult.db.LopareDAO;
import se.edtek.olresult.internalmodel.Lopare;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class FetchMedlemmar extends AbstractProcess {


    public void run() {
        System.out.println("Start - Running FetchMedlemmar!");
        LopareDAO dao = getDBI().onDemand(LopareDAO.class);
        List<Lopare> allaJokLopare = getEventorClient().getLopare("198");

        String juliaP = "64068"; // Julia Pettersson 2007
        String benjaminH = "159210"; // Benjamin Hjertén 2008
        String MaxB = "132748"; // Max Boström 2008
        String ArvidasG = "181327"; // Arvidas Gunnarsson 2008
        String IdaP = "64067"; // Ida Pettersson 2009
        String SamuelA= "86212"; // Samuel Andersson 2009
        String HampusL= "144776"; // Hampus Siljeqvist 2011
        String MalteL= "184701"; // Malte Lundblad 2012
        String LudvigW= "199208"; // Ludvig Wernersson 2014

        List<String> samples = Arrays.asList(juliaP, benjaminH, MaxB, ArvidasG, IdaP, SamuelA, HampusL, MalteL, LudvigW);

        List<Lopare> jokare = allaJokLopare.stream().filter(lopare -> samples.contains(lopare.eventorId)).collect(Collectors.toList());

        allaJokLopare.stream().filter(l -> l.fodelseDatum.isAfter(FetchResultat.OLDEST_BIRTH_DATE_LD)).forEach(l -> {
            dao.create(
                    UUID.randomUUID().toString(),
                    l.fornamn,
                    l.efternamn,
                    l.eventorId,
                    l.fodelseDatum.toString());

//                System.out.println("EventorId: " + l.eventorId +
//                        "  - Namn: " + l.fornamn + " " + l.efternamn +
//                        "  - Fodd: " + l.fodelseDatum
//                            );
        });

        System.out.println("Done - Running FetchMedlemmar!");

    }

    public static void main(String[] args) {
        new FetchMedlemmar().run();
    }
}
