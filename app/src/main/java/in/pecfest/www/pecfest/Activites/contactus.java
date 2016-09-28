package in.pecfest.www.pecfest.Activites;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;
import java.util.ArrayList;

import in.pecfest.www.pecfest.Adapters.ContactsAdapter;
import in.pecfest.www.pecfest.Model.Item;
import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.Utility;

public class contactus extends AppCompatActivity {

    ContactsAdapter contactsAdapter;
    ListView listView;
    View footerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_contactus);

        Toolbar t= (Toolbar) findViewById(R.id.notification_toolbar);
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pecfest Team");
            contactsAdapter= new ContactsAdapter(this, generateContactData());
            // 2. Get ListView from activity_main.xml
            listView = (ListView) findViewById(R.id.listview1);

            // 3. setListAdapter
        footerView= getLayoutInflater().inflate(R.layout.contact_footer, null);
        listView.addFooterView(footerView);

            listView.setAdapter(contactsAdapter);

        listView.setFadingEdgeLength(100);
        loadFooter();
       // final String a=ta.getText().toString();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    try {
                        intent.setData(Uri.parse("tel:" + contactsAdapter.getItem(position).phone1));
                        startActivity(intent);
                    }
                    catch(Exception e){

                    }
                }
            });
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return true;
    }


    private ArrayList<ContactsAdapter.Contact> generateContactData() {
        ArrayList<ContactsAdapter.Contact> items = new ArrayList<ContactsAdapter.Contact>();
        items.add(ContactsAdapter.makeObject("Convener","Prashant Sharma","9779898998","http://pecfest.in/img/ourTeam/prashant.png"));
        items.add(ContactsAdapter.makeObject("Co Convener","Navdeep Singh Lathar","9041143100","http://pecfest.in/img/ourTeam/navdeep.png"));
        items.add(ContactsAdapter.makeObject("Secretary","Devanshu Goenka","9815447755","http://pecfest.in/img/ourTeam/devanshu.png","Vaibhav Dhingra","9815447755","http://pecfest.in/img/ourTeam/vaibhavDhingra.png"));
        items.add(ContactsAdapter.makeObject("Event Coord Cultural","Dhruv Chaudhary","9501461928","http://pecfest.in/img/ourTeam/dhruv.png", "Shrey Nagrath","7696805906","http://pecfest.in/img/ourTeam/shrey.png"));
        items.add(ContactsAdapter.makeObject("Event Coord Technical","Sakshi Vohra","9501427933","http://pecfest.in/img/ourTeam/sakshi.png", "Vanshaj","9855881147","http://pecfest.in/img/ourTeam/vanshaj.png"));
        items.add(ContactsAdapter.makeObject("Public Relations","Divija Rawat","9560813266","http://pecfest.in/img/ourTeam/divija.png"));
        items.add(ContactsAdapter.makeObject("Online Publicity","Abhishek","9646555234","http://pecfest.in/img/ourTeam/abhishek.png"));
        items.add(ContactsAdapter.makeObject("Infrastructure","Navi Joshi","9465588808","http://pecfest.in/img/ourTeam/navi.png"));
        items.add(ContactsAdapter.makeObject("Hospitality","Kamal Chaudhary","7837229233","http://pecfest.in/img/ourTeam/kamal.png"));
        items.add(ContactsAdapter.makeObject("Mega Shows","Vaibhav Sharma","9988634375","http://pecfest.in/img/ourTeam/vaibhavSharma.png"));
        items.add(ContactsAdapter.makeObject("Logistics","Nupur Arora","7814667022","http://pecfest.in/img/ourTeam/nupur.png"));
        items.add(ContactsAdapter.makeObject("Marketing","Shishir Kumar","8591155276","http://pecfest.in/img/ourTeam/shishir.png"));
        items.add(ContactsAdapter.makeObject("Publicity","Karandeep Singh","8699176783","http://pecfest.in/img/ourTeam/karandeep.png"));
        items.add(ContactsAdapter.makeObject("Finance","Shine Sharma","9915324444","http://pecfest.in/img/ourTeam/shine.png"));
        items.add(ContactsAdapter.makeObject("Alumni Relations","Vrinda","9855943075","http://pecfest.in/img/ourTeam/vrinda.png"));
        items.add(ContactsAdapter.makeObject("Discipline","Tahir Sandhu","9779190230","http://pecfest.in/img/ourTeam/tahir.png"));
        items.add(ContactsAdapter.makeObject("Creative","Sahil Garg","9041370343","http://pecfest.in/img/ourTeam/sahil.png"));
        items.add(ContactsAdapter.makeObject("Printing","Asheem Jinsi","8146957895","http://pecfest.in/img/ourTeam/asheem.png"));

        return items;
    }

    private void loadFooter(){
        Utility.GetBitmap("http://developers.pecfest.in/img/jatin.png", (ImageView)footerView.findViewById(R.id.image1), false, 100, true, true);
        Utility.GetBitmap("http://developers.pecfest.in/img/abhinandan.png", (ImageView)footerView.findViewById(R.id.image2), false, 100, true, true);
        Utility.GetBitmap("http://developers.pecfest.in/img/nischit.png", (ImageView)footerView.findViewById(R.id.image3), false, 100, true, true);
        Utility.GetBitmap("http://developers.pecfest.in/img/devansh.png", (ImageView)footerView.findViewById(R.id.image4), false, 100, true, true);
    }

    public void makeCall(View view){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        try {
            String t="";

                int ids[]={R.id.phone1, R.id.phone2, R.id.phone3, R.id.phone4};
                for (int i = 0; i < 4; i++) {
                    try {
                        t = ((TextView) view.findViewById(ids[i])).getText().toString();
                    }
                    catch(Exception e){

                    }
                }
            intent.setData(Uri.parse("tel:" + t));
            startActivity(intent);
        }
        catch(Exception e){

        }
    }
}
