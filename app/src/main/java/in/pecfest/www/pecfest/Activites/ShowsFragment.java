package in.pecfest.www.pecfest.Activites;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import in.pecfest.www.pecfest.R;
/**
 * Created by Abhi on 06-09-2016.
 */
public class ShowsFragment extends Fragment {

    ImageView iv1;
    TextView tx1, tx2;
    int showDay=1;
    public ShowsFragment(){

    }

    public ShowsFragment(int day){
        showDay= day;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_shows, container, false);
        iv1 = (ImageView) rootView.findViewById(R.id.shows_image);
        tx1 = (TextView) rootView.findViewById(R.id.shows_title);
        tx2 = (TextView) rootView.findViewById(R.id.shows_desc);

        return rootView;
    }

}
