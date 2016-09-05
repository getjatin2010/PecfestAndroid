package in.pecfest.www.pecfest.Activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import in.pecfest.www.pecfest.Interfaces.CommunicationInterface;
import in.pecfest.www.pecfest.Model.Common.Constants;
import in.pecfest.www.pecfest.Model.Common.Request;
import in.pecfest.www.pecfest.Model.Common.Response;
import in.pecfest.www.pecfest.Model.Verify.VerifyRequest;
import in.pecfest.www.pecfest.Model.Verify.VerifyResponse;
import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.Utility;

public class navverify extends AppCompatActivity implements CommunicationInterface {

    Bundle bundle;
    EditText phoneEdit,pecfestIdEdit;
    String pecfestId;
    String phone;
    Button btn_login;

    Boolean fromNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navverify);
        bundle = getIntent().getExtras();
        try {
            fromNav = bundle.getBoolean("fromNav");
        }
        catch(Exception e)
        {
            fromNav = true;
        }
        phoneEdit = (EditText)findViewById(R.id.number);
        pecfestIdEdit=(EditText)findViewById(R.id.otp);
        btn_login = (Button)findViewById(R.id.btn_login);

        if(!fromNav) {
            pecfestId = bundle.getString("pecfestId");
            phone = bundle.getString("mobile");
            phoneEdit.setText(phone);
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });

        }

    private void sendRequest()
    {
        VerifyRequest vr = new VerifyRequest();
        vr.pecfestId = pecfestIdEdit.getText().toString();
        vr.phone = phoneEdit.getText().toString();
        Request rr = new Request();
        rr.requestData = Utility.GetJsonObject(vr);
        rr.heading = "Verifying";
        rr.method = Constants.METHOD.VERIFY;
        Utility.SendRequestToServer(navverify.this,rr);
    }

    @Override
    public void onRequestCompleted(String method, Response rr) {
        if(rr.isSuccess==false)
        {
            Utility.showProblemDialog(this,"Some Error Occured. Please Try again");
             return;
        }

        final VerifyResponse respone= (VerifyResponse) Utility.getObjectFromJson(rr.JsonResponse, VerifyResponse.class);
        if(respone!=null)
        if(respone.verified==false)
        {
            Utility.showProblemDialog(this,respone.response);
        }
        else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(respone.response);
            alertDialogBuilder.setPositiveButton("yayy!!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    finish();
                }
            });


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }


    }
}
