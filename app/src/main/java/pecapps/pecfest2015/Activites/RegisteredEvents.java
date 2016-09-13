package pecapps.pecfest2015.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import pecapps.pecfest2015.Adapters.RegisteredEventsAdapter;
import pecapps.pecfest2015.Interfaces.CommunicationInterface;
import pecapps.pecfest2015.Model.Common.Constants;
import pecapps.pecfest2015.Model.Common.Request;
import pecapps.pecfest2015.Model.Common.Response;
import pecapps.pecfest2015.Model.RegisteredEvents.RegisteredEvent;
import pecapps.pecfest2015.Model.RegisteredEvents.RegisteredEventRequest;
import pecapps.pecfest2015.Model.RegisteredEvents.RegisteredEventResponse;
import pecapps.pecfest2015.R;
import pecapps.pecfest2015.Utilites.Utility;

public class RegisteredEvents extends AppCompatActivity implements CommunicationInterface {

    ListView lv;
    ArrayList<RegisteredEvent> rel;
    RegisteredEventsAdapter eventsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_events);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registered Events");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rel= new ArrayList<RegisteredEvent>();
        lv=(ListView) findViewById(R.id.registered_listview);

        notifyChanges();
    }

    public void notifyChanges(){

        RegisteredEventRequest rr = new RegisteredEventRequest();
        rr.pecfestId = Utility.getsaveId(this).pecfestId;

        Request r = new Request();
        r.hidePleaseWaitAtEnd = true;
        r.showPleaseWaitAtStart = true;
        r.method = Constants.METHOD.REGISTERED_EVENT;
        r.requestData = Utility.GetJsonObject(rr);

        Utility.SendRequestToServer(this, r);

    }

    @Override
    public void onRequestCompleted(String method, Response rr) {
        if(rr.isSuccess==false)
        {
            Toast.makeText(this, rr.errorMessage, Toast.LENGTH_LONG).show();
        }
        if(method.equalsIgnoreCase(Constants.METHOD.REGISTERED_EVENT))
        {
            RegisteredEventResponse rrr =(RegisteredEventResponse) Utility.getObjectFromJson(rr.JsonResponse,RegisteredEventResponse.class);
                rel= rrr.eventList;
                eventsAdapter= new RegisteredEventsAdapter(this, rel);
                lv.setAdapter(eventsAdapter);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
