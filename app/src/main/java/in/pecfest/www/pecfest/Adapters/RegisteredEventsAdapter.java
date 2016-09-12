package in.pecfest.www.pecfest.Adapters;

/**
 * Created by Abhi on 12-09-2016.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.pecfest.www.pecfest.Model.RegisteredEvents.RegisteredEvent;
import in.pecfest.www.pecfest.R;

public class RegisteredEventsAdapter extends ArrayAdapter<RegisteredEvent> {

    Activity context;
    ArrayList<RegisteredEvent> registeredList;
    ViewHolder holder;
    public RegisteredEventsAdapter(Activity context, ArrayList<RegisteredEvent> rel){
        super(context, R.layout.registered_list, rel);
        this.context= context;
        this.registeredList= rel;
    }

    static class ViewHolder{
        TextView tx1,tx2,tx3,tx4;
    }

    @Override
    public int getCount(){
        return registeredList.size();
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent){
        holder= new ViewHolder();

        if(view==null){
            LayoutInflater inf=context.getLayoutInflater();
            view=inf.inflate(R.layout.event_register_list, parent,false);
            holder.tx1=(TextView) view.findViewById(R.id.registered_eventname);
            holder.tx2=(TextView) view.findViewById(R.id.registered_details);
            holder.tx3=(TextView) view.findViewById(R.id.registered_contact);
            holder.tx4=(TextView) view.findViewById(R.id.registered_lb2);
            view.setTag(holder);
        }
        else{
            holder=(ViewHolder)view.getTag();
        }

        return view;
    }


}


