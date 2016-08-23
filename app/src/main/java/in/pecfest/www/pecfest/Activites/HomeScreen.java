package in.pecfest.www.pecfest.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.text.Html;
import android.graphics.Color;
import android.widget.Toast;
import in.pecfest.www.pecfest.Adapters.HomePagerAdapter;
import in.pecfest.www.pecfest.Adapters.HomeScreenGridAdapter;
import in.pecfest.www.pecfest.Communication.ImageLoader;
import in.pecfest.www.pecfest.Interfaces.CommunicationInterface;
import in.pecfest.www.pecfest.Model.Common.Constants;
import in.pecfest.www.pecfest.Model.Common.Request;
import in.pecfest.www.pecfest.Model.Common.Response;
import in.pecfest.www.pecfest.Model.Sponsor.SponsorResponse;
import in.pecfest.www.pecfest.Model.navheader;
import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.Utility;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,CommunicationInterface {
    private static int notifications=3,x=0,DELAY=5000;//DELAY is in milliseconds
    Handler handler;//for runnable
    public String as;
    GridView grid;
    Button a;
    TextView t;
    LinearLayout sponsorBanner;
    ImageView sp1,sp2,sp3,sp4,sp5;
    SponsorResponse sponsorResponse;


    EditText e;
    String[] text={"Events",
            "Shows","Lecture",
            "Register"};
    int[] imageId={
            R.drawable.test,
            R.drawable.test,
            R.drawable.test,
            R.drawable.test
    };
    ViewPager mViewPager;
    private LinearLayout dotsLayout,notificationLayout;
    private TextView notification_digit;




    Runnable marquee=new Runnable() {
        @Override
        public void run() {
            int y=Math.abs(x-4);
            x=(x+1)%8;
            mViewPager.setCurrentItem(y,true);
            handler.postDelayed(this,DELAY);
        }



    };


    void positionEverything()
    {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        float w = dm.widthPixels/dm.xdpi;
        float h = dm.heightPixels/dm.ydpi;
        float textSize = h*5;
        float width = dm.widthPixels;
        float height = dm.heightPixels;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)width,(int)height/3);
        params.leftMargin = (int) ((0));
        params.topMargin = (int) ((0));
        mViewPager.setLayoutParams(params);


//        params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.leftMargin = (int) ((width/5));
//        params.topMargin = (int) ((1.2*height/3));
//        dotsLayout.setLayoutParams(params);


        params = new RelativeLayout.LayoutParams((int)width,(int)(1.05f*height/3));
        params.leftMargin = (int) ((0));
        params.topMargin = (int) ((1.27*height/3));
        grid.setLayoutParams(params);


        params = new RelativeLayout.LayoutParams((int)width,(int)height/3);
        params.leftMargin = (int) ((0));
        params.topMargin = (int) ((1.18*2*height/3));
        sponsorBanner.setLayoutParams(params);

    }


    private void loadSponsors()
    {
        Request rr=  new Request();

        rr.method = Constants.METHOD.SPONSOR_REQUEST;
        rr.showPleaseWaitAtStart = false;
        rr.hidePleaseWaitAtEnd = false;
        rr.heading = null;
        rr.requestData = null;

        Utility.SendRequestToServer(this,rr);
    }

    @Override
    public void onRequestCompleted(String method, Response rr) {
        if(rr.isSuccess==false)
        {
            Toast.makeText(this,rr.errorMessage,Toast.LENGTH_LONG).show();
        }
        if(method.equals(Constants.METHOD.SPONSOR_REQUEST))
        {
           try {
               sponsorResponse = (SponsorResponse) Utility.getObjectFromJson(rr.JsonResponse, SponsorResponse.class);
               if (sponsorResponse != null)
                   processSponsors();
           }
           catch(Exception e){
               Toast.makeText(this, "Invalid response!", Toast.LENGTH_LONG).show();
           }
        }
    }

    private void processSponsors()
    {
        ImageLoader i1 = new ImageLoader(sponsorResponse.sponsorlist[0].sponsorUrl,sp1,1,false);
        i1.execute();

        ImageLoader i2 = new ImageLoader(sponsorResponse.sponsorlist[1].sponsorUrl,sp2,1,false);
        i2.execute();

        ImageLoader i3 = new ImageLoader(sponsorResponse.sponsorlist[2].sponsorUrl,sp3,1,false);
        i3.execute();

        ImageLoader i4 = new ImageLoader(sponsorResponse.sponsorlist[3].sponsorUrl,sp4,1,false);
        i4.execute();

        ImageLoader i5 = new ImageLoader(sponsorResponse.sponsorlist[4].sponsorUrl,sp5,1,false);
        i5.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(marquee, DELAY);
    }
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(marquee);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.home_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        setSupportActionBar(toolbar);


        //SPONSOR BANNER
        sponsorBanner = (LinearLayout)findViewById(R.id.sponsorBanner);
        //notification button--------------------------------------------------
        notification_digit=(TextView)findViewById(R.id.actionbar_notificationTV);
        notifCol();

        notificationLayout=(LinearLayout) findViewById(R.id.notification_Layout);
        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(HomeScreen.this,"you clicked notification",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(HomeScreen.this,Notification.class);
                intent.putExtra("newNotificationNumber",notifications);
                notifications=0;
                notifCol();
                startActivity(intent);

            }
        });
        //notification button---------------------------------------------------

//------------HOMEPAGE GRID------------------------------------------------

        HomeScreenGridAdapter adapter= new HomeScreenGridAdapter(HomeScreen.this,text,imageId);
        grid=(GridView)findViewById(R.id.gridViewHomePage);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(text[position].equals("Events")){
                    startActivity(new Intent(getApplicationContext(), contactus.class));
                }
                if(text[position]=="Register")
                {
                Intent i= new Intent(getApplicationContext(),register.class);
                    startActivity(i);
                }
            }
        });
//------------HOMEPAGE GRID ENDS-------------------------------------------

        //testscroll------------------
        handler=new Handler();
        //testscroll------------------
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);


        sp1 = (ImageView)findViewById(R.id.sp1);
        sp2 = (ImageView)findViewById(R.id.sp2);
        sp3 = (ImageView)findViewById(R.id.sp3);
        sp4 = (ImageView)findViewById(R.id.sp4);
        sp5 = (ImageView)findViewById(R.id.sp5);

        loadSponsors();
        addHomePager();
        positionEverything();
    }

    void notifCol(){
        if(notifications>0){

            notification_digit.setText(String.valueOf(notifications));
            notification_digit.setBackgroundResource(android.R.color.holo_red_dark);

        }else{
            notification_digit.setBackgroundResource(0);
            notification_digit.setText("");
        }

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //MenuInflater inflater=getMenuInflater();
        //inflater.inflate(R.menu.mainmenu_actionbar,menu);

        if (false)
            getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.notification_actionbar_menu:
               Toast.makeText(HomeScreen.this,"you clicked notification",Toast.LENGTH_SHORT).show();

                break;
            case R.id.dotdot_actionbar_menu:
                Toast.makeText(HomeScreen.this,"you clicked dotdot walla thing",Toast.LENGTH_SHORT).show();
                break;

        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
   if(item.getItemId()==R.id.nav_contact)
   {
    Intent i=new Intent(getApplicationContext(),contactus.class);
       startActivity(i);

   }
        return true;
    }

    private void addHomePager(){
        final int[] mResources = {
                R.drawable.download1,
                R.drawable.download2,
                R.drawable.download3
        };

        mViewPager.setAdapter(new HomePagerAdapter(this, mResources));
        addBottomDots(0, mResources.length);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                addBottomDots(position, mResources.length);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private void addBottomDots(int currentPage, int length) {
        TextView []dots = new TextView[length];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.CYAN);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.BLUE);
    }





}
