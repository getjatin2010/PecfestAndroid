package in.pecfest.www.pecfest.Activites;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.util.ArrayList;
import in.pecfest.www.pecfest.Adapters.EventsAdapter.EventsData;
import in.pecfest.www.pecfest.Adapters.EventsAdapter;
import in.pecfest.www.pecfest.R;

public class Events extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.events_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ArrayList<EventsData> eventsList = new ArrayList<EventsData>();
        eventsList.add(new EventsData("Asce", "plane making event"));
        eventsList.add(new EventsData("Apc", "FacePainting"));
        eventsList.add(new EventsData("Edc", "Apprentice"));
        eventsList.add(new EventsData("Pdc", "photography"));
        eventsList.add(new EventsData("Pdc", "Video making"));
        eventsList.add(new EventsData("Pdc", "nature shots"));
        eventsList.add(new EventsData("Hindi ed board", "Shero Shayari"));
        eventsList.add(new EventsData("English ed board", "Poetry"));
        eventsList.add(new EventsData("Edc", "IPL"));
        eventsList.add(new EventsData("Apc", "Collage"));
        eventsList.add(new EventsData("Ieee", "Break the code"));
        eventsList.add(new EventsData("Ieee", "Hackathon"));
        eventsList.add(new EventsData("Music", "Antakshri"));

        mAdapter = new EventsAdapter(eventsList);
        mRecyclerView.setAdapter(mAdapter);
    }

}
