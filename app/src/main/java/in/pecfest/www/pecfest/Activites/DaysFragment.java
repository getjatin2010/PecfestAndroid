package in.pecfest.www.pecfest.Activites;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.pecfest.www.pecfest.Adapters.EventsAdapter;
import in.pecfest.www.pecfest.Model.EventDetails.Event;
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
    private SwipeRefreshLayout srl;

    public DaysFragment(){
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        showDay = getArguments().getInt("day");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.days_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.events_recycler_view);
        srl= (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        eventsList= new ArrayList<EventsAdapter.EventsData>();
        eventsListTemp= new ArrayList<EventsAdapter.EventsData>();
        mAdapter = new EventsAdapter(eventsListTemp, getContext());
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                ((Events)getActivity()).refreshList();
            }
        });
        notifyChanges();
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
//                if (query.isEmpty()) {
//                    eventsListTemp.clear();
//                    eventsListTemp.addAll(eventsList);
//                    return false;
//                }
//
//                eventsListTemp.clear();
//                query = query.toLowerCase();
//                for (EventsAdapter.EventsData item : eventsList) {
//                    if (item.eventName.toLowerCase().contains(query) || item.clubName.toLowerCase().contains(query)) {
//                        eventsListTemp.add(item);
//                    }
//                }
//                mAdapter.notifyDataSetChanged();
               return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
//                if (query.isEmpty()) {
//                    eventsListTemp.clear();
//                    eventsListTemp.addAll(eventsList);
//                    mAdapter.notifyDataSetChanged();
//                }
//
//                return false;

                if (query.isEmpty()) {
                    eventsListTemp.clear();
                    eventsListTemp.addAll(eventsList);
                    return false;
                }

                eventsListTemp.clear();
                query = query.toLowerCase();
                for (EventsAdapter.EventsData item : eventsList) {
                    if (item.eventName.toLowerCase().contains(query) || item.clubName.toLowerCase().contains(query) || item.eventType.toLowerCase().contains(query)) {
                        eventsListTemp.add(item);
                    }
                }
                mAdapter.notifyDataSetChanged();
                return false;

            }
        });

    }

    public void notifyChanges(){
        addAllModed();
        srl.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
    }

    public void addAllModed(){

        if(eventsList==null)
            eventsList=new ArrayList<EventsAdapter.EventsData>();

        eventsList.clear();
        for(int i=0; i<Events.globalEventsList.size();i++){
            Event ev= Events.globalEventsList.get(i);
            if(ev.day==showDay){
                eventsList.add(new EventsAdapter.EventsData(""+ev.eventId, ev.eventName, ev.clubName, (ev.eventDetails!=null && ev.eventDetails.length()>50)?(ev.eventDetails.substring(0,50)+"..."): ev.eventDetails, ev.location+", "+ev.time,ev.eventTypeName));
            }
        }
        eventsListTemp.clear();
        eventsListTemp.addAll(eventsList);

    }

}
