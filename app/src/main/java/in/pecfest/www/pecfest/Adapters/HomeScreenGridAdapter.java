package in.pecfest.www.pecfest.Adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.pecfest.www.pecfest.Activites.HomeScreen;
import in.pecfest.www.pecfest.R;

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
            ImageView imageView=(ImageView)grid.findViewById(R.id.grid_square_image);
            textView.setText(text[position]);
            imageView.setImageResource(imageId[position]);
            imageView.setColorFilter(getFilterColour(position), PorterDuff.Mode.MULTIPLY);

            //making grid variable
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(display.getWidth()/2, (int) (display.getHeight()/5.79));
            imageView.setLayoutParams(layoutParams);

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
