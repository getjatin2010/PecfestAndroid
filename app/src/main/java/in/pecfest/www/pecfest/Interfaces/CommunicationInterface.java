package in.pecfest.www.pecfest.Interfaces;


import in.pecfest.www.pecfest.Model.Common.Response;

/**
 * Created by Pradeep on 19-01-2016.
 */
public interface CommunicationInterface {
    void onRequestCompleted(String method, Response rr);
}
