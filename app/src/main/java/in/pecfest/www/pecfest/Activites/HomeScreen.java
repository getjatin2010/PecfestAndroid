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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.text.Html;
import android.graphics.Color;
import android.widget.Toast;
import in.pecfest.www.pecfest.Adapters.HomePagerAdapter;
import in.pecfest.www.pecfest.Adapters.HomeScreenGridAdapter;
import in.pecfest.www.pecfest.R;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static int notifications=3,x=0,DELAY=5000;//DELAY is in milliseconds
    Handler handler;//for runnable
    GridView grid;
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
            int y=Math.abs((x % 8)-4);//sawwave equation taken from wikipedia
            x+=1;
            x%=7;
            mViewPager.setCurrentItem(y,true);
            handler.postDelayed(this,DELAY);
        }
    };
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
                Toast.makeText(HomeScreen.this,"you clicked "+text[position],Toast.LENGTH_SHORT).show();
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

        addHomePager();
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
        Toast.makeText(HomeScreen.this,"You clicked "+ item.getTitle(),Toast.LENGTH_SHORT).show();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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
