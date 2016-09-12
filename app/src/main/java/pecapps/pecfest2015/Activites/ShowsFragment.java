package pecapps.pecfest2015.Activites;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pecapps.pecfest2015.Model.MegaShows.MegaResponse;
import pecapps.pecfest2015.www.pecfest.R;
import pecapps.pecfest2015.Utilites.Utility;

/**
 * Created by Abhi on 06-09-2016.
 */
public class ShowsFragment extends Fragment {

    ImageView iv1;
    TextView tx1, tx2;
    int showDay=1;

    public ShowsFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_shows, container, false);
        iv1 = (ImageView) rootView.findViewById(R.id.shows_image);
        tx1 = (TextView) rootView.findViewById(R.id.shows_title);
        tx2 = (TextView) rootView.findViewById(R.id.shows_desc);

        showDay = getArguments().getInt("day");

        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();
        notifyChanges();
    }

    public void notifyChanges(){
        MegaResponse mr= Events.megaResponse;
        final ImageView iv= iv1;
        if(mr==null)
            return;
        if(showDay==1)
        {
//                    ImageLoader i = new ImageLoader("http://"+mr.url1,iv1,1,false);
//                    i.execute();
            Utility.GetBitmap("http://"+mr.url1, iv, false, 0, true);
            tx1.setText(mr.title1);
            tx2.setText(mr.des1);
        }
        if(showDay==2)
        {
//                    ImageLoader i = new ImageLoader("http://"+mr.url2,iv1,1,false);
//                    i.execute();
            Utility.GetBitmap("http://" + mr.url2, iv, false, 0, true);
            tx1.setText(mr.title2);
            tx2.setText(mr.des2);
        }
        if(showDay==3)
        {
//            ImageLoader i = new ImageLoader("http://"+mr.url3,iv1,1,false);
//            i.execute();
            Utility.GetBitmap("http://" + mr.url3, iv, false, 0, true);
            tx1.setText(mr.title3);
            tx2.setText(mr.des3);
        }
    }
}
