package in.pecfest.www.pecfest.Activites;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;

import in.pecfest.www.pecfest.Adapters.EventsAdapter;
import in.pecfest.www.pecfest.R;

/**
 * Created by Abhi on 24-08-2016.
 */
public class DaysFragment extends Fragment {

    private int showDay= 1;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<EventsAdapter.EventsData> eventsList, eventsListTemp;

    public DaysFragment(){

    }

    public DaysFragment(int day){
        showDay=day;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.days_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.events_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();
        showEventsGrid();
    }

        @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.events, menu);
        super.onCreateOptionsMenu(menu,inflater);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.isEmpty()){
                    eventsListTemp.clear();
                    eventsListTemp.addAll(eventsList);
                    return false;
                }

                eventsListTemp.clear();
                query = query.toLowerCase();
                for(EventsAdapter.EventsData item: eventsList){
                    if(item.eventName.toLowerCase().contains(query) || item.clubName.toLowerCase().contains(query)){
                        eventsListTemp.add(item);
                    }
                }
                mAdapter.notifyDataSetChanged();
                return false;
             }

            @Override
            public boolean onQueryTextChange(String query) {
                if(query.isEmpty()){
                    eventsListTemp.clear();
                    eventsListTemp.addAll(eventsList);
                    mAdapter.notifyDataSetChanged();
                }

                return false;
            }
        });

    }

    private void showEventsGrid(){

        eventsList= new ArrayList<EventsAdapter.EventsData>();
        eventsList.add(new EventsAdapter.EventsData("1","Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
        eventsList.add(new EventsAdapter.EventsData("2","Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
        eventsList.add(new EventsAdapter.EventsData("3","Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
        eventsList.add(new EventsAdapter.EventsData("4","Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));

        if(showDay!=2) {
            eventsList.add(new EventsAdapter.EventsData("5", "Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
            eventsList.add(new EventsAdapter.EventsData("6", "Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
            eventsList.add(new EventsAdapter.EventsData("7", "Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
            eventsList.add(new EventsAdapter.EventsData("8", "Hackathon", "Ieee", "Make the apps in an overnight hackathon. Win great prizes. Fist prize: 35k.", "8th Oct"));
            eventsList.add(new EventsAdapter.EventsData("9", "Hackathon", "Ieee", "Make the apps in an overnight hackathon. Win great prizes. Fist prize: 35k.", "8th Oct"));
            eventsList.add(new EventsAdapter.EventsData("10", "Hackathon", "Ieee", "Make the apps in an overnight hackathon. Win great prizes. Fist prize: 35k.", "8th Oct"));
            eventsList.add(new EventsAdapter.EventsData("11", "Hackathon", "Ieee", "Make the apps in an overnight hackathon. Win great prizes. Fist prize: 35k.", "8th Oct"));
            eventsList.add(new EventsAdapter.EventsData("12", "Hackathon", "Ieee", "Make the apps in an overnight hackathon. Win great prizes. Fist prize: 35k.", "8th Oct"));
        }

        if(showDay!=3) {


            eventsList.add(new EventsAdapter.EventsData("13", "Hackathon", "Ieee", "Make the apps in an overnight hackathon. Win great prizes. Fist prize: 35k.", "8th Oct"));
            eventsList.add(new EventsAdapter.EventsData("14", "Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
            eventsList.add(new EventsAdapter.EventsData("15", "Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
            eventsList.add(new EventsAdapter.EventsData("16", "Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
            eventsList.add(new EventsAdapter.EventsData("17", "Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
            eventsList.add(new EventsAdapter.EventsData("18", "Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
            eventsList.add(new EventsAdapter.EventsData("19", "Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
            eventsList.add(new EventsAdapter.EventsData("20", "Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
            eventsList.add(new EventsAdapter.EventsData("21", "Hackathon", "Ieee", "Make the apps in an overnight hackathon. Win great prizes. Fist prize: 35k.", "8th Oct"));
            eventsList.add(new EventsAdapter.EventsData("22", "Hackathon", "Ieee", "Make the apps in an overnight hackathon. Win great prizes. Fist prize: 35k.", "8th Oct"));
        }

        eventsListTemp= new ArrayList<EventsAdapter.EventsData>();
        eventsListTemp.addAll(eventsList);
        mAdapter = new EventsAdapter(eventsListTemp, getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

}
