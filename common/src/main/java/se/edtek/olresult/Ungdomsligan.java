package se.edtek.olresult;

import se.edtek.olresult.process.*;

public class Ungdomsligan {

    public static void main(String[] args) {

        System.out.println("");
        System.out.println("");
        System.out.println(" *************** DROPPAR MEDLEMMAR ***************" );
        System.out.println("");

        DropMedlemmar.main(null);

        System.out.println("");
        System.out.println("");
        System.out.println(" *************** HÄMTAR MEDLEMMAR - POPULERAR \"LOPARE\" ***************" );
        System.out.println("");

        FetchMedlemmar.main(null);

        System.out.println("");
        System.out.println("");
        System.out.println(" *************** HÄMTAR RESULTAT - POPULERAR \"RESULTAT\", UPPDATERAR \"LOPARE\" OCH \"TAVLING\"  ***************" );
        System.out.println("");

        FetchResultat.main(null);

        System.out.println("");
        System.out.println("");
        System.out.println(" *************** HÄMTAR TÄVLINGAR - POPULERAR \"TAVLING\" **************" );
        System.out.println("");

        FetchTavlingar.main(null);

        System.out.println("");
        System.out.println("");
        System.out.println(" *************** KALKYLERAR  - ANTAL SEGRAR, ANTAL HUNDRAPOÄNGARE SAMT TOTAL  ***************" );
        System.out.println("");

        CalculateResult.main(null);
    }
}
