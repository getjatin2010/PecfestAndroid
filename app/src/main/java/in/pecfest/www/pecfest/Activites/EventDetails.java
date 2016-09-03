package in.pecfest.www.pecfest.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.pecfest.www.pecfest.Adapters.EventRegisterAdapter;
import in.pecfest.www.pecfest.Model.EventDetails.Event;
import in.pecfest.www.pecfest.R;

public class EventDetails extends AppCompatActivity {

    TextView tx1, tx2, tx3;
    EditText et1;
    ImageView iv1;
    ListView lv1;
    Button bt1,bt2;
    EventRegisterAdapter registerAdapter;
    ArrayList<String> registrantsList;
    ViewGroup header;
    String pecfestEventId="";
    Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        pecfestEventId= getIntent().getStringExtra("pecfestEventId");
        Log.v("eventId",pecfestEventId);
        for(int i=0; i<Events.globalEventsList.size();i++){
            if(Events.globalEventsList.get(i).eventId==Integer.parseInt(pecfestEventId)){
                event= Events.globalEventsList.get(i);
                break;
            }
        }
        lv1= (ListView) findViewById(R.id.register_list_view);
        header= (ViewGroup) getLayoutInflater().inflate(R.layout.event_register_header, lv1, false);
        lv1.addHeaderView(header);


        tx1= (TextView) header.findViewById(R.id.event_name);
        tx2= (TextView) header.findViewById(R.id.club_name);
        tx3= (TextView) header.findViewById(R.id.event_details);
        et1= (EditText) findViewById(R.id.event_register_id);
        iv1= (ImageView) header.findViewById(R.id.event_image);
        bt1= (Button) findViewById(R.id.btn_event_register);
        bt2= (Button) findViewById(R.id.btn_event_submit);

        registrantsList= new ArrayList<String>();
        registerAdapter= new EventRegisterAdapter(this, registrantsList);
        lv1.setAdapter(registerAdapter);

        tx1.setText(event.eventName);
        tx2.setText(event.clubName);
        tx3.setText(event.eventDetails);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRegistrant();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                submitRegistrants();
            }

        });

    }

    public void addRegistrant(){
        String r= et1.getText().toString();
        if(r.isEmpty())
            return;
        if(registrantsList.size()>=event.maxSize){
            Toast.makeText(this, "Maximum limit reached!", Toast.LENGTH_SHORT).show();
            return;
        }
        et1.setText("");
        registrantsList.add(r);
        registerAdapter.notifyDataSetChanged();
    }

    public void submitRegistrants(){
        if(registrantsList.size()<event.minSize){
            Toast.makeText(this, "Not enough members to participate in the event!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

}
