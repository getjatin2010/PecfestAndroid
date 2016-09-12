package pecapps.pecfest2015.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import pecapps.pecfest2015.Activites.EventDetails;
import pecapps.pecfest2015.www.pecfest.R;
/**
 * Created by Abhi on 07-08-2016.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private ArrayList<EventsData> eventsList;
    private static Context context;
    public static int regBtnId;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView eventName;
        public TextView clubName;
        public TextView descText;
        public TextView date;
        public ImageButton regBtn;
        public ViewHolder(final View view) {
            super(view);

            eventName=(TextView)view.findViewById(R.id.title);
            clubName= (TextView)view.findViewById(R.id.sub_title);
            descText=(TextView)view.findViewById(R.id.description);
            date= (TextView)view.findViewById(R.id.date);
            regBtn= (ImageButton)view.findViewById(R.id.registerBtn);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

                Intent in= new Intent(context, EventDetails.class);
                in.putExtra("pecfestEventId",eventsList.get(getAdapterPosition()).eventId);
                context.startActivity(in);

        }
    }

    public EventsAdapter(ArrayList<EventsData> eventsList, Context context){
        this.eventsList= eventsList;
        this.context= context;
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        EventsData event= eventsList.get(position);
        holder.eventName.setText(event.eventName);
        holder.clubName.setText(event.clubName);
        holder.descText.setText(event.description);
        holder.date.setText(event.date);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public static class EventsData{
        String eventId;
        public String eventName;
        public String clubName;
        String description;
        String date;
        public String eventType;

        public EventsData(String i, String e, String c, String d, String dt){
            eventId= i;
            eventName= e;
            clubName= c;
            description= d;
            date= dt;
            eventType="";
        }

        public EventsData(String i, String e, String c, String d, String dt, String et){
            eventId= i;
            eventName= e;
            clubName= c;
            description= d;
            date= dt;
            eventType= et;
        }

        public String getEventId(){
            return eventId;
        }
    }
}

