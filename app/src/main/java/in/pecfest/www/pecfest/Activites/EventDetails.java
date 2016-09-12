package in.pecfest.www.pecfest.Activites;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.pecfest.www.pecfest.Adapters.EventRegisterAdapter;
import in.pecfest.www.pecfest.Communication.HttpConnection;
import in.pecfest.www.pecfest.Communication.ImageLoader;
import in.pecfest.www.pecfest.Model.Communication.RequestC;
import in.pecfest.www.pecfest.Model.EventDetails.Event;
import in.pecfest.www.pecfest.Model.EventRegister.EventRegisterRequest;
import in.pecfest.www.pecfest.Model.EventRegister.EventRegisterResponse;
import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.Results;
import in.pecfest.www.pecfest.Utilites.Utility;

public class EventDetails extends AppCompatActivity {

    TextView tx1, tx2, tx3, tx4, tx5,tx6;
//    EditText et1;
    AutoCompleteTextView et1;
    ImageView iv1;
    ListView lv1;
    Button bt1,bt2;
    EventRegisterAdapter registerAdapter;
    ArrayList<String> registrantsList;
    ViewGroup header;
    String pecfestEventId="";
    ArrayList<String> cacheList;
    Event event;
    public String[] invalidList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        pecfestEventId= getIntent().getStringExtra("pecfestEventId");
        Log.v("eventId",pecfestEventId);

        for(int i=0; i<Events.globalEventsList.size();i++){
            if(Events.globalEventsList.get(i).eventId==Integer.parseInt(pecfestEventId)){
                event= Events.globalEventsList.get(i);
                break;
            }
        }

        lv1= (ListView) findViewById(R.id.register_list_view);
        header= (ViewGroup) getLayoutInflater().inflate(R.layout.event_register_header, lv1, false);
        lv1.addHeaderView(header);

        ImageView imageView = (ImageView)header.findViewById(R.id.event_image);
        ImageLoader il = new ImageLoader(event.imageUrl,imageView,1,false);
        il.execute();
        
        tx1= (TextView) header.findViewById(R.id.event_name);
        tx2= (TextView) header.findViewById(R.id.club_name);
        tx3= (TextView) header.findViewById(R.id.event_details);
        tx4= (TextView) header.findViewById(R.id.register_message);
        tx5= (TextView) header.findViewById(R.id.register_label);
        tx6= (TextView) header.findViewById(R.id.event_details_ext);
        iv1= (ImageView) header.findViewById(R.id.event_image);
        et1= (AutoCompleteTextView) findViewById(R.id.event_register_id);
        bt1= (Button) findViewById(R.id.btn_event_register);
        bt2= (Button) findViewById(R.id.btn_event_submit);

        registrantsList= new ArrayList<String>();
        registerAdapter= new EventRegisterAdapter(this, registrantsList);

        if(Utility.getsaveId((this))!=null && Utility.getsaveId((this)).pecfestId!=null){
            registerAdapter.add(Utility.getsaveId(this).pecfestId);
        }

        lv1.setAdapter(registerAdapter);

        tx1.setText(event.eventName);
        tx2.setText(event.clubName);
        if(event.instructions!=null && event.instructions!="")
        tx3.setText(event.eventDetails+"\n\n"+"Instructions:\n\n"+event.instructions);
        else
        tx3.setText(event.eventDetails);
        if(event.eventDetails==null || event.eventDetails.isEmpty())
            tx3.setVisibility(View.GONE);

        String t="Category: "+event.eventTypeName+"\n"+"Location: "+event.location+"\n"+"Time: "+"Day "+event.day+" "+event.time+"\n"+"Contact: "+event.clubHead.replace(";",", ");
        if(event.maxSize>0)
            t=t+"\n\n"+"Team Size: "+event.minSize+"-"+event.maxSize;
        tx6.setText(t);

        if(event.maxSize==0){
            et1.setVisibility(View.GONE);
            bt1.setVisibility(View.GONE);
            bt2.setVisibility(View.GONE);
            tx5.setVisibility(View.GONE);
        }

        et1.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1, getCacheList()));
        et1.setThreshold(1);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRegistrant();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                tx4.setVisibility(View.GONE);
                submitRegistrants();
            }

        });

    }

    public void addRegistrant(){
        String r= et1.getText().toString();
        if(r.isEmpty())
            return;
        if(registrantsList.size()>=event.maxSize){
            Toast.makeText(this, "Maximum limit reached!", Toast.LENGTH_SHORT).show();
            return;
        }
        et1.setText("");
        registrantsList.add(r);
        registerAdapter.notifyDataSetChanged();

//        addToCacheList(r);
//        for(int i=0;i<cacheList.size();i++){
//            Log.v("list",cacheList.get(i));
//        }
    }

    public void submitRegistrants(){
        if(registrantsList.size()<event.minSize){
            Toast.makeText(this, "Not enough members to participate in the event!", Toast.LENGTH_SHORT).show();
            return;
        }
        new doRegistration().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    class doRegistration extends AsyncTask<Void, Void, Void>{

        Results rts;
        @Override
        public Void doInBackground(Void...v){
            rts= new Results();
            HttpConnection hc= new HttpConnection(Utility.getBaseUrl(EventDetails.this),1);
            String t= Utility.GetJsonObject(new EventRegisterRequest(pecfestEventId, registrantsList));
            RequestC rc = new RequestC();
            rc.method = "registerEvent";
            rc.request = t;
            hc.putBody(Utility.GetJsonObject(rc));
            rts= hc.getData();
            return null;
        }

        @Override
        public void onPostExecute(Void v){
            try{
                EventRegisterResponse err= (EventRegisterResponse)Utility.getObjectFromJson(rts.data, EventRegisterResponse.class);
                if(err.registered){
                    tx4.setVisibility(View.VISIBLE);
                    tx4.setText("Registered succesfully!");

                    for(int i=0;i<registrantsList.size();i++)
                        addToCacheList(registrantsList.get(i));

                    return;
                }
                if(err.invalidMembers!=null) {
                    invalidList = err.invalidMembers;
                    registerAdapter.notifyDataSetChanged();
                }
                if(err.response!=null){
                    tx4.setVisibility(View.VISIBLE);
                    tx4.setText(err.response);
                }

            }
            catch(Exception e){

            }
        }
    }

    private ArrayList<String> getCacheList(){
        if(cacheList==null) {
            cacheList = new ArrayList<String>();
            String t = Utility.getSharedPreferences(this).getString("cachedIds", null);
            if (t != null) {
                String[] a = t.split(",");
                for (int i = 0; i < a.length; i++) {
                    if (a[i].isEmpty())
                        continue;
                    cacheList.add(a[i]);
                }
            }
        }
        return cacheList;
    }

    private void addToCacheList(String str){

        if(cacheList==null)
            getCacheList();

        for(int i=0;i<cacheList.size();i++){
            if(cacheList.get(i).equals(str))
                return;
        }
        cacheList.add(str);
        Utility.getSharedPreferencesEditor(this).putString("cachedIds",Utility.getSharedPreferences(this).getString("cachedIds","")+","+str).commit();
    }

}
