package in.pecfest.www.pecfest.Adapters;

import android.content.Context;
import android.content.res.Resources;
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
    int newNotificationNumber;

    public Notification_Adapter(Context context,String[] Title,String[] bodyText,int newNotificationNumber){
        this.context=context;
        this.bodyText=bodyText;
        this.TitleText=Title;
        this.newNotificationNumber=newNotificationNumber;
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
        if(position<newNotificationNumber){
            holder.newNotification.setText("new");
            holder.newNotification.setBackgroundResource(android.R.color.holo_red_dark);

        }else{
            holder.newNotification.setBackgroundColor(0);
            holder.newNotification.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return TitleText.length;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title,body,newNotification;

        public ViewHolder(View view) {
            super(view);
            title=(TextView) view.findViewById(R.id.title);
            body=(TextView) view.findViewById(R.id.body);
            newNotification=(TextView) view.findViewById(R.id.notification_new_Text);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getLayoutPosition();
                    TextView tv= (TextView) v.findViewById(R.id.notification_new_Text);

                    tv.setText("");
                    tv.setBackgroundColor(0);

                    Toast.makeText(context,"you clicked "+TitleText[position],Toast.LENGTH_SHORT).show();

                }
            });
        }

    }
}
