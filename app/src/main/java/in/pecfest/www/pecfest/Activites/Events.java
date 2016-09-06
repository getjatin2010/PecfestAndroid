package in.pecfest.www.pecfest.Activites;

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
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import in.pecfest.www.pecfest.Communication.HttpConnection;
import in.pecfest.www.pecfest.Model.Common.Constants;
import in.pecfest.www.pecfest.Model.EventDetails.Event;
import in.pecfest.www.pecfest.Model.EventDetails.EventResponse;
import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.Results;
import in.pecfest.www.pecfest.Utilites.Utility;

public class Events extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static ArrayList<Event> globalEventsList;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if((title=getIntent().getStringExtra("title"))!=null){
            getSupportActionBar().setTitle(title);
        }
        else
            title="";

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if(title.equals("Shows"))
            setupViewPager2(viewPager);
        else
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(title.equals("Shows") || title.equals("Lectures")){
            fab.setVisibility(View.GONE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if(!title.equals("Shows")) {
            globalEventsList = new ArrayList<Event>();
            new getEventsList(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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
        adapter.addFragment(new DaysFragment(1), "DAY 1");
        adapter.addFragment(new DaysFragment(2), "DAY 2");
        adapter.addFragment(new DaysFragment(3), "DAY 3");
        viewPager.setAdapter(adapter);
    }

    private void setupViewPager2(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ShowsFragment(1), "DAY 1");
        adapter.addFragment(new ShowsFragment(2), "DAY 2");
        adapter.addFragment(new ShowsFragment(3), "DAY 3");
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
                hc.putBody("{\"method\": \"" + Constants.METHOD.EVENT_DETAILS + "\"}");
                results = hc.getData();
                parseResponse();
            }
            else {

                if (!force) {
                    try {
                        results.data = Utility.getSharedPreferences(context).getString("completeEventsList", null);
                        parseResponse();
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
                    parseResponse();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v){
            for(int i=0; i<getSupportFragmentManager().getFragments().size();i++)
            ((DaysFragment)getSupportFragmentManager().getFragments().get(i)).notifyChanges();

        }

        private void parseResponse(){
            try{
                EventResponse er= (EventResponse)Utility.getObjectFromJson(results.data, EventResponse.class);
                globalEventsList= er.eventList;
                Utility.getSharedPreferencesEditor(context).putString("completeEventsList",results.data).commit();
            }
            catch(Exception e){

            }
        }
    }

    public void refreshList(){
        new getEventsList(this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
