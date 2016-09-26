package in.pecfest.www.pecfest.Adapters;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.Utility;

/**
 * Created by Nischit on 8/9/2016.
 */
public class FilterApadter extends BaseAdapter {
    Context context;
    String[] imageId;
    float width,height;

    public FilterApadter(Context c, String[] imageid,float width,float height ){
        this.context=c;
        this.imageId=imageid;
        this.width = width;
        this.height = height;

    }
    @Override
    public int getCount() {
        return imageId.length;
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
            grid=inflater.inflate(R.layout.filter_square,null);
            ImageView imageView=(ImageView)grid.findViewById(R.id.filterImage);
            Utility.GetBitmap(imageId[position],imageView,false,0,true);


            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (0.18f * height), (int) (0.18f * height));
            params.leftMargin = (int) ((0));
            params.topMargin = (int) (0);
            imageView.setLayoutParams(params);

        }else{
            grid=(View)convertView;
        }
        return grid;
    }
}
