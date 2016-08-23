package in.pecfest.www.pecfest.Activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import in.pecfest.www.pecfest.Model.Item;
import in.pecfest.www.pecfest.Model.MyAdapter;
import in.pecfest.www.pecfest.R;

public class contactus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_contactus);

            // 1. pass context and data to the custom adapter
            MyAdapter adapter = new MyAdapter(this, generateData());

            // 2. Get ListView from activity_main.xml
            ListView listView = (ListView) findViewById(R.id.listview1);

            // 3. setListAdapter
            listView.setAdapter(adapter);
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
