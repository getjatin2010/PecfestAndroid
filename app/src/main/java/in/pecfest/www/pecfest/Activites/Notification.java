package in.pecfest.www.pecfest.Activites;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import in.pecfest.www.pecfest.Adapters.Notification_Adapter;
import in.pecfest.www.pecfest.Model.Common.DataHolder;
import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.ImageViewAnimatedChange;

public class Notification extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager linearLayoutManager;
    private TextView pecfestText_notification;
    private int newNotificationNumber;
    int sponsorInt;
    private ImageView sp1,sp2,sp3,sp4,sp5;
    private ImageViewAnimatedChange imageViewAnimatedChange;

    Handler handler;//for runnable
    private String[] bodyText={"RoboWars is postponed from 3:00 pm to 5:00 pm ",
            "CodeWars is preponed from 5:00 pm to 3:00 pm",
            "Venue of Human Foosball has been changed from Atheletic Field to Football ground ",
            "Winner of Treasure Hunt is Jilly Baker!\nCongratulations !"
            ,"asidaosi","asddddd","asdwqq"},
            titleText={"RoboWars","CodeWars","FoosBall","Treasure Hunt","asdasudhiau","asd","asss"};


    Runnable marquee=new Runnable() {
        @Override
        public void run() {
            if(DataHolder.getInstance().sponsorImage!=null){
                setSponsorImage();
            }
            handler.postDelayed(this,HomeScreen.DELAY);
        }
    };

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.notification_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sponsorInt=getIntent().getIntExtra("sponsorCurrentIndex",0);

        newNotificationNumber=getIntent().getIntExtra("newNotificationNumber",0);
        sp1 = (ImageView)findViewById(R.id.sp1);
        sp2 = (ImageView)findViewById(R.id.sp2);
        sp3 = (ImageView)findViewById(R.id.sp3);
        sp4 = (ImageView)findViewById(R.id.sp4);
        sp5 = (ImageView)findViewById(R.id.sp5);
        // mask actionbar title with bitmap------------------------------------
        TextView actionBarTitle=(TextView)findViewById(R.id.pefcestText_notification);
        TextView actionBarNotice=(TextView)findViewById(R.id.noticeText_notification);
        Bitmap overlay= BitmapFactory.decodeResource(getResources(),R.drawable.title_overlay);
        Shader shader=new BitmapShader(overlay,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        actionBarTitle.getPaint().setShader(shader);
        shader=new BitmapShader(overlay,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        actionBarNotice.getPaint().setShader(shader);
        //---------------------------------------------------------------------

//recycleview-------------------------------------------------------------------
        recyclerView=(RecyclerView)findViewById(R.id.notification_recycle_layout);
        linearLayoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new Notification_Adapter(Notification.this,titleText,bodyText,newNotificationNumber);
        recyclerView.setAdapter(adapter);
//recycleView-------------------------------------------------------------------

        //testscroll start runable------------

        handler=new Handler();
        marquee.run();
        //------------------------------------
//for returning to homescreen form notification---------------------------------
        pecfestText_notification=(TextView)findViewById(R.id.pefcestText_notification);
        pecfestText_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//for returning to homescreen form notification---------------------------------

    }
    private void setSponsorImage(){
        imageViewAnimatedChange.ImageViewAnimatedChange(Notification.this,this.sp1,DataHolder.getInstance().sponsorImage[(sponsorInt++)% DataHolder.getInstance().spon]);
        imageViewAnimatedChange.ImageViewAnimatedChange(Notification.this,this.sp2,DataHolder.getInstance().sponsorImage[(sponsorInt++)% DataHolder.getInstance().spon]);
        imageViewAnimatedChange.ImageViewAnimatedChange(Notification.this,this.sp3,DataHolder.getInstance().sponsorImage[(sponsorInt++)% DataHolder.getInstance().spon]);
        imageViewAnimatedChange.ImageViewAnimatedChange(Notification.this,this.sp4,DataHolder.getInstance().sponsorImage[(sponsorInt++)% DataHolder.getInstance().spon]);
        imageViewAnimatedChange.ImageViewAnimatedChange(Notification.this,this.sp5,DataHolder.getInstance().sponsorImage[(sponsorInt++)% DataHolder.getInstance().spon]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
