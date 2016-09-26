package in.pecfest.www.pecfest.Activites;

import android.Manifest.permission;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Random;

import in.pecfest.www.pecfest.Adapters.HomePagerAdapter;
import in.pecfest.www.pecfest.Adapters.HomeScreenGridAdapter;
import in.pecfest.www.pecfest.Interfaces.CommunicationInterface;
import in.pecfest.www.pecfest.Model.Common.Constants;
import in.pecfest.www.pecfest.Model.Common.Request;
import in.pecfest.www.pecfest.Model.Common.Response;
import in.pecfest.www.pecfest.Model.Permissions.PermissionRequest;
import in.pecfest.www.pecfest.Model.Permissions.PermissionResponse;
import in.pecfest.www.pecfest.Model.Posters.PosterResponse;
import in.pecfest.www.pecfest.Model.Sponsor.SponsorResponse;
import in.pecfest.www.pecfest.Model.login.LoginResponse;
import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.ImageViewAnimatedChange;
import in.pecfest.www.pecfest.Utilites.Utility;


public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,CommunicationInterface {
    private  int notifications=0,posterCount =1;
    public int sponsorInt=1;


    public static final int DELAY2=5000;
    public static final int DELAY=3000;
    Handler handler,handlerPoster;//for runnable
    private ImageViewAnimatedChange  imageViewAnimatedChange;
    Button a;
    TextView t;
    MenuItem i1,i2;
    NavigationView navigationview;
    SponsorResponse sponsorResponse;
    LinearLayout sponsorBanner;
    ImageView sp1,sp2,sp3,sp4,sp5;
    public String as;
    GridView grid;

    int[] colour={
            R.color.yellow,
            R.color.green2,
            R.color.colorAccent,
            R.color.green3,
            R.color.red,
            R.color.orange1,
            R.color.orange0,
    };
    //----------------------------------------------------------------
//for homescreen page viewer----------------------------------------------

    //---------------------------------------------------------------------
    String[] text={"Events",
            "Shows","Lecture & Workshops",
            "Register"};
    int[] imageId={
            //R.drawable.events1,     //event
           // R.drawable.shows4,      //shows
           // R.drawable.lectures1,  //lecture
            //R.drawable.register    //register
    };
    ViewPager mViewPager;
    private LinearLayout dotsLayout,notificationLayout;

    private TextView notification_digit,navBarHeaderText;

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


            setSponsorImage();
            handler.postDelayed(this,DELAY2);
        }
    };

    Runnable marquee2=new Runnable() {
        @Override
        public void run() {
            marqueeBanner();
            handlerPoster.postDelayed(this,DELAY);
        }
    };
    int x = 0;
    Boolean goingFront = true;
    void marqueeBanner(){
        int y=Math.abs(x);

        if(x==0)
            goingFront = true;
        if(x==posterCount-1)
            goingFront = false;

         if(goingFront)
            x=(x+1)%posterCount;
         else
             x = (x-1)%posterCount;

        mViewPager.setCurrentItem(y,true);
    }

    void askPermission()
    {
        // Here, thisActivity is the current activity
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{permission.WRITE_EXTERNAL_STORAGE},
                        100);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
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

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)width,(int)(height*.97f/3));
        params.leftMargin = (int) ((0));
        params.topMargin = (int) ((height*0.00125));
        mViewPager.setLayoutParams(params);


        params = new RelativeLayout.LayoutParams((int) width, (int) (1.05f * height / 3));
        params.leftMargin = (int) ((0));
        params.topMargin = (int) (1.25 * height / 3);
        grid.setLayoutParams(params);



        params = new RelativeLayout.LayoutParams((int)width,(int)height/3);
        params.leftMargin = (int) ((0));
        params.topMargin = (int) ((1.15*2*height/3));
        sponsorBanner.setLayoutParams(params);

    }
    PermissionResponse pr;
    @Override
    public void onRequestCompleted(String method, Response rr) {

        if(rr.isSuccess==false)
        {
            Toast.makeText(this,rr.errorMessage,Toast.LENGTH_LONG).show();
        }
        if(method.equalsIgnoreCase(Constants.METHOD.APP_PERMISSIONS))
        {
            pr =(PermissionResponse)Utility.getObjectFromJson(rr.JsonResponse,PermissionResponse.class);              ;
            WhatToDo(pr);
        }
        if(method.equals(Constants.METHOD.LOAD_SPONSER))
        {

        }
        if((method.equals(Constants.METHOD.GET_POSTERS)))
        {
            final PosterResponse pr = (PosterResponse)Utility.getObjectFromJson(rr.JsonResponse,PosterResponse.class);
            mViewPager.setAdapter(new HomePagerAdapter(this, pr.posterUrl));
            addBottomDots(0, pr.posterUrl.length);
            posterCount =  pr.posterUrl.length;
            if(posterCount==0)
                posterCount = 1;
            mViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    handlerPoster.removeCallbacks(marquee2);
                    handlerPoster.postDelayed(marquee2,8000);
                    return false;
                }
            });
            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
                @Override
                public void onPageSelected(int position) {
                    addBottomDots(position, pr.posterUrl.length);
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int arg0) {

                }
            });
        }
    }


    private void WhatToDo(final PermissionResponse pr)
    {
        if(pr==null)
            return;

        if(pr.code.equalsIgnoreCase(Constants.PERMISSIONS.WARNING))
        {
            android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(pr.text);

            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                        getUpdate();
                }
            });
            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else if(pr.code.equalsIgnoreCase(Constants.PERMISSIONS.STOP))
        {
            android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(pr.text);

            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                        getUpdate();
                    finish();

                }
            });
            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }


    //set downloaded image to imageView--------------------------------------------------------------------
    private void setSponsorImage(){
        int[] arr = new int[5];

        for(int i = 0 ; i<5 ;i++)
        {
            boolean found = false;
            int numb = (int) (Math.random() * sponsorResponse.count);
                for(int j = 0 ; j<i ;j++)
                {
                    if(numb==arr[j])
                        found=true;
                }

            if(found==true)
                i--;
            else
                arr[i] = numb;
        }

        if(sponsorResponse==null)
            sponsorResponse = (SponsorResponse)Utility.getObjectFromJson(getIntent().getExtras().getString("sponsor"),SponsorResponse.class);
        if(sponsorResponse!=null) {
            //String url, ImageView iv, boolean resize, int width, boolean fetchFromLocal

            imageViewAnimatedChange.ImageViewAnimatedChange(HomeScreen.this, sp1,
                    sponsorResponse.sponsorlist[arr[0]].sponsorUrl);
            imageViewAnimatedChange.ImageViewAnimatedChange(HomeScreen.this, sp2,
                    sponsorResponse.sponsorlist[arr[1]].sponsorUrl);
            imageViewAnimatedChange.ImageViewAnimatedChange(HomeScreen.this, sp3,
                    sponsorResponse.sponsorlist[arr[2]].sponsorUrl);
            imageViewAnimatedChange.ImageViewAnimatedChange(HomeScreen.this, sp4,
                    sponsorResponse.sponsorlist[arr[3]].sponsorUrl);
            imageViewAnimatedChange.ImageViewAnimatedChange(HomeScreen.this, sp5,
                    sponsorResponse.sponsorlist[arr[4]].sponsorUrl);


             }
    }
    //-----------------------------------------------------------------------------------------------------
    //loadImages ------------------------------------------------------------------------------------------
    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(marquee, DELAY2);
        handlerPoster.postDelayed(marquee2, DELAY);
    }


    public void getPermissions()
    {
        PermissionRequest pr = new PermissionRequest();
        pr.version = Constants.appVersion;

        Request r = new Request();
        r.hidePleaseWaitAtEnd =false;
        r.showPleaseWaitAtStart = false;
        r.method = Constants.METHOD.APP_PERMISSIONS;
        r.requestData = Utility.GetJsonObject(pr);

        Utility.SendRequestToServer(this, r);

    }
    public static Bitmap fetchFromLocal(String i){
        try{
            Log.v("from local", "photo from local" + i);
            File f=new File(Constants.STORAGE_PATH+"images/"+i);
            return BitmapFactory.decodeStream((InputStream)new FileInputStream(f));
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(marquee);
        handlerPoster.removeCallbacks(marquee2);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //allow animation for changing activity
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_home_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.home_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        setSupportActionBar(toolbar);

        //look for targets
//        try {
//            if (getIntent().getStringExtra("target") != null && !getIntent().getStringExtra("target").contains("HomeScreen"))
//                startActivity(new Intent(this, Class.forName(getIntent().getStringExtra("target"))));
//        }
//        catch(Exception e){
//            Log.v("not found on home", e.getMessage());
//        }

        //Subscribe for notifications
        FirebaseMessaging.getInstance().subscribeToTopic("pecfest");
        FirebaseMessaging.getInstance().subscribeToTopic("pecfestTest");
        //animation changer initialize
        imageViewAnimatedChange=new ImageViewAnimatedChange();

        // mask actionbar title with bitmap------------------------------------
        TextView actionBarTitle=(TextView)findViewById(R.id.action_bar_title);
        Bitmap overlay= BitmapFactory.decodeResource(getResources(),R.drawable.title_overlay);
        Shader shader=new BitmapShader(overlay,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        actionBarTitle.getPaint().setShader(shader);
        //---------------------------------------------------------------------


         // Remove the comment from below line to test DrapYourCapeClass
        //NISCHIT,Please Beautify the class
       // startActivity(new Intent(this,DrapYourCape.class));






        //notification button--------------------------------------------------
        notificationLayout=(LinearLayout) findViewById(R.id.notification_Layout);
        notification_digit=(TextView)findViewById(R.id.actionbar_notificationTV);

        notifications =Utility.getNewNotifs(this);
        Log.e("o",String.valueOf(notifications));

        notifCol();

        notificationLayout.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(HomeScreen.this,Notification.class);
                intent.putExtra("newNotificationNumber",notifications);
                intent.putExtra("sponsorCurrentIndex", sponsorInt);
                notifications = 0;
                startActivity(intent);
                notifCol();

                //notif.setImageResource(R.drawable.notiDefault);

            }
        });
        //notification button---------------------------------------------------

//------------HOMEPAGE GRID------------------------------------------------
        randomizeArray(colour);//Randomize colours array before passing

hideItem();
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
                        startActivity(new Intent(getApplicationContext(), Events.class).putExtra("title","Shows"));
                        break;
                    case "Lecture & Workshops":
                        if(pr==null || pr.lecture !=0)
                        startActivity(new Intent(getApplicationContext(), Events.class).putExtra("title","Lectures"));
                        else
                        Toast.makeText(HomeScreen.this,"Will start this section soon",Toast.LENGTH_SHORT).show();
                        break;
                    case "Register":
                        Intent i= new Intent(getApplicationContext(),register.class);
                        startActivityForResult(i,7);
                        break;
                }
            }
        });

//------------HOMEPAGE GRID ENDS-------------------------------------------

        //testscroll------------------
        handler=new Handler();
        handlerPoster = new Handler();
        //testscroll------------------
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);


        sponsorResponse = (SponsorResponse)Utility.getObjectFromJson(getIntent().getExtras().getString("sponsor"),SponsorResponse.class);
        //Sponsor-----------------------------------------------------------------------
        //SPONSOR BANNER
        sponsorBanner = (LinearLayout)findViewById(R.id.sponsorBanner);
        //sponsorResponse.randomizeList();

        //Getting Navigational Header Text
        View headerLayout = navigationView.getHeaderView(0);
        navBarHeaderText = (TextView)headerLayout.findViewById(R.id.navBarHeaderText);



        LoginResponse lr = Utility.getsaveId(this);
        if(lr.name!=null)
        {
            navBarHeaderText.setText("Hello " + lr.name + "!\nId: "+lr.pecfestId);
            navBarHeaderText.setClickable(false);
        }

        sp1 = (ImageView)findViewById(R.id.sp1);
        sp2 = (ImageView)findViewById(R.id.sp2);
        sp3 = (ImageView)findViewById(R.id.sp3);
        sp4 = (ImageView)findViewById(R.id.sp4);
        sp5 = (ImageView)findViewById(R.id.sp5);
        //------------------------------------------------------------------------------
        getPermissions();
        addHomePager();
        askPermission();
        positionEverything();
        getPosters();
        setSponsorImage();


    }
    private void hideItem()
    {

        LoginResponse lr = Utility.getsaveId(this);
        if(lr.name==null) {
            navigationview = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationview.getMenu();
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            Menu nav_Menu1 = navigationview.getMenu();
            nav_Menu1.findItem(R.id.nav_Regevent).setVisible(false);
        }


    }
    private void getPosters()
    {
        Request r = new Request();
        r.hidePleaseWaitAtEnd = false;
        r.showPleaseWaitAtStart = false;
        r.method = Constants.METHOD.GET_POSTERS;

        Utility.SendRequestToServer(this,r);
    }
    void notifCol(){
        if(notifications>0){
            notificationLayout.setBackgroundResource(R.drawable.noti_new);
            notification_digit.setText(String.valueOf(notifications));
            //notification_digit.setBackgroundResource(android.R.color.holo_red_dark);

            //notification_digit.setTextColor(getResources().getColor(R.color.black));

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
        if (requestCode == 5 || requestCode == 6 || requestCode == 7) {
            if (resultCode == RESULT_OK) {
                LoginResponse lr= Utility.getsaveId(this);
                navBarHeaderText.setText("Hello " + lr.name + " !\nId: "+lr.pecfestId);
                navBarHeaderText.setClickable(false);
                navigationview = (NavigationView) findViewById(R.id.nav_view);
                Menu nav_Menu = navigationview.getMenu();
                nav_Menu.findItem(R.id.nav_logout).setVisible(true);
                Menu nav_Menu1 = navigationview.getMenu();
                nav_Menu1.findItem(R.id.nav_Regevent).setVisible(true);


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
            startActivityForResult(i, 6);
        }

        if(item.getItemId()==R.id.nav_map)
        {
            location();
        }

        if(item.getItemId()==R.id.nav_Regevent){
            if ((Utility.getsaveId((this)) != null && Utility.getsaveId((this)).pecfestId != null)) {
                Intent i = new Intent(getApplicationContext(), RegisteredEvents.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(this,"Please Login",Toast.LENGTH_SHORT).show();
            }
        }


        if(item.getItemId()==R.id.nav_logout) {
            if ((Utility.getsaveId((this)) != null && Utility.getsaveId((this)).pecfestId != null)) {
                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Are you sure you want to logut?");

                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        LoginResponse lr = new LoginResponse();
                        lr.name = null;
                        lr.phone = null;
                        lr.pecfestId = null;
                        Utility.saveId(lr, getApplicationContext());
                        Toast.makeText(HomeScreen.this, "Logged Out", Toast.LENGTH_SHORT).show();
                        navBarHeaderText.setText("LOGIN ");
                        navBarHeaderText.setClickable(true);
                        navigationview = (NavigationView) findViewById(R.id.nav_view);
                        Menu nav_Menu = navigationview.getMenu();
                        nav_Menu.findItem(R.id.nav_logout).setVisible(false);
                        Menu nav_Menu1 = navigationview.getMenu();
                        nav_Menu1.findItem(R.id.nav_Regevent).setVisible(false);
                    }
                });
                android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();



            }
            else
            {
                Toast.makeText(this,"Not Logged in!",Toast.LENGTH_SHORT).show();

            }
        }
        return true;
    }

    private void addHomePager(){


    }

    public void location()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:30.766202,76.786354?q=PEC University of Technology,Chandigarh"));
        startActivity(intent);
    }

    private void addBottomDots(int currentPage, int length) {
        TextView []dots = new TextView[length];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.WHITE);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.BLACK);
    }


   void getUpdate() {
       final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
       try {
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
       } catch (android.content.ActivityNotFoundException anfe) {
           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
       }
   }


    }


