package in.pecfest.www.pecfest.Adapters;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import in.pecfest.www.pecfest.R;
import java.util.ArrayList;
/**
 * Created by Abhi on 07-08-2016.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private ArrayList<EventsData> eventsList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titleText;
        public TextView descText;
        public ViewHolder(View view) {
            super(view);
            titleText=(TextView)view.findViewById(R.id.title);
            descText=(TextView)view.findViewById(R.id.description);
        }
    }

    public EventsAdapter(ArrayList<EventsData> eventsList){
        this.eventsList= eventsList;
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
        holder.titleText.setText(event.title);
        holder.descText.setText(event.description);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public static class EventsData{
        String title;
        String description;

        public EventsData(String t, String d){
            title= t;
            description=d;
        }
    }
}

