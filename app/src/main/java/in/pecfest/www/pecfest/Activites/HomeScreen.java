package in.pecfest.www.pecfest.Activites;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.Shader;
import android.os.Build;
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
import android.util.Log;
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

import org.w3c.dom.Text;

import java.util.Random;

import in.pecfest.www.pecfest.Adapters.HomePagerAdapter;
import in.pecfest.www.pecfest.Adapters.HomeScreenGridAdapter;
import in.pecfest.www.pecfest.Communication.ImageLoader;
import in.pecfest.www.pecfest.Interfaces.CommunicationInterface;
import in.pecfest.www.pecfest.Model.Common.Constants;
import in.pecfest.www.pecfest.Model.Common.DataHolder;
import in.pecfest.www.pecfest.Model.Common.Request;
import in.pecfest.www.pecfest.Model.Common.Response;
import in.pecfest.www.pecfest.Model.Sponsor.Sponsor;
import in.pecfest.www.pecfest.Model.Sponsor.SponsorResponse;
import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.ImageViewAnimatedChange;
import in.pecfest.www.pecfest.Utilites.Utility;


public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,CommunicationInterface {
    private static int notifications=3,x=4;
    public int sponsorInt=0;

    public static final int DELAY=3000;
    Handler handler;//for runnable
    private ImageViewAnimatedChange  imageViewAnimatedChange;
    Button a;
    TextView t;
    LinearLayout sponsorBanner;
    ImageView sp1,sp2,sp3,sp4,sp5;
    EditText e;
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
//for homescreen page viewer----------------------------------------------
    final int[] mResources = {
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
            R.drawable.banner4
    };
    //---------------------------------------------------------------------
    String[] text={"Events",
            "Shows","Lecture",
            "Register"};
    int[] imageId={
            R.drawable.events1,     //event
            R.drawable.shows4,      //shows
            R.drawable.lectures1,  //lecture
            R.drawable.register    //register
    };
    ViewPager mViewPager;
    private LinearLayout dotsLayout,notificationLayout;

    private TextView notification_digit,navBarHeaderText;
    private NavigationView nav_view;

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

            if(DataHolder.getInstance().sponsorImage!=null){
                setSponsorImage();
            }
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
        params.topMargin = (int) ((height*0.00125));
        mViewPager.setLayoutParams(params);

        params = new RelativeLayout.LayoutParams((int)width,(int)(1.05f*height/3));
        params.leftMargin = (int) ((0));
        params.topMargin=(int)(1.29*height/3);
        grid.setLayoutParams(params);


        params = new RelativeLayout.LayoutParams((int)width,(int)height/3);
        params.leftMargin = (int) ((0));
        params.topMargin = (int) ((1.18*2*height/3));
        sponsorBanner.setLayoutParams(params);

    }

    @Override
    public void onRequestCompleted(String method, Response rr) {

        if(rr.isSuccess==false)
        {
            Toast.makeText(this,rr.errorMessage,Toast.LENGTH_LONG).show();
        }
    }

    //set downloaded image to imageView--------------------------------------------------------------------
    private void setSponsorImage(){

        Log.e("pecfest",String.valueOf(DataHolder.getInstance().spon));
        imageViewAnimatedChange.ImageViewAnimatedChange(HomeScreen.this,sp1,DataHolder.getInstance().sponsorImage[(sponsorInt++)% DataHolder.getInstance().spon]);
        imageViewAnimatedChange.ImageViewAnimatedChange(HomeScreen.this,sp2,DataHolder.getInstance().sponsorImage[(sponsorInt++)% DataHolder.getInstance().spon]);
        imageViewAnimatedChange.ImageViewAnimatedChange(HomeScreen.this,sp3,DataHolder.getInstance().sponsorImage[(sponsorInt++)% DataHolder.getInstance().spon]);
        imageViewAnimatedChange.ImageViewAnimatedChange(HomeScreen.this,sp4,DataHolder.getInstance().sponsorImage[(sponsorInt++)% DataHolder.getInstance().spon]);
        imageViewAnimatedChange.ImageViewAnimatedChange(HomeScreen.this,sp5,DataHolder.getInstance().sponsorImage[(sponsorInt++)% DataHolder.getInstance().spon]);

    }
    //-----------------------------------------------------------------------------------------------------
    //loadImages ------------------------------------------------------------------------------------------
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

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.home_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        setSupportActionBar(toolbar);

        //animation changer initialize
        imageViewAnimatedChange=new ImageViewAnimatedChange();

        // mask actionbar title with bitmap------------------------------------
        TextView actionBarTitle=(TextView)findViewById(R.id.action_bar_title);
        Bitmap overlay= BitmapFactory.decodeResource(getResources(),R.drawable.title_overlay);
        Shader shader=new BitmapShader(overlay,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        actionBarTitle.getPaint().setShader(shader);
        //---------------------------------------------------------------------

        //notification button--------------------------------------------------
        notificationLayout=(LinearLayout) findViewById(R.id.notification_Layout);
        notification_digit=(TextView)findViewById(R.id.actionbar_notificationTV);
        notifCol();

        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(HomeScreen.this,"you clicked notification",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(HomeScreen.this,Notification.class);
                intent.putExtra("newNotificationNumber",notifications);
                intent.putExtra("sponsorCurrentIndex",sponsorInt);
                notifications=0;
                notifCol();
                //notif.setImageResource(R.drawable.notiDefault);
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
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(text[position]){
                    case "Events":
                        startActivity(new Intent(getApplicationContext(), Events.class));
                        break;
                    case "Shows":

                        break;
                    case "Lectures":

                        break;
                    case "Register":
                        Intent i= new Intent(getApplicationContext(),register.class);
                        startActivity(i);
                        break;
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

        //Getting Navigational Header Text
        View headerLayout = navigationView.getHeaderView(0);
        navBarHeaderText = (TextView)headerLayout.findViewById(R.id.navBarHeaderText);
        
        sp1 = (ImageView)findViewById(R.id.sp1);
        sp2 = (ImageView)findViewById(R.id.sp2);
        sp3 = (ImageView)findViewById(R.id.sp3);
        sp4 = (ImageView)findViewById(R.id.sp4);
        sp5 = (ImageView)findViewById(R.id.sp5);
        //------------------------------------------------------------------------------
        addHomePager();
        positionEverything();
        setSponsorImage();
    }

    void notifCol(){
        if(notifications>0){
            notificationLayout.setBackgroundResource(R.drawable.noti_new);
            notification_digit.setText(String.valueOf(notifications));
            //notification_digit.setBackgroundResource(android.R.color.holo_red_dark);

        }else{
            notificationLayout.setBackgroundResource(R.drawable.noti_default);
            //notification_digit.setBackgroundResource(0);
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

    public void loginIn(View view)
    {
        Intent i=new Intent(getApplicationContext(),login.class);
        startActivityForResult(i, 5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 5) {
            if (resultCode == RESULT_OK) {
                String pecfestId  = Utility.getsaveId(this);
                navBarHeaderText.setText("Hello " +pecfestId+" !");
                navBarHeaderText.setClickable(false);

            }
        }
    }


   /* @Override
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
*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
   if(item.getItemId()==R.id.nav_contact)
   {
        Intent i=new Intent(getApplicationContext(),contactus.class);
        startActivity(i);
   }
        if(item.getItemId()==R.id.nav_verify)
        {

            Intent i= new Intent(getApplicationContext(),navverify.class);
            startActivity(i);
        }


        if(item.getItemId()==R.id.nav_logout)
        {
            Utility.saveId(null,getApplicationContext());
            Toast.makeText(this,"Logged Out",Toast.LENGTH_SHORT).show();
            navBarHeaderText.setText("LOGIN ");
            navBarHeaderText.setClickable(true);






        }
        return true;
    }

    private void addHomePager(){

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
