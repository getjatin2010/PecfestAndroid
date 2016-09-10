package in.pecfest.www.pecfest.Activites;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import in.pecfest.www.pecfest.Communication.ImageLoader;
import in.pecfest.www.pecfest.Communication.LoadSponsorImages;
import in.pecfest.www.pecfest.Interfaces.CommunicationInterface;
import in.pecfest.www.pecfest.Model.Common.Constants;
import in.pecfest.www.pecfest.Model.Common.DataHolder;
import in.pecfest.www.pecfest.Model.Common.Request;
import in.pecfest.www.pecfest.Model.Common.Response;
import in.pecfest.www.pecfest.Model.Sponsor.SponsorResponse;
import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.Utility;

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
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            }, Constants.SPLASH_SCREEN_WAIT);
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
    public void onRequestCompleted(String method, Response rr) {

        if (rr.isSuccess == false) {
            Toast.makeText(this, rr.errorMessage, Toast.LENGTH_LONG).show();
        }

        if (method.equals(Constants.METHOD.SPONSOR_REQUEST)) {
            try {
                sponsorResponse = (SponsorResponse) Utility.getObjectFromJson(rr.JsonResponse, SponsorResponse.class);

                if (sponsorResponse != null) {
                    Intent mainIntent = new Intent(this,HomeScreen.class);
                    mainIntent.putExtra("sponsorResponse",rr.JsonResponse);
                    startActivity(mainIntent);
                    finish();
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
