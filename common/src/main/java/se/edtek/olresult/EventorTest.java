package se.edtek.olresult;

import se.edtek.olresult.internalmodel.Lopare;
import se.edtek.olresult.internalmodel.Resultat;
import se.edtek.olresult.internalmodel.Tavling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventorTest {

    public static String LOPARE_EVENTOR_ID_1 = "64068"; // Julia Pettersson 2007
    public static String LOPARE_EVENTOR_ID_2 = "159210"; // Benjamin Hjertén 2008
    public static String LOPARE_EVENTOR_ID_3 = "132748"; // Max Boström 2008
    public static String LOPARE_EVENTOR_ID_4 = "181327"; // Arvidas Gunnarsson 2008
    public static String LOPARE_EVENTOR_ID_5 = "64067"; // Ida Pettersson 2009
    public static String LOPARE_EVENTOR_ID_6 = "86212"; // Samuel Andersson 2009
    public static String LOPARE_EVENTOR_ID_7 = "144776"; // Hampus Siljeqvist 2011
    public static String LOPARE_EVENTOR_ID_8 = "184701"; // Malte Lundblad 2012
    public static String LOPARE_EVENTOR_ID_9 = "199208"; // Ludvis Wernersson 2014

    public static String JOK_KLUBB_ID = "198";

    public static final LocalDate OLDEST_BIRTH_DATE_LD = LocalDate.of(2006, 12, 31);

    //Uppdatera datum varje år
    public static LocalDate DATE_FROM = LocalDate.of(2023, 1, 1);
    public static LocalDate DATE_TO = LocalDate.of(2023, 12, 31);


    public static void main(String[] args) throws Exception {

        testGeneral();
        //fetchMedlemmar();
        //fetchResultat();
        //fetchTavlingar();

    }

    public static void fetchTavlingar() {
        EventorClient ec = new EventorClient();

        List<Lopare> allaLopare = ec.getLopare(JOK_KLUBB_ID);
        allaLopare.sort((l1, l2) -> l1.fodelseDatum.compareTo(l2.fodelseDatum));

        //List<Resultat> resultat = allaLopare.stream().map(r -> ec.getResultat(r.eventorId, DATE_FROM, DATE_TO)).flatMap(List::stream).collect(Collectors.toList());
        List<Resultat> allaResultat = new ArrayList<>();

        int j = 1;
        for (Lopare lopare : allaLopare) {
            List<Resultat> lopareResultat =  ec.getResultat(lopare.eventorId, DATE_FROM, DATE_TO);
            allaResultat.addAll(lopareResultat);
            System.out.println(j + ".");
            j++;
        }

        Set<Tavling> tavlingar = new HashSet<>();
        Set<String> tavlingEventorIds = new HashSet<>();

        allaResultat.stream().map(r -> r.tavling.eventorId).forEach(tavlingEventorIds::add);

        System.out.println("***************************************");
        System.out.println("Alla resultat = " + allaResultat.size() + " st");
        System.out.println("Alla lopare = " + allaLopare.size() + " st");
        System.out.println("Alla tavlingar = " + tavlingEventorIds.size() + " st");
        System.out.println("***************************************");

        int i = 1;
        for (String eventorId : tavlingEventorIds) {
            Tavling tavling = ec.getTavling(eventorId);
            tavlingar.add(tavling);
            System.out.println(i + ".");
            i++;
        }

        System.out.println("Fetching Tavlingar");
        System.out.println("eventorId, namn, eventForm, disciplineId, id, eventClassificationName, eventStatusId, eventClassificationId");
        tavlingar.forEach(t -> System.out.println(t.eventorId + ","
                + t.namn + ","
                + t.eventForm + ","
                + t.disciplineId + ","
                + t.id + ","
                + t.eventClassificationName
                + t.eventStatusId + ","
                + t.eventClassificationId));

        System.out.println("Fetched totally " + tavlingar.size() + " tavlingar");

    }

    public static void testGeneral() {
        EventorClient ec = new EventorClient();

        System.out.println("Fetching Medlemmar");
        List<Lopare> lopare = ec.getLopare(JOK_KLUBB_ID);
        lopare.sort((l1, l2) -> l1.fodelseDatum.compareTo(l2.fodelseDatum));
        Lopare testLopare = lopare.stream().filter(l -> l.eventorId.equals(LOPARE_EVENTOR_ID_1)).findFirst().get();
        System.out.println("Fetched totally " + lopare.size() + " lopare");


        //lopare.forEach(l -> System.out.println("Id: " + l.eventorId + " -  Namn: " + l.fornamn + " " + l.efternamn));

        List<Lopare> tooOldLopare = new ArrayList<>();
        List<Lopare> correctAgeLopare = new ArrayList<>();
        List<Lopare> correctAgeNoResults = new ArrayList<>();


        System.out.println("***************************************");
        System.out.println("Fetching Resultat");
        System.out.println("år, eventorId, namn, antal resultat, vinster, andraplatser");
        for (Lopare lopare1 : lopare) {
            if (lopare1.fodelseDatum.isAfter(OLDEST_BIRTH_DATE_LD)) {
                //List<Resultat> resultsForLopare = ec.getResultat(lopare1.eventorId, DATE_FROM, DATE_TO);
                List<Resultat> resultsForLopare = Collections.emptyList();

                if (!resultsForLopare.isEmpty()) {
                    int wins = resultsForLopare.stream().filter(r -> r.placering == 1).mapToInt(r -> 1).sum();
                    int secondPlaces = resultsForLopare.stream().filter(r -> r.placering == 2).mapToInt(r -> 1).sum();
//                    System.out.println(lopare1.fodelseDatum.getYear() + ","
//                            + lopare1.eventorId + ","
//                            + lopare1.fornamn + " "
//                            + lopare1.efternamn + ","
//                            + resultsForLopare.size() + ","
//                            + wins + ","
//                            + secondPlaces);
                    System.out.print(".");
                    correctAgeLopare.add(lopare1);
                } else {
                    correctAgeNoResults.add(lopare1);
                }

            } else {
                tooOldLopare.add(lopare1);
            }
        }

        System.out.println("***************************************");
        int totalNumberOfLopare = correctAgeLopare.size() + correctAgeNoResults.size();
        System.out.println("Fetching Resultat for totally " + totalNumberOfLopare + " lopare");
        System.out.println("Ignoring " + correctAgeNoResults.size() + " lopare without results");
        System.out.println("Keeping " + correctAgeLopare.size() + " lopare with results");
        System.out.println("Skipping " + tooOldLopare.size() + " lopare that are born before " + OLDEST_BIRTH_DATE_LD.getYear() + ".");
        System.out.println("***************************************");

        for (Lopare lopare1 : tooOldLopare) {
            //  System.out.println("Skipping " + lopare1.fornamn + " " + lopare1.efternamn + " - too old : "  + lopare1.fodelseDatum.getYear());
        }

        System.out.println();
        System.out.println("***************************************");
        System.out.println("Fetching Resultat for lopare born before " + OLDEST_BIRTH_DATE_LD.getYear() + ".");
        for (Lopare lopare1 : tooOldLopare) {
            List<Resultat> resultsForLopare = ec.getResultat(lopare1.eventorId, DATE_FROM, DATE_TO);
            if (!resultsForLopare.isEmpty()) {
                System.out.println("Checking lopare: " + lopare1.fornamn + " " + lopare1.efternamn);
                int wins = resultsForLopare.stream().filter(r -> r.placering == 1).mapToInt(r -> 1).sum();
                int secondPlaces = resultsForLopare.stream().filter(r -> r.placering == 2).mapToInt(r -> 1).sum();
                System.out.println(lopare1.fodelseDatum.getYear() + ","
                        + lopare1.eventorId + ","
                        + lopare1.fornamn + " "
                        + lopare1.efternamn + ","
                        + resultsForLopare.size() + ","
                        + wins + ","
                        + secondPlaces);
            } else {
                correctAgeNoResults.add(lopare1);
            }
        }


        System.out.println();
        System.out.println("***************************************");
        System.out.println("Checking the result for ONE Lopare");
        List<Resultat> results = ec.getResultat(LOPARE_EVENTOR_ID_1, DATE_FROM, DATE_TO);
        System.out.println("Fetching Results for LÖPARE = " + LOPARE_EVENTOR_ID_1 + " - " + testLopare.fornamn + " " + testLopare.efternamn + " - " + results.size() + ",results");

        for (Resultat result : results) {
            System.out.println("Tavling: " + result.tavling.namn + " - " + result.tavling.eventorId + " - " + result.placering + " - " + result.timeDiff);
        }

        System.out.println("Fetching Tavlingar");

        for (Resultat result : results) {
            Tavling tavling = ec.getTavling(result.tavling.eventorId);
            System.out.println("Tavling: " + tavling.namn + " - " + tavling.eventorId + " - " + tavling.eventForm);
        }
    }


}
