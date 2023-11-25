package se.edtek.olresult.process;

import se.edtek.olresult.db.LopareDAO;
import se.edtek.olresult.db.ResultatDAO;
import se.edtek.olresult.db.TavlingDAO;

public class DropAllData extends AbstractProcess {


    public void run() {
        System.out.println("Dropping all data!");
        getDBI().onDemand(ResultatDAO.class).deleteAll();
        getDBI().onDemand(TavlingDAO.class).deleteAll();
        getDBI().onDemand(LopareDAO.class).deleteAll();
    }

    public static void main(String[] args) {
        new DropAllData().run();
    }

}
