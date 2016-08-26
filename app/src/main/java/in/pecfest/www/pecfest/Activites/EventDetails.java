package in.pecfest.www.pecfest.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.util.Log;
import java.util.ArrayList;

import in.pecfest.www.pecfest.Adapters.EventRegisterAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        pecfestEventId= getIntent().getStringExtra("pecfestEventId");
        Log.v("eventId",pecfestEventId);
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

        tx3.setText(tx3.getText()+"Very big event. A number of great awards. Great fun. Lots of enjoyment. Come and bring all your friends. This is" +
                "a event with a lot of description. So it is a big event");
        registrantsList= new ArrayList<String>();
        registerAdapter= new EventRegisterAdapter(this, registrantsList);
        lv1.setAdapter(registerAdapter);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRegistrant();
            }
        });
    }

    public void addRegistrant(){
        String r= et1.getText().toString();
        if(r.isEmpty())
            return;
        et1.setText("");
        registrantsList.add(r);
        registerAdapter.notifyDataSetChanged();
    }


}
