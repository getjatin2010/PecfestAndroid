package pecapps.pecfest2015.Model.EventRegister;

/**
 * Created by jatin on 6/9/16.
 */

import java.util.ArrayList;
public class EventRegisterRequest {
    String eventId;
    ArrayList<String> members;

    public EventRegisterRequest( String e, ArrayList<String> mm){

        eventId=e;
        members=mm;
    }
}
