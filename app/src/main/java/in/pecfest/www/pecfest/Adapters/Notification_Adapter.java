package in.pecfest.www.pecfest.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import in.pecfest.www.pecfest.Activites.Notification;
import in.pecfest.www.pecfest.R;

/**
 * Created by Nischit on 8/16/2016.
 */
public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.ViewHolder> {
    Context context;
    String[] bodyText,TitleText;

    public Notification_Adapter(Context context,String[] Title,String[] bodyText){
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
        holder.title.setText(TitleText[position]);
        holder.body.setText(bodyText[position]);
    }

    @Override
    public int getItemCount() {
        return TitleText.length;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title,body;

        public ViewHolder(View view) {
            super(view);
            title=(TextView) view.findViewById(R.id.title);
            body=(TextView) view.findViewById(R.id.body);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getLayoutPosition();
                    Toast.makeText(context,"you clicked "+TitleText[position],Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
