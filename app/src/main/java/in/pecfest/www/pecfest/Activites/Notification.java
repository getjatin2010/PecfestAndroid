package in.pecfest.www.pecfest.Activites;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import in.pecfest.www.pecfest.Adapters.Notification_Adapter;
import in.pecfest.www.pecfest.R;

public class Notification extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager linearLayoutManager;
    private TextView pecfestText_notification;
    private int newNotificationNumber;

    private String[] bodyText={"RoboWars is postponed from 3:00 pm to 5:00 pm ",
            "CodeWars is preponed from 5:00 pm to 3:00 pm",
            "Venue of Human Foosball has been changed from Atheletic Field to Football ground ",
            "Winner of Treasure Hunt is Jilly Baker!\nCongratulations !"
            ,"asidaosi","asddddd","asdwqq"},
            titleText={"RoboWars","CodeWars","FoosBall","Treasure Hunt","asdasudhiau","asd","asss"};

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.notification_toolbar);
        setSupportActionBar(toolbar);

        newNotificationNumber=getIntent().getIntExtra("newNotificationNumber",0);

//recycleview-------------------------------------------------------------------
        recyclerView=(RecyclerView)findViewById(R.id.notification_recycle_layout);
        linearLayoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new Notification_Adapter(Notification.this,titleText,bodyText,newNotificationNumber);
        recyclerView.setAdapter(adapter);
//recycleView-------------------------------------------------------------------

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

}
