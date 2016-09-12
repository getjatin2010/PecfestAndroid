package pecapps.pecfest2015.Adapters;

/**
 * Created by Abhi on 25-08-2016.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pecapps.pecfest2015.Activites.EventDetails;
import pecapps.pecfest2015.www.pecfest.R;

public class EventRegisterAdapter extends ArrayAdapter<String> {

    Activity context;
    ArrayList<String> registrantsList;
    ViewHolder holder;
    public EventRegisterAdapter(Activity context, ArrayList<String> als){
        super(context, R.layout.event_register_list, als);
        this.context= context;
        this.registrantsList= als;
    }

    static class ViewHolder{
        TextView tx1,tx2,tx3,tx4;
        ImageView tx5;

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent){
        holder= new ViewHolder();

        if(view==null){
            LayoutInflater inf=context.getLayoutInflater();
            view=inf.inflate(R.layout.event_register_list, parent,false);
            holder.tx1=(TextView) view.findViewById(R.id.register_counter);
            holder.tx2=(TextView) view.findViewById(R.id.register_id);
            holder.tx3=(TextView) view.findViewById(R.id.register_name);
            holder.tx4=(TextView) view.findViewById(R.id.register_invalid);
            holder.tx5=(ImageView) view.findViewById(R.id.register_remove);
            view.setTag(holder);
        }
        else{
            holder=(ViewHolder)view.getTag();
        }

        holder.tx1.setText("#"+(position+1));

        holder.tx2.setText(registrantsList.get(position));

        if(checkExistence(registrantsList.get(position))){
            holder.tx4.setVisibility(View.VISIBLE);
        }
        else
        holder.tx4.setVisibility(View.GONE);

        holder.tx3.setText("");
        holder.tx5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrantsList.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public int getCount(){
        return registrantsList.size();
    }

    private boolean checkExistence(String str){
        String []arr= ((EventDetails)context).invalidList;

        if(arr==null)
            return false;

        for(int i=0;i<arr.length;i++){
            if(arr[i].equals(str))
                return true;
        }
        return false;
    }
}
