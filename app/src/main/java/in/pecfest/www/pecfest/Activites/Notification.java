package in.pecfest.www.pecfest.Activites;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import in.pecfest.www.pecfest.Adapters.Notification_Adapter;
import in.pecfest.www.pecfest.Model.Common.DataHolder;
import in.pecfest.www.pecfest.Notifications.NotificationPayload;
import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.ImageViewAnimatedChange;
import in.pecfest.www.pecfest.Utilites.Utility;

public class Notification extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager linearLayoutManager;
    private TextView pecfestText_notification;
    private int newNotificationNumber;
    int sponsorInt;


    private ArrayList<String> bodyText;
            ArrayList<String> titleText;




    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.notification_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArrayList<String> notifications = Utility.getNotifs(this);

        newNotificationNumber = notifications.size();

        bodyText = new ArrayList<>();
        titleText = new ArrayList<>();
        int count = 0;
        for(int i = 0 ; i< newNotificationNumber ; i++)
        {
            Log.e("mssg", notifications.get(i));
            NotificationPayload notificationPayload = null;
            try {
                notificationPayload = (NotificationPayload) Utility.getObjectFromJson(notifications.get(i).toString(), NotificationPayload.class);
                bodyText.add(notificationPayload.text);
                titleText.add( notificationPayload.title);
                count++;
                }
            catch (Exception e)
            {

            }
        }
        newNotificationNumber= count;




        sponsorInt=getIntent().getIntExtra("sponsorCurrentIndex",0);

        TextView actionBarNotice=(TextView)findViewById(R.id.noticeText_notification);
        Bitmap overlay= BitmapFactory.decodeResource(getResources(),R.drawable.title_overlay);
        //Shader shader=new BitmapShader(overlay,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        //actionBarTitle.getPaint().setShader(shader);
        Shader shader=new BitmapShader(overlay,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        actionBarNotice.getPaint().setShader(shader);
        //---------------------------------------------------------------------

//recycleview-------------------------------------------------------------------
        recyclerView=(RecyclerView)findViewById(R.id.notification_recycle_layout);
        linearLayoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        Collections.reverse(titleText);
        Collections.reverse(bodyText);
        adapter=new Notification_Adapter(Notification.this,titleText,bodyText);
        recyclerView.setAdapter(adapter);
        Utility.setNewNotificationZero(this);


        //------------------------------------
//for returning to homescreen form notification---------------------------------
        /*pecfestText_notification=(TextView)findViewById(R.id.pefcestText_notification);
        pecfestText_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
//for returning to homescreen form notification---------------------------------

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
