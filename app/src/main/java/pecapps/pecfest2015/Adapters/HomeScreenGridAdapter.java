package pecapps.pecfest2015.Adapters;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import pecapps.pecfest2015.R;

/**
 * Created by Nischit on 8/9/2016.
 */
public class HomeScreenGridAdapter extends BaseAdapter {
    Context context;
    String[] text;
    int[] imageId,colour;
    Display display;
    public HomeScreenGridAdapter(Context c,String[] text, int[] imageid,int [] colour,Display display){
        this.context=c;
        this.text=text;
        this.imageId=imageid;
        this.colour=colour;
        this.display=display;
    }
    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){
            //grid=new View(context);
            grid=inflater.inflate(R.layout.gird_square,null);
            TextView textView=(TextView)grid.findViewById(R.id.grid_square_text);
            textView.setBackgroundColor(getFilterColour(position));
            //ImageView imageView=(ImageView)grid.findViewById(R.id.grid_square_image);
            textView.setText(text[position]);
            //imageView.setImageResource(imageId[position]);
            //imageView.setColorFilter(getFilterColour(position), PorterDuff.Mode.MULTIPLY);
            //imageView.setAlpha(180);
            //making grid variable
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(display.getWidth()/2, (int) (display.getHeight()/5.79));
            textView.setLayoutParams(layoutParams);

        }else{
            grid=(View)convertView;
        }
        return grid;
    }
    int getFilterColour(int position){
        View view=new View(context);
        return view.getResources().getColor(colour[position]);
    }
}
