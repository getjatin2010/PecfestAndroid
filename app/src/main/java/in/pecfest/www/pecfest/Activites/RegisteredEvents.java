package in.pecfest.www.pecfest.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import in.pecfest.www.pecfest.Adapters.RegisteredEventsAdapter;
import in.pecfest.www.pecfest.Interfaces.CommunicationInterface;
import in.pecfest.www.pecfest.Model.Common.Constants;
import in.pecfest.www.pecfest.Model.Common.Request;
import in.pecfest.www.pecfest.Model.Common.Response;
import in.pecfest.www.pecfest.Model.RegisteredEvents.RegisteredEvent;
import in.pecfest.www.pecfest.Model.RegisteredEvents.RegisteredEventRequest;
import in.pecfest.www.pecfest.Model.RegisteredEvents.RegisteredEventResponse;
import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.Utility;

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
