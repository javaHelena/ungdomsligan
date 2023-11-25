package se.edtek.olresult.process;

public class Application {

    /**
     * This is the main entry point for the application.
     *
     * This will DELETE all tables (except tmp)
     *
     * This will fetch and store all runners.
     * Then fetch and store all results and events.
     * Then fetch additional information for all events
     * Then calculate Ungdomsligan for all runners
     *
     *
     * @param args
     */

    public static void main(String[] args) {

        DropAllData.main(args);

        FetchMedlemmar.main(args);
        FetchResultat.main(args);
        FetchTavlingar.main(args);
        CalculateResult.main(args);
    }
}
