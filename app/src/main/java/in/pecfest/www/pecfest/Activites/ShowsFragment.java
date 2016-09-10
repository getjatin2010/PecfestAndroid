package in.pecfest.www.pecfest.Activites;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import in.pecfest.www.pecfest.Communication.ImageLoader;
import in.pecfest.www.pecfest.Interfaces.CommunicationInterface;
import in.pecfest.www.pecfest.Model.Common.Constants;
import in.pecfest.www.pecfest.Model.Common.Request;
import in.pecfest.www.pecfest.Model.Common.Response;
import in.pecfest.www.pecfest.Model.MegaShows.MegaResponse;
import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.Utility;

/**
 * Created by Abhi on 06-09-2016.
 */
public class ShowsFragment extends Fragment implements CommunicationInterface {

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

        Request r = new Request();
        r.method = Constants.METHOD.GET_MEGA_SHOWS;
        r.showPleaseWaitAtStart=true;
        r.hidePleaseWaitAtEnd = false;
        r.heading = "GETTING SHOWS";
        Utility.SendRequestToServer(getContext(),r);
        return rootView;
    }

    @Override
    public void onRequestCompleted(String method, Response rr) {
        if(rr.isSuccess==false)
        {
            Toast.makeText(getContext(), rr.errorMessage, Toast.LENGTH_LONG).show();
        }
        if(method.equals(Constants.METHOD.GET_MEGA_SHOWS))
        {
            MegaResponse mr = null;
            try{
                 mr = (MegaResponse)Utility.getObjectFromJson(rr.JsonResponse,MegaResponse.class);
                if(showDay==1)
                {
                    ImageLoader i = new ImageLoader(mr.url1,iv1,1,false);
                    i.execute();
                    tx1.setText(mr.title1);
                    tx2.setText(mr.des1);
                }
                if(showDay==2)
                {
                    ImageLoader i = new ImageLoader(mr.url2,iv1,1,false);
                    i.execute();
                    tx1.setText(mr.title2);
                    tx2.setText(mr.des2);
                }
                if(showDay==3)
                {
                    ImageLoader i = new ImageLoader(mr.url3,iv1,1,false);
                    i.execute();
                    tx1.setText(mr.title3);
                    tx2.setText(mr.des3);
                }
            }
            catch (Exception e)
            {
            }

        }
    }
}
