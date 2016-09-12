package pecapps.pecfest2015.Activites;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import pecapps.pecfest2015.Communication.LoadSponsorImages;
import pecapps.pecfest2015.Interfaces.CommunicationInterface;
import pecapps.pecfest2015.Model.Common.Constants;
import pecapps.pecfest2015.Model.Common.Request;
import pecapps.pecfest2015.Model.Common.Response;
import pecapps.pecfest2015.Model.Sponsor.SponsorResponse;
import pecapps.pecfest2015.www.pecfest.R;
import pecapps.pecfest2015.Utilites.Utility;

public class SplashsScreen extends AppCompatActivity implements CommunicationInterface {


    SponsorResponse sponsorResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splashs_screen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final Context thisclass = this;

        startClass();

        }

    private void startClass()
    {

        if(!Utility.isNetworkAvailable(this))
        {
            retryDialog();
        }
        else {
            loadSponsors();

        }
    }

    public  void retryDialog()
    {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Internet is not available");

        alertDialogBuilder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                startClass();
            }
        });
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onRequestCompleted(String method, final Response rr) {

        if (rr.isSuccess == false) {
            Toast.makeText(this, rr.errorMessage, Toast.LENGTH_LONG).show();
        }




        if (method.equals(Constants.METHOD.LOAD_SPONSER))
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashsScreen.this, HomeScreen.class);
                startActivity(mainIntent);
                finish();
            }
        }, 500);

        if (method.equals(Constants.METHOD.SPONSOR_REQUEST)) {
            try {
                sponsorResponse = (SponsorResponse) Utility.getObjectFromJson(rr.JsonResponse, SponsorResponse.class);

                if (sponsorResponse != null) {

                    try {
                        if (sponsorResponse != null) {
                            sponsorResponse.randomizeList();
                            LoadSponsorImages i1 = new LoadSponsorImages(sponsorResponse, this, 1, true);
                            i1.execute();
                        }
                    }
                    catch (Exception e)
                    {

                    }



                }
            } catch (Exception e) {
               retryDialog();

            }
        }
    }



    private void loadSponsors()
    {
        Request rr=  new Request();

        rr.method = Constants.METHOD.SPONSOR_REQUEST;
        rr.showPleaseWaitAtStart = false;
        rr.hidePleaseWaitAtEnd = false;
        rr.heading = null;
        rr.requestData = null;

        Utility.SendRequestToServer(this, rr);
    }


}
