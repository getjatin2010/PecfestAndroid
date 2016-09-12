package in.pecfest.www.pecfest.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.ArrayList;

import in.pecfest.www.pecfest.Model.RegisteredEvents.RegisteredEvent;
import in.pecfest.www.pecfest.R;

public class RegisteredEvents extends AppCompatActivity {

    ListView lv;
    ArrayList<RegisteredEvent> rel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_events);

        rel= new ArrayList<RegisteredEvent>();
        lv=(ListView) findViewById(R.id.registered_list);
        
    }
}
