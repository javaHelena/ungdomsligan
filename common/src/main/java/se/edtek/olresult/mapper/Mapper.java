package se.edtek.olresult.mapper;

import se.edtek.olresult.eventormodel.*;
import se.edtek.olresult.internalmodel.BaseClass;
import se.edtek.olresult.internalmodel.Lopare;
import se.edtek.olresult.internalmodel.Resultat;
import se.edtek.olresult.internalmodel.Tavling;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mapper {

    private static final List<String> INGNORABLE_EVENT_IDS =
            Arrays.asList("27396" ,"27398", "27529", "27738", "27739",
                    "27742", "27743", "27746", "27972", "28501","30222",
                    "30223", "30236", "30624", "31081", "31088", "31124",
                    "31542", "32210", "32592", "32725", "32960");


//    27396, 27398, 27742, 27743, SkidO
//    27529, 27738, 27739 //Mila Stockholm by night
//    27746 Sthlm Indoor Cup
//    27972 Gävle indoor cup
//    28501 //Elitmiljö Sthlm
//    30222, 30223, 30236 //Stof Träningsstafett
//    30624 Träningstävling Kårsta
//    31081, 31088, 31124 //Norrortsutmaningen
//    31542 Klubb Kyllingaröd
//    32210 Utvecklingsläger sprinten
//    32592 Elitmiljöträning
//    32725, 32960 Fredrika Bremer

    public static Lopare asLopare(Person person) {
        Lopare lopare = new Lopare();

        lopare.efternamn = person.personName.family;
        lopare.fornamn = person.personName._given();
        lopare.eventorId = person.personId.value;
        lopare.fodelseDatum = LocalDate.parse(person.birthDate.date);

        return lopare;
    }

    public static Resultat asResultat(Event event, ClassResult classResult, String eventorIdPerson) {
        Resultat resultat = new Resultat();
        resultat.tavling = asTavling(event);
        resultat.lopare = asLopare(eventorIdPerson);
        resultat.klass = classResult.eventClass.name;
        resultat.classTypeId = classResult.eventClass.classTypeId;
        resultat.baseClassId = classResult.eventClass.baseClassId;

        //System.out.println(event.eventId + ";" + event.name +  "; " + classResult.eventClass.name + "; " + classResult.eventClass.baseClassId +  "; " + classResult.eventClass.classTypeId + "; " + classResult.eventClass.eventClassStatus );

        if (classResult.eventClass.baseClassId == 0) {
//            System.out.println("EventId, event.name; eventClass.name; baseClass.id; classType.id; eventClassStatus" );
//            System.out.println(event.eventId + ";" + event.name +  "; " + classResult.eventClass.name + "; " + classResult.eventClass.baseClassId +  "; " + classResult.eventClass.classTypeId + "; " + classResult.eventClass.eventClassStatus );

//            System.out.println("Tavling: " + event.name +  " - EventClass: " + classResult.eventClass.name + " - BaseClassId: " + classResult.eventClass.baseClassId +  " och ClassType id: " + classResult.eventClass.classTypeId + " - EventClassStatus: " + classResult.eventClass.eventClassStatus );
        }

        resultat.baseClass = asBasklass(resultat.baseClassId, classResult, event.eventId);
        resultat.maxpoang = resultat.baseClass.getMaxpoang();

        // ************************ for Helena ***************** //


        String id = resultat.lopare.eventorId;
        String p = resultat.lopare.fornamn + "  " + resultat.lopare.fornamn;
        String t = resultat.tavling.namn;
        String kl = resultat.tavling.eventClassificationName;
        int po = resultat.poang;
        int pl = resultat.placering;
        String bkl = resultat.baseClass.name();
        int x = resultat.maxpoang;
        int bx = resultat.baseClassId;
        int cx = resultat.classTypeId;
        String ex = classResult.eventClass.name;

        String f = "no date";
        if (resultat.lopare.fodelseDatum != null) {
            f = resultat.lopare.fodelseDatum.toString();
        }
        //System.out.println("MAPPER: Resultat för: " + id + " - " + t + " - klass: " + bkl + "  - maxpoäng: " + x + "  (EventClass: "  + ex + " BaseClassId = " + bx + " och ClassType id: " + cx + ")");

        // ************************ for Helena ***************** //

        return resultat;
    }

    public static Resultat asIndSingleDayResult(Event event, ClassResult classResult, String eventorIdPerson) {


        Resultat resultat = asResultat(event, classResult, eventorIdPerson);
        populate(resultat, classResult.personResult.get(0).result);

        return resultat;
    }

    public static Resultat asRelaySingleDayResult(Event event) {
        Resultat resultat = new Resultat();
        resultat.tavling = asTavling(event);
        return resultat;
    }

    public static Resultat asPatrolSingleDayResult(Event event) {
        Resultat resultat = new Resultat();
        resultat.tavling = asTavling(event);
        return resultat;
    }

    private static void populate(Resultat resultat, Result result) {
        resultat.status = result.competitorStatus.value;
        resultat.placering = result.resultPosition;
        resultat.timeDiff = result.timeDiff;
        resultat.poangReduktion = getPoangReduktion(resultat.timeDiff);

        resultat.poang = 0;
        if (resultat.status.equals("OK")) {
            if (resultat.maxpoang > 0) {
                if (resultat.baseClass == BaseClass.INSKOLNING) {
                    resultat.poang = 10;
                } else {
                    resultat.poang = resultat.maxpoang - resultat.poangReduktion;
                }
            } else {
                //System.out.println("MAPPER: Maxpoäng för tävling : " + resultat.tavling.namn + " är " + resultat.maxpoang);
            }
        }
    }


    public static List<Resultat> asIndMultiDayResult(Event event, ClassResult classResult, String eventorIdPerson) {
        List<Resultat> allResultat = new ArrayList<>();

        for (PersonResult pr : classResult.personResult) {

            Resultat resultat = asResultat(event, classResult, eventorIdPerson);
//            System.out.println("Tävling: " + event.name +
//                    " - baseClassId: " + classResult.eventClass.baseClassId +
//                    " - classTypeId: " + classResult.eventClass.classTypeId +
//                    " - name " + classResult.eventClass.name);
            if (event.eventForm.equals("IndSingleDay")) {
                populate(resultat, pr.result);

            } else if (event.eventForm.equals("IndMultiDay")) {
                populate(resultat, pr.raceResult.result);
            } else {
                throw new RuntimeException("Ogilitg EventForm: " + event.eventForm);
            }

            allResultat.add(resultat);

        }

        return allResultat;
    }

    private static int getPoangReduktion(String timeDiff) {
        if (timeDiff == null) {
            return 0;
        }
        int index = timeDiff.indexOf(":");
        int minutesAfter = Integer.parseInt(timeDiff.substring(0, index));
        return minutesAfter;
    }

    public static Tavling asTavling(Event event) {
        Tavling tavling = new Tavling();

        tavling.namn = event.name;
        tavling.eventorId = event.eventId;

        if (event.eventClassification != null) {
            tavling.eventClassificationId = event.eventClassification.eventClassificationId;
            tavling.eventClassificationName = event.eventClassification.name;
        } else {
            tavling.eventClassificationId = event.eventClassificationId;
        }

        tavling.disciplineId = event.disciplineId;
        tavling.eventStatusId = event.eventStatusId;
        tavling.eventForm = event.eventForm;


        //System.out.println("Tävling: " + tavling.eventorId + " - " + tavling.namn + " - status: " + tavling.eventStatusId + " - form: " + tavling.eventForm) ;
        return tavling;
    }

    public static Lopare asLopare(String eventorIdPerson) {
        Lopare lopare = new Lopare();
        lopare.eventorId = eventorIdPerson;
        return lopare;
    }

    public static BaseClass asBasklass(int baseClassId, ClassResult classResult, String eventId) {


        if (INGNORABLE_EVENT_IDS.contains(eventId)) {
            //System.out.println("MAPPER.asBasKlass: Ignorerar tävling " + eventId);
            return BaseClass.MAX_0;
        }

        if (baseClassId == 0 && classResult.eventClass.classTypeId == 17) {
            //System.out.println("MAPPER.asBasKlass: BaseclassId = 0 - använder " + BaseClass.MAX_100.name() + " istället.");
            return BaseClass.MAX_100;
        }

        if (baseClassId == 0 && classResult.eventClass.classTypeId == 18) {
            //System.out.println("MAPPER.asBasKlass: BaseclassId = 0 - använder " + BaseClass.MAX_50.name() + " istället.");
            return BaseClass.MAX_50;
        }

        if (baseClassId == 0 && classResult.eventClass.classTypeId == 19) {
            //System.out.println("MAPPER.asBasKlass: BaseclassId = 0 - ClassTypeId 19 - NO RETURN");
        }

        for (BaseClass baseClass : BaseClass.values()) {
            //System.out.println( "I for loopen - baseklass.getKlass: " + baseClass.getKlass() + "  - baseclass: " + baseClass);
            if (baseClass.getKlass() == baseClassId) {
                return baseClass;
            }
        }

        //System.out.println("Ogiltig tävling:  " + eventId);
        //return BaseClass.OGILTIG;
//        System.out.println("MAPPER.asBasKlass:  Classresult:  baseClassid: " + classResult.eventClass.baseClassId +
//                " - classTypeId: " + classResult.eventClass.classTypeId +
//                " - name: " + classResult.eventClass.name);

        throw new RuntimeException("Invalid k: " + baseClassId);
    }
}
