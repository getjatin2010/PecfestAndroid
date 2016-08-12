package in.pecfest.www.pecfest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import in.pecfest.www.pecfest.R;

/**
 * Created by Nischit on 8/9/2016.
 */
public class HomeScreenGridAdapter extends BaseAdapter {
    Context context;
    String[] text;
    int[] imageId;
    public HomeScreenGridAdapter(Context c,String[] text, int[] imageid){
        this.context=c;
        this.text=text;
        this.imageId=imageid;
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
            grid=new View(context);
            grid=inflater.inflate(R.layout.gird_square,null);
            TextView textView=(TextView)grid.findViewById(R.id.grid_square_text);
            ImageView imageView=(ImageView)grid.findViewById(R.id.grid_square_image);
            textView.setText(text[position]);
            imageView.setImageResource(imageId[position]);
        }else{
            grid=(View)convertView;
        }
        return grid;
    }
}
