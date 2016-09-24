package in.pecfest.www.pecfest.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import java.util.ArrayList;

import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.Utility;

/**
 * Created by Abhi on 24-09-2016.
 */
public class ContactsAdapter extends ArrayAdapter<ContactsAdapter.Contact> {

    private final Context context;
    private final ArrayList<Contact> itemsArrayList;
    private ViewHolder holder;
    public ContactsAdapter(Context context, ArrayList<Contact> itemsArrayList) {
        super(context, R.layout.contact_row, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public int getCount(){
        return itemsArrayList.size();
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent){

        holder= new ViewHolder();

        if(view==null){
            LayoutInflater inf=((AppCompatActivity)context).getLayoutInflater();
            view=inf.inflate(R.layout.contact_row, parent,false);
            holder.tx1=(TextView) view.findViewById(R.id.label);
            holder.tx2=(TextView) view.findViewById(R.id.name1);
            holder.tx3=(TextView) view.findViewById(R.id.phone1);
            holder.tx4=(TextView) view.findViewById(R.id.name2);
            holder.tx5= (TextView) view.findViewById(R.id.phone2);
            holder.iv1= (ImageView) view.findViewById(R.id.image1);
            holder.iv2= (ImageView) view.findViewById(R.id.image2);
            view.setTag(holder);
        }
        else{
            holder=(ViewHolder)view.getTag();
        }

        Contact con= itemsArrayList.get(pos);
        holder.tx1.setText(con.lbl);
        holder.tx2.setText(con.name1);
        holder.tx3.setText(con.phone1);
        final String phone1= con.phone1;

        holder.tx2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                makeCall(phone1);
            }
        });

        holder.tx3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                makeCall(phone1);
            }
        });


        Utility.GetBitmap(con.desc1, holder.iv1, false, 100, true,true);
        if(con.name2!=null){
            holder.tx4.setVisibility(View.VISIBLE);
            holder.tx5.setVisibility(View.VISIBLE);
            holder.iv2.setVisibility(View.VISIBLE);

            holder.tx4.setText(con.name2);
            holder.tx5.setText(con.phone2);
            Utility.GetBitmap(con.desc2, holder.iv2, false, 100, true, true);

            final String phone2= con.phone2;
            holder.tx4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeCall(phone2);
                }
            });

            holder.tx5.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    makeCall(phone2);
                }
            });
        }
        else{
            holder.tx4.setVisibility(View.GONE);
            holder.tx5.setVisibility(View.GONE);
            holder.iv2.setVisibility(View.GONE);
        }
        return view;
    }

    public void makeCall(String str){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + str));
        ((AppCompatActivity)context).startActivity(intent);
    }
    static class ViewHolder{
        TextView tx1,tx2,tx3,tx4,tx5;
        ImageView iv1, iv2;
    }

    public static class Contact{
        String lbl;
        String name1;
        public String phone1;
        String desc1;
        String name2;
        public String phone2;
        String desc2;

        public Contact(String l,String n1, String p1, String d1){
            this(l, n1, p1, d1, null, null, null);
        }

        public Contact(String l,String n1, String p1, String d1, String n2, String p2, String d2){
            lbl=l;
            name1= n1;
            phone1=p1;
            desc1=d1;
            name2=n2;
            phone2=p2;
            desc2=d2;
        }
    }

    public static Contact makeObject(String l, String n1, String p1, String d1){
        return new Contact(l, n1, p1, d1);
    }

    public static Contact makeObject(String l, String n1, String p1, String d1, String n2, String p2, String d2){
        return new Contact(l, n1, p1, d1, n2, p2, d2);
    }
}

