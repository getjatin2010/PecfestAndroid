package pecapps.pecfest2015.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pecapps.pecfest2015.R;

/**
 * Created by Nischit on 8/16/2016.
 */
public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.ViewHolder> {
    Context context;
    ArrayList<String> bodyText,TitleText;

    public Notification_Adapter(Context context,ArrayList<String> Title,ArrayList<String> bodyText){
        this.context=context;
        this.bodyText=bodyText;
        this.TitleText=Title;
    }
    @Override
    public Notification_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.notification_fragment,parent,false);
        RecyclerView.ViewHolder viewHolder=new ViewHolder(view);
        return (ViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(Notification_Adapter.ViewHolder holder, int position) {
        holder.title.setText(TitleText.get(position));
        holder.body.setText(bodyText.get(position));
    }

    @Override
    public int getItemCount() {
        return TitleText.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title,body;

        public ViewHolder(View view) {
            super(view);
            title=(TextView) view.findViewById(R.id.title);
            body=(TextView) view.findViewById(R.id.body);

        }

    }
}
