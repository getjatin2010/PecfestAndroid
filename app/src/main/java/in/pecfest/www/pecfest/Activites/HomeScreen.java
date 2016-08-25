package in.pecfest.www.pecfest.Activites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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
import android.widget.Toast;

import java.util.Random;

import in.pecfest.www.pecfest.Adapters.HomePagerAdapter;
import in.pecfest.www.pecfest.Adapters.HomeScreenGridAdapter;
import in.pecfest.www.pecfest.Communication.ImageLoader;
import in.pecfest.www.pecfest.Interfaces.CommunicationInterface;
import in.pecfest.www.pecfest.Model.Common.Constants;
import in.pecfest.www.pecfest.Model.Common.Request;
import in.pecfest.www.pecfest.Model.Common.Response;
import in.pecfest.www.pecfest.Model.Sponsor.Sponsor;
import in.pecfest.www.pecfest.Model.Sponsor.SponsorResponse;
import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.Utility;

/*Changes Done
    made sponsor image array so that image doesnot have to be downloaded everytime
    sponsor list is random
    made randomizer function in sponsorResponse
    made changes in ImageLoader - new constructor, minor change in OnPostExecute
    made changes to homescreen grid view- random colour filter from colour set
    made changes in HomeScreenGridAdapter- each grid layout is not in sized according to the screen height and width
    the function ProcessSponsor is not needed now
  */

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,CommunicationInterface {
    private static int notifications=3,sponsorInt=0,x=4,DELAY=3000;//DELAY is in milliseconds
    public static Bitmap sponsorImage[];
    public static int spon=0;
    Handler handler;//for runnable

    public String as;
    GridView grid;
    //GridView tint colour list---------------------------------------
    int[] colour={android.R.color.holo_red_dark,
            android.R.color.holo_green_dark,
            android.R.color.holo_purple,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark,
            android.R.color.secondary_text_dark_nodisable
    };
    //----------------------------------------------------------------
    Button a;
    TextView t;
    LinearLayout sponsorBanner;
    ImageView sp1,sp2,sp3,sp4,sp5;
    static SponsorResponse sponsorResponse;  //made static so that the value stays even when activity is changed


    EditText e;
    String[] text={"Events",
            "Shows","Lecture",
            "Register"};
    int[] imageId={
            R.drawable.events,     //event
            R.drawable.shows,      //shows
            R.drawable.classroom,  //lecture
            R.drawable.lectures    //register
    };
    ViewPager mViewPager;
    private LinearLayout dotsLayout,notificationLayout;
    private TextView notification_digit;

//Randomaize Colour and Sponsor array for gridView--------------------------
    void randomizeArray(int[] array){

        Random r=new Random();
        for(int i=0;i<(array.length/2);i++){
            int x= r.nextInt(array.length);
            int y= r.nextInt(array.length);
            if(y==x){
                y=(y+1)%array.length;
            }

            int temp = array[x];
            array[x]=array[y];
            array[y]=temp;
        }
    }
//--------------------------------------------------------------------------

    Runnable marquee=new Runnable() {
        @Override
        public void run() {
            marqueeBanner();

            if(sponsorImage!=null){
                setSponsorImage();
            }
            //if(sponsorResponse!=null&&sponsorResponse.sponsorlist!=null){

                //processSponsors();
            //}
            handler.postDelayed(this,DELAY);
        }



    };
    void marqueeBanner(){
        int y=Math.abs(x-4);
        x=(x+1)%8;
        mViewPager.setCurrentItem(y,true);
    }

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

        params = new RelativeLayout.LayoutParams((int)width,(int)(1.05f*height/3));
        params.leftMargin = (int) ((0));
        params.topMargin=(int)(1.285*height/3);
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
               if (sponsorResponse != null){

                   sponsorResponse.randomizeList();  //randomize sponsor list
                   //processSponsors();
                   loadDownloadedImage();
                   setSponsorImage();
               }
           }
           catch(Exception e){
               Toast.makeText(this, "Invalid response!", Toast.LENGTH_LONG).show();
           }
        }
    }

    //set downloaded image to imageView--------------------------------------------------------------------
    private void setSponsorImage(){
        sp1.setImageBitmap(sponsorImage[(sponsorInt++)% spon]);
        sp2.setImageBitmap(sponsorImage[(sponsorInt++)% spon]);
        sp3.setImageBitmap(sponsorImage[(sponsorInt++)% spon]);
        sp4.setImageBitmap(sponsorImage[(sponsorInt++)% spon]);
        sp5.setImageBitmap(sponsorImage[(sponsorInt++)% spon]);
        //sponsorResponse.sponsorlist.length
    }
    //-----------------------------------------------------------------------------------------------------
    //loadImages ------------------------------------------------------------------------------------------
    void loadDownloadedImage(){
        ImageView img=new ImageView(this);
        for(int i=0;i<sponsorResponse.sponsorlist.length;i++){
            ImageLoader i1 = new ImageLoader(sponsorResponse.sponsorlist[i].sponsorUrl,1,false,true);
            i1.execute();
        }

    }
    //-----------------------------------------------------------------------------------------------------
    /*private void processSponsors()
    {
        ImageLoader i1 = new ImageLoader(sponsorResponse.sponsorlist[(sponsorInt++)% sponsorResponse.sponsorlist.length].sponsorUrl,sp1,1,false);
        i1.execute();

        ImageLoader i2 = new ImageLoader(sponsorResponse.sponsorlist[(sponsorInt++)% sponsorResponse.sponsorlist.length].sponsorUrl,sp2,1,false);
        i2.execute();

        ImageLoader i3 = new ImageLoader(sponsorResponse.sponsorlist[(sponsorInt++)% sponsorResponse.sponsorlist.length].sponsorUrl,sp3,1,false);
        i3.execute();

        ImageLoader i4 = new ImageLoader(sponsorResponse.sponsorlist[(sponsorInt++)% sponsorResponse.sponsorlist.length].sponsorUrl,sp4,1,false);
        i4.execute();

        ImageLoader i5 = new ImageLoader(sponsorResponse.sponsorlist[(sponsorInt++)% sponsorResponse.sponsorlist.length].sponsorUrl,sp5,1,false);
        i5.execute();
    }
*/
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

        //SponsorImage array test decleration-----------------------------------
        sponsorImage=new Bitmap[12];
        //----------------------------------------------------------------------



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
        randomizeArray(colour);//Randomize colours array before passing

        HomeScreenGridAdapter adapter= new HomeScreenGridAdapter(HomeScreen.this,text,imageId,colour,getWindowManager().getDefaultDisplay());
        grid=(GridView)findViewById(R.id.gridViewHomePage);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(text[position].equals("Events")){
                    startActivity(new Intent(getApplicationContext(), Events.class));
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



        //Sponsor-----------------------------------------------------------------------
        //SPONSOR BANNER
        sponsorBanner = (LinearLayout)findViewById(R.id.sponsorBanner);
        //sponsorResponse.randomizeList();

        sp1 = (ImageView)findViewById(R.id.sp1);
        sp2 = (ImageView)findViewById(R.id.sp2);
        sp3 = (ImageView)findViewById(R.id.sp3);
        sp4 = (ImageView)findViewById(R.id.sp4);
        sp5 = (ImageView)findViewById(R.id.sp5);
        loadSponsors();
        //------------------------------------------------------------------------------
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
                R.drawable.banner,
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
