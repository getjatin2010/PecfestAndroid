package in.pecfest.www.pecfest.Activites;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import in.pecfest.www.pecfest.Model.Item;
import in.pecfest.www.pecfest.Model.MyAdapter;
import in.pecfest.www.pecfest.R;

public class contactus extends AppCompatActivity {
TextView ta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_contactus);
        ta= (TextView) findViewById(R.id.n);

            // 1. pass context and data to the custom adapter
            final MyAdapter adapter = new MyAdapter(this, generateData());

            // 2. Get ListView from activity_main.xml
            ListView listView = (ListView) findViewById(R.id.listview1);

            // 3. setListAdapter
            listView.setAdapter(adapter);
        Toolbar t= (Toolbar) findViewById(R.id.notification_toolbar);
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView.setFadingEdgeLength(150);
       // final String a=ta.getText().toString();
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+adapter.getItem(position).getnumber()));
        startActivity(intent);
    }
});
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return true;
    }

    private ArrayList<Item> generateData(){
            ArrayList<Item> items = new ArrayList<Item>();
            items.add(new Item("Convener ","Prashant Sharma","9779898998"));
            items.add(new Item("Co Convener ","Navdeep Singh Lathar","9041143100"));
            items.add(new Item("Secretary ","Devanshu and Vaibhav","9815447755"));
            items.add(new Item("Event Coordination ","Dhruv and Sakshi","9501461928"));
            items.add(new Item("Infra ","Navi Joshi","9465588808"));
            items.add(new Item("Public Relations ","Divija Rawat","9560813266"));
            items.add(new Item("Hospitality  ","Kamal Chaudhary","7837229233"));
        items.add(new Item("Mega Shows  ","Vaibhav Sharma","9988634375"));
        items.add(new Item("Logistics  ","Nupur Arora","7814667022"));
        items.add(new Item("Discipline  ","Tahir Sandhu","9779190230"));
        items.add(new Item("Hospitality  ","Kamal Chaudhary","7837229233"));
        items.add(new Item("Publicity and Alumni Relations  ","Karandeep and Vrinda","8699176783"));
        items.add(new Item("Hospitality  ","Kamal Chaudhary","7837229233"));
        items.add(new Item("Marketing","Shishir Kumar","8591155276"));
        items.add(new Item("Creative  ","Sahil Garg","9041370343"));
        items.add(new Item("Finance  ","Shine and Asheem","9915324444"));
        items.add(new Item("App Development Team ","Jatin Arora, Abhinandan, Devansh and Nishchit","9041092408"));
            return items;
        }
}
