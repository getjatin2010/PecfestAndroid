package in.pecfest.www.pecfest.Activites;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import java.util.ArrayList;
import in.pecfest.www.pecfest.Adapters.EventsAdapter.EventsData;
import in.pecfest.www.pecfest.Adapters.EventsAdapter;
import in.pecfest.www.pecfest.Communication.VolleyBase;
import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.Utility;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.MenuItem;
import android.widget.Toast;
import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.content.res.Resources;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import android.os.AsyncTask;
import org.json.JSONObject;
import org.json.JSONArray;

public class Events extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<EventsData> eventsList, eventsListTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.events_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        eventsList= new ArrayList<EventsData>();

        VolleyBase vbreq= VolleyBase.getInstance(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utility.getBaseUrl(this)+"events.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                       Log.v("Response", response);
                        new ProcessEventsJson().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Response", error.getMessage());
            }
        });

        vbreq.addToRequestQueue(stringRequest);

//        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Toast.makeText(getApplicationContext(), "You clicked the item"+ eventsList.get(position).getEventId(),Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        showEventsGrid();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.events, menu);
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
                for(EventsData item: eventsList){
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
        return true;
    }

    private class ProcessEventsJson extends AsyncTask<String, Void, String>{
        protected String doInBackground(String...qs){
            String res= qs[0];
            try {
                JSONObject jo = new JSONObject(res);
                if(Boolean.parseBoolean(jo.getString("valid"))){
                    int size= Integer.parseInt(jo.getString("count"));
                    JSONArray ja= jo.optJSONArray("events");
                    for(int i=0; i<size;i++){
                        JSONObject job= ja.getJSONObject(i);
                        eventsList.add(i,new EventsData(job.getString("id"), job.getString("event"), job.getString("club"), job.getString("desc"), job.getString("date")));
                    }
                }
                eventsListTemp.clear();
                eventsListTemp.addAll(eventsList);
                return "done";
            }
            catch(Exception e){
                Log.v("Json error:", e.getMessage());
            }
            return "error";
        }
        protected void onPostExecute(String s){
            if(s.equals("done"))
                mAdapter.notifyDataSetChanged();
        }
    }

    private void showEventsGrid(){

        eventsList.add(new EventsData("1","Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
        eventsList.add(new EventsData("2","Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
        eventsList.add(new EventsData("3","Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
        eventsList.add(new EventsData("4","Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
        eventsList.add(new EventsData("5","Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
        eventsList.add(new EventsData("6","Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
        eventsList.add(new EventsData("7","Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
        eventsList.add(new EventsData("8","Hackathon", "Ieee", "Make the apps in an overnight hackathon. Win great prizes. Fist prize: 35k.", "8th Oct"));
        eventsList.add(new EventsData("9","Hackathon", "Ieee", "Make the apps in an overnight hackathon. Win great prizes. Fist prize: 35k.", "8th Oct"));
        eventsList.add(new EventsData("10","Hackathon", "Ieee", "Make the apps in an overnight hackathon. Win great prizes. Fist prize: 35k.", "8th Oct"));
        eventsList.add(new EventsData("11","Hackathon", "Ieee", "Make the apps in an overnight hackathon. Win great prizes. Fist prize: 35k.", "8th Oct"));
        eventsList.add(new EventsData("12","Hackathon", "Ieee", "Make the apps in an overnight hackathon. Win great prizes. Fist prize: 35k.", "8th Oct"));
        eventsList.add(new EventsData("13","Hackathon", "Ieee", "Make the apps in an overnight hackathon. Win great prizes. Fist prize: 35k.", "8th Oct"));
        eventsList.add(new EventsData("14","Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
        eventsList.add(new EventsData("15","Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
        eventsList.add(new EventsData("16","Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
        eventsList.add(new EventsData("17","Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
        eventsList.add(new EventsData("18","Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
        eventsList.add(new EventsData("19","Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
        eventsList.add(new EventsData("20","Plane making", "Asce", "Make planes that fly the distance. Win many cool prizes. First prize 5k cash.", "7th Oct"));
        eventsList.add(new EventsData("21","Hackathon", "Ieee", "Make the apps in an overnight hackathon. Win great prizes. Fist prize: 35k.", "8th Oct"));
        eventsList.add(new EventsData("22","Hackathon", "Ieee", "Make the apps in an overnight hackathon. Win great prizes. Fist prize: 35k.", "8th Oct"));

        eventsListTemp= new ArrayList<EventsData>();
        eventsListTemp.addAll(eventsList);
        mAdapter = new EventsAdapter(eventsListTemp, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Events.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Events.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
