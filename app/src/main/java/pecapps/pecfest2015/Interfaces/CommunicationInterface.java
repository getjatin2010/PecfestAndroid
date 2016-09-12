package pecapps.pecfest2015.Interfaces;


import pecapps.pecfest2015.Model.Common.Response;

/**
 * Created by Pradeep on 19-01-2016.
 */
public interface CommunicationInterface {
    void onRequestCompleted(String method, Response rr);
}
