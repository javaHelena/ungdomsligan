package se.edtek.olresult;


import se.edtek.olresult.eventormodel.Event;
import se.edtek.olresult.eventormodel.PersonList;
import se.edtek.olresult.eventormodel.ResultList;
import se.edtek.olresult.eventormodel.ResultListList;
import se.edtek.olresult.internalmodel.Lopare;
import se.edtek.olresult.internalmodel.Resultat;
import se.edtek.olresult.internalmodel.Tavling;
import se.edtek.olresult.mapper.Mapper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EventorClient {

    String apiKey = "b05deb67157844d8bfd1f341555d70f2";
    String uri = "https://eventor.orientering.se";

    public List<Lopare> getLopare(String organisationId) {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(uri).path("api/persons/organisations/" + organisationId);

        PersonList pl = doGet(target, PersonList.class);

        return pl.persons.stream().map(p -> Mapper.asLopare(p)).collect(Collectors.toList());
    }

    public List<Resultat> getResultat(String loparEventorId, LocalDate from, LocalDate to) {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(uri).path("api/results/person");

        String fromDate = from.toString();
        String toDate = to.toString();

        target = target.queryParam("personId", loparEventorId).queryParam("fromDate", fromDate).queryParam("toDate", toDate);

        ResultListList rll = doGet(target, ResultListList.class);

        if (rll.resultLists == null || rll.resultLists.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        System.out.println("****************   Results for lopare" + loparEventorId + "   *****************");
        List<Resultat> fetchedResults = new ArrayList<>();
        for (ResultList rl : rll.resultLists) {
            System.out.println("Event: " + rl.event.name);
            if (rl.event.eventForm.equals("RelaySingleDay")) {
                fetchedResults.add(Mapper.asRelaySingleDayResult(rl.event));
            }

            else if (rl.event.eventForm.equals("IndMultiDay")) {
                fetchedResults.addAll(Mapper.asIndMultiDayResult(rl.event, rl.classResult, loparEventorId));
            }

            else if (rl.event.eventForm.equals("IndSingleDay")) {
                fetchedResults.add(Mapper.asIndSingleDayResult(rl.event, rl.classResult, loparEventorId));
            }
            else if (rl.event.eventForm.equals("PatrolSingleDay")) {
                fetchedResults.add(Mapper.asPatrolSingleDayResult(rl.event));
            }
            else {
                throw new RuntimeException("Unknown eventForm: " + rl.event.eventForm);
            }

        }

        return fetchedResults;
    }

    public Tavling getTavling(String eventId) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(uri).path("api/event/" + eventId);

        Event event = doGet(target, Event.class);

        return Mapper.asTavling(event);
    }

    private <T> T doGet(WebTarget target, Class<T> clazz) {
        return target.request().header("ApiKey", apiKey).get().readEntity(clazz);
    }
}
