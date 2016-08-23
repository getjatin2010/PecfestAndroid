package in.pecfest.www.pecfest.Activites;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import in.pecfest.www.pecfest.R;

public class Events extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

//        VolleyBase vbreq= VolleyBase.getInstance(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utility.getBaseUrl(this)+"events.php",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                       Log.v("Response", response);
//                        new ProcessEventsJson().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.v("Response", error.getMessage());
//            }
//        });
//
//        vbreq.addToRequestQueue(stringRequest);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.events, menu);
//        final MenuItem item = menu.findItem(R.id.action_search);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if(query.isEmpty()){
//                    eventsListTemp.clear();
//                    eventsListTemp.addAll(eventsList);
//                    return false;
//                }
//
//                eventsListTemp.clear();
//                query = query.toLowerCase();
//                for(EventsData item: eventsList){
//                    if(item.eventName.toLowerCase().contains(query) || item.clubName.toLowerCase().contains(query)){
//                        eventsListTemp.add(item);
//                    }
//                }
//                mAdapter.notifyDataSetChanged();
//                return false;
//             }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                if(query.isEmpty()){
//                    eventsListTemp.clear();
//                    eventsListTemp.addAll(eventsList);
//                    mAdapter.notifyDataSetChanged();
//                }
//
//                return false;
//            }
//        });
//        return true;
//    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DaysFragment(1), "DAY 1");
        adapter.addFragment(new DaysFragment(2), "DAY 2");
        adapter.addFragment(new DaysFragment(3), "DAY 3");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

//    private class ProcessEventsJson extends AsyncTask<String, Void, String>{
//        protected String doInBackground(String...qs){
//            String res= qs[0];
//            try {
//                JSONObject jo = new JSONObject(res);
//                if(Boolean.parseBoolean(jo.getString("valid"))){
//                    int size= Integer.parseInt(jo.getString("count"));
//                    JSONArray ja= jo.optJSONArray("events");
//                    for(int i=0; i<size;i++){
//                        JSONObject job= ja.getJSONObject(i);
//                        eventsList.add(i,new EventsData(job.getString("id"), job.getString("event"), job.getString("club"), job.getString("desc"), job.getString("date")));
//                    }
//                }
//                eventsListTemp.clear();
//                eventsListTemp.addAll(eventsList);
//                return "done";
//            }
//            catch(Exception e){
//                Log.v("Json error:", e.getMessage());
//            }
//            return "error";
//        }
//        protected void onPostExecute(String s){
//            if(s.equals("done"))
//                mAdapter.notifyDataSetChanged();
//        }
//    }


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
