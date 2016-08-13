package in.pecfest.www.pecfest.Adapters;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import in.pecfest.www.pecfest.R;
import java.util.ArrayList;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Context;
import android.util.Log;
import android.view.animation.AlphaAnimation;
/**
 * Created by Abhi on 07-08-2016.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private ArrayList<EventsData> eventsList;
    private static Context context;
    public static int regBtnId;
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            regBtn.setOnClickListener(this);
            regBtnId=regBtn.getId();
        }

        @Override
        public void onClick(View v) {
            Log.v("Id",""+v.getId());
            if (v.getId() == regBtn.getId()){
                Toast.makeText(context, "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "ROW PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
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

        public EventsData(String i, String e, String c, String d, String dt){
            eventId= i;
            eventName= e;
            clubName= c;
            description= d;
            date= dt;
        }

        public String getEventId(){
            return eventId;
        }
    }
}

