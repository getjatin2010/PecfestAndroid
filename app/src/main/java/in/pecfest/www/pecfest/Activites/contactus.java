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
            items.add(new Item("Publicity ","Devansh","9466504866"));
            items.add(new Item("Marketing ","Devansh","9466504866"));
            items.add(new Item("Finance ","Devansh","9466504866"));
            items.add(new Item("Technical ","Devansh","9466504866"));
            items.add(new Item("Infra ","Devansh","9466504866"));
            items.add(new Item("PR ","Devansh","9466504866"));
            items.add(new Item("Secretary  ","Devansh","9466504866"));
            return items;
        }
}
