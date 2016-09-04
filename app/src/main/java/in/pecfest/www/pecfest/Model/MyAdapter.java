package in.pecfest.www.pecfest.Model;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import in.pecfest.www.pecfest.Activites.HomeScreen;
import in.pecfest.www.pecfest.R;

/**
 * Created by Bansal on 8/21/2016.
 */
public class MyAdapter extends ArrayAdapter<Item>{
    private final Context context;
    private final ArrayList<Item> itemsArrayList;

    public MyAdapter(Context context, ArrayList<Item> itemsArrayList) {

        super(context, R.layout.row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.row,parent,false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.label);
        TextView valueView = (TextView) rowView.findViewById(R.id.value);
        TextView number= (TextView) rowView.findViewById(R.id.n);

        //change colour
        labelView.setTextColor(Color.DKGRAY);
        valueView.setTextColor(Color.DKGRAY);
        number.setTextColor(Color.DKGRAY);

        // 4. Set the text for textView
        labelView.setText(itemsArrayList.get(position).getTitle());
        valueView.setText(itemsArrayList.get(position).getDescription());
        number.setText(itemsArrayList.get(position).getnumber());
        // 5. retrn rowView
        return rowView;
    }

}
