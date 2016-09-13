package pecapps.pecfest2015.Activites;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pecapps.pecfest2015.Communication.HttpConnection;
import pecapps.pecfest2015.Model.Common.Constants;
import pecapps.pecfest2015.Model.EventDetails.Event;
import pecapps.pecfest2015.Model.EventDetails.EventResponse;
import pecapps.pecfest2015.Model.MegaShows.MegaResponse;
import pecapps.pecfest2015.R;
import pecapps.pecfest2015.Utilites.Results;
import pecapps.pecfest2015.Utilites.Utility;

public class Events extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static ArrayList<Event> globalEventsList;
    public static MegaResponse megaResponse;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if((title=getIntent().getStringExtra("title"))!=null){
            if(title.equals("Lectures"))
                getSupportActionBar().setTitle("Lectures & Workshops");
            else
                getSupportActionBar().setTitle(title);
        }
        else
            title="";

        globalEventsList = new ArrayList<Event>();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if(title.equals("Shows"))
            setupViewPager2(viewPager);
        else
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if(!title.equals("Shows")) {
            new getEventsList(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else{
            new getShow().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle1 = new Bundle();
        bundle1.putInt("day", 1);
        Fragment day1 = new DaysFragment();
        day1.setArguments(bundle1);

        Bundle bundle2 = new Bundle();
        bundle2.putInt("day", 2);
        Fragment day2 = new DaysFragment();
        day2.setArguments(bundle2);

        Bundle bundle3 = new Bundle();
        bundle3.putInt("day", 3);
        Fragment day3 = new DaysFragment();
        day3.setArguments(bundle3);

        adapter.addFragment(day1, "8th Oct");
        adapter.addFragment(day2, "9th Oct");
        adapter.addFragment(day3, "10th Oct");
        viewPager.setAdapter(adapter);
    }

    private void setupViewPager2(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle1 = new Bundle();
        bundle1.putInt("day", 1);
        Fragment day1 = new ShowsFragment();
        day1.setArguments(bundle1);

        Bundle bundle2 = new Bundle();
        bundle2.putInt("day", 2);
        Fragment day2 = new ShowsFragment();
        day2.setArguments(bundle2);

        Bundle bundle3 = new Bundle();
        bundle3.putInt("day", 3);
        Fragment day3 = new ShowsFragment();
        day3.setArguments(bundle3);


        adapter.addFragment(day1, "8th Oct");
        adapter.addFragment(day2, "9th Oct");
        adapter.addFragment(day3, "10th Oct");
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

    class getEventsList extends AsyncTask<Void, Void, Void>{

        AppCompatActivity context;
        Results results;
        boolean force;
        getEventsList(AppCompatActivity context){
            this.context= context;
            force= false;
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR);
            int day = c.get(Calendar.DAY_OF_MONTH);
            String value =  String.valueOf(hour)+String.valueOf(day);
            if(value.equals(Utility.getDateHour(context)))
            {
                force = false;
            }
            else
            {
                Utility.saveDateHour(value,context);
                force = true;

            }
        }
        getEventsList(AppCompatActivity context, boolean forceNew){
            this.context= context;
            force= forceNew;
        }
        @Override
        protected Void doInBackground(Void...v){
            results= new Results();

            if(title.equals("Lectures")){
                HttpConnection hc = new HttpConnection(Utility.getBaseUrl(context), 1);
                hc.putBody("{\"method\": \"" + Constants.METHOD.LECTURE_DETAILS + "\"}");
                results = hc.getData();
                parseResponse(force);
            }
            else {

                if (!force) {
                    try {
                        results.data = Utility.getSharedPreferences(context).getString("completeEventsList", null);
                        parseResponse(force);
                        if (globalEventsList.size() <= 0)
                            throw new Exception();
                    } catch (Exception e) {
                        force = true;
                    }
                }

                if (force) {
                    HttpConnection hc = new HttpConnection(Utility.getBaseUrl(context), 1);
                    hc.putBody("{\"method\": \"" + Constants.METHOD.EVENT_DETAILS + "\"}");
                    results = hc.getData();
                    parseResponse(force);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            for(int i=0; i<getSupportFragmentManager().getFragments().size();i++)
            ((DaysFragment)getSupportFragmentManager().getFragments().get(i)).notifyChanges();
        }

        private void parseResponse(boolean force){
            try{
                EventResponse er= (EventResponse)Utility.getObjectFromJson(results.data, EventResponse.class);
                globalEventsList= er.eventList;
                if(force)
                    Utility.getSharedPreferencesEditor(context).putString("completeEventsList",results.data).commit();
            }
            catch(Exception e){

            }
        }
    }

    public void refreshList(){
        new getEventsList(this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    class getShow extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void...v){

            try {
                HttpConnection hc = new HttpConnection(Utility.getBaseUrl(Events.this), 1);
                hc.putBody("{\"method\": \"" + Constants.METHOD.GET_MEGA_SHOWS + "\"}");
                Results rts = hc.getData();
                megaResponse = (MegaResponse)Utility.getObjectFromJson(rts.data,MegaResponse.class);
            }
            catch(Exception e){
                Log.v("Error", e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Void v){
            try{
                for(int i=0; i<getSupportFragmentManager().getFragments().size();i++)
                    ((ShowsFragment)getSupportFragmentManager().getFragments().get(i)).notifyChanges();
            }
            catch (Exception e)
            {
                Log.v("Error2", e.getMessage());
            }
        }
    }
}
