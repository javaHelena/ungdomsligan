package se.edtek.olresult.mapper;

import se.edtek.olresult.eventormodel.ClassResult;
import se.edtek.olresult.eventormodel.Event;
import se.edtek.olresult.eventormodel.Person;
import se.edtek.olresult.eventormodel.PersonResult;
import se.edtek.olresult.eventormodel.Result;
import se.edtek.olresult.internalmodel.BaseClass;
import se.edtek.olresult.internalmodel.Lopare;
import se.edtek.olresult.internalmodel.Resultat;
import se.edtek.olresult.internalmodel.Tavling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mapper {

    private static final List<String> INGNORABLE_EVENT_IDS =
            Arrays.asList(
                    "38907",                                            //Daladubbeln patrull
                    "47362",                                            //Falu Indoor etapp 1
                    "43305",                                            //Gävle Indoor
                    //"46135",                                          //Höglands-OL Anneberg
                    // "47575",                                         //Luffarligan
                    // "42704", "42887", "42908",                       //Mareld Stockholm by night
                    "46730",                                            //MiniKM
                    // "44506",                                         //Motionsorientering Ursvik
                    // "45850",                                         //Motionsorientering nationaldagen
                    "44379",                                            //MTBO Uppsala
                    "43656",                                            //Oringen träningsbanor
                    // "46607",                                         //Roslagsveteranerna
                    // "42801",                                         //Skärgårdssprinten
                    "42765", "42766",                                   //SkidO
                    // "42681",                                         //Stockholm by night
                    "43588", "43589",                                   //Stockholm Indoor
                    //  "46951",                                        //USM öppna klasser
                    "46918"                                             //Viken indoor
                    // "44123",                                         //Vintercupen #10 HärlövsIF
                    // "47743", "43771", "43785", "43982", "44443"      //VNC Vinternatt Cup 1, 3, 4, 5, 6
                    // "44272", "43935", "43946", "44239"               //Vinterserien
            );

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
        resultat.baseClass = asBasklass(resultat.baseClassId, classResult, event);
        resultat.maxpoang = resultat.baseClass.getMaxpoang();

//        System.out.println(resultat.tavling.eventorId + ";"
//                + resultat.tavling.namn +  "; "
//                + resultat.klass + "; "
//                + resultat.baseClassId +  "; "
//                + resultat.classTypeId + "; "
//                + resultat.baseClass.name() + "; "
//                + resultat.baseClass.getMaxpoang() + "; "
//                + resultat.baseClass.getKlass()
//                + classResult.eventClass.eventClassStatus );

        // ************************ for Helena ***************** //

        String id = resultat.lopare.eventorId;
        String p = resultat.lopare.fornamn + "  " + resultat.lopare.fornamn;
        String t = resultat.tavling.namn;
        String tid = resultat.tavling.eventorId;
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
        //System.out.println("MAPPER.asResultat: Resultat för löpare: " + id + " - " + tid + " - " + t + " - klass: " + bkl + "  - maxpoäng: " + x + "  (EventClass: "  + ex + " BaseClassId = " + bx + " och ClassType id: " + cx + ")");

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
                    if (resultat.poang < 0) {
                        resultat.poang = 10;
                    }
                }
            }
        } else if (resultat.status.equals("MisPunch")) {
            resultat.poang = 1;
        }
    }


    public static List<Resultat> asIndMultiDayResult(Event event, ClassResult classResult, String eventorIdPerson) {
        List<Resultat> allResultat = new ArrayList<>();

        for (PersonResult pr : classResult.personResult) {

            Resultat resultat = asResultat(event, classResult, eventorIdPerson);
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
        return calculateTotalMinutes(timeDiff);
    }

    private static int calculateTotalMinutes(String timeDiff) {
        String[] parts = timeDiff.split(":");
        int hours = 0;
        int minutes;

        if (parts.length == 3) {
            hours = Integer.parseInt(parts[0]);
            minutes = Integer.parseInt(parts[1]);
            // Log if the time difference is over an hour
            int minutesAfter = hours * 60 + minutes;
            //System.out.println("The time difference for is over an hour: " + timeDiff + ". Minutes after: " + minutesAfter);
        } else if (parts.length == 2) {
            minutes = Integer.parseInt(parts[0]);
            // Optionally, handle seconds as well
            // int seconds = Integer.parseInt(parts[1]);
        } else {
            throw new IllegalArgumentException("Invalid time format for " + timeDiff);
        }
        return hours * 60 + minutes;
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

        return tavling;
    }

    public static Lopare asLopare(String eventorIdPerson) {
        Lopare lopare = new Lopare();
        lopare.eventorId = eventorIdPerson;
        return lopare;
    }

    public static BaseClass asBasklass(int baseClassId, ClassResult classResult, Event event) {
        // ClassResult.eventClass.classTypeId:
        // 16, 17 vanlig
        // 18 Inskolning, u-klasser (stämmer inte riktigt dock
        // 19 öppna

        if (INGNORABLE_EVENT_IDS.contains(event.eventId)) {
            //System.out.println("Ignorerar tävling " + event.eventId + " - " + event.name + " - " + classResult.eventClass.name + " - " + baseClassId + "( "+ classResult.eventClass.classTypeId + ")");
            return BaseClass.MAX_0;
        }

        if (baseClassId == 0 && classResult.eventClass.classTypeId == 17) {   // Ska inte alltid vara Base Class MAX_100, därav specialkoll

            if (classResult.eventClass.name.contains("Kort")){
                return BaseClass.MAX_80;  // Kortklasser ska vara MAX_80
            }

            if (event.eventId.equals("42887")){ //Mareld Stockholm by night
                return BaseClass.MAX_10;  // Alla Mareld ska ge 10 poöng. Ej HD-klasser
            }

            if( event.eventId.equals("42751") && classResult.eventClass.name.equals("Motion") ) {
                return BaseClass.MAX_10; //Höstlunken motion
            }

            return BaseClass.MAX_100;
        }

        if (baseClassId == 0 && classResult.eventClass.classTypeId == 18) {
            //System.out.println("MAPPER.asBasKlass: BaseclassId = 0 type id 18 - använder " + BaseClass.MAX_50.name() + " istället.");
            //System.out.println("0 & 18;" + eventId + ";" + classResult.eventClass.name + ";" + baseClassId + ";" + classResult.eventClass.classTypeId + ";" + BaseClass.MAX_50.name() + ";" + BaseClass.MAX_50.getMaxpoang());
            return BaseClass.MAX_50;
        }

        if (baseClassId == 0 && classResult.eventClass.classTypeId == 19) {

            if (Arrays.asList("U1", "U2", "U3", "U4").contains(classResult.eventClass.name)) {
                return BaseClass.NOLL_50; //Vanligtvis U-klasser
            }

            return BaseClass.MAX_10;  //Resten Öppna klasser
        }

        if (Arrays.asList("U1", "U2", "U3", "U4").contains(classResult.eventClass.name) && event.eventId.equals("47833")) {
            return BaseClass.NOLL_50; //Nordcupen #4 U2, U3
        }

        BaseClass currentBaseClass = BaseClass.fromBaseClassId(baseClassId);
        if(currentBaseClass != null) {
            //System.out.println("currentBaseClass != null;" + eventId + ";" + classResult.eventClass.name + ";" + baseClassId + ";" + classResult.eventClass.classTypeId + ";" + currentBaseClass.name() + ";" + currentBaseClass.getMaxpoang());
            return currentBaseClass;
        }

        //INGEN MATCHNING
        System.out.println("Ogiltig tävling:  " + event.eventId);
//        return BaseClass.OGILTIG;
//        System.out.println("MAPPER.asBasKlass:  Classresult:  baseClassid: " + classResult.eventClass.baseClassId +
//                " - classTypeId: " + classResult.eventClass.classTypeId +
//                " - name: " + classResult.eventClass.name);

        throw new RuntimeException("Invalid k: " + baseClassId);
    }
}
