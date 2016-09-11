package in.pecfest.www.pecfest.Activites;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import in.pecfest.www.pecfest.Interfaces.CommunicationInterface;
import in.pecfest.www.pecfest.Model.Common.Constants;
import in.pecfest.www.pecfest.Model.Common.Request;
import in.pecfest.www.pecfest.Model.Common.Response;
import in.pecfest.www.pecfest.Model.Verify.VerifyRequest;
import in.pecfest.www.pecfest.Model.Verify.VerifyResponse;
import in.pecfest.www.pecfest.Model.login.LoginRequest;
import in.pecfest.www.pecfest.Model.login.LoginResponse;
import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.Utility;

public class navverify extends AppCompatActivity implements CommunicationInterface {

    Bundle bundle;
    EditText pecfestIdEdit;
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

        pecfestIdEdit=(EditText)findViewById(R.id.otp);
        btn_login = (Button)findViewById(R.id.btn_login);
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
        Request rr = new Request();
        rr.requestData = Utility.GetJsonObject(vr);
        rr.heading = "Verifying";
        rr.method = Constants.METHOD.VERIFY;
        Utility.SendRequestToServer(navverify.this, rr);
    }

    @Override
    public void onRequestCompleted(String method, Response rr) {
        if(rr.isSuccess==false)
        {
            Utility.showProblemDialog(this,"Some Error Occured. Please Try again");
             return;
        }
        if(method==Constants.METHOD.VERIFY) {
            final VerifyResponse respone = (VerifyResponse) Utility.getObjectFromJson(rr.JsonResponse, VerifyResponse.class);
            if (respone != null)
                if (respone.verified == false) {
                    Utility.showProblemDialog(this, respone.response);
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setMessage(respone.response);
                    alertDialogBuilder.setPositiveButton("yayy!!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            if(Utility.getsaveId((navverify.this))!=null && Utility.getsaveId((navverify.this)).pecfestId!=null) {
                                finish();
                            }
                            else {
                                loginAndSaving(pecfestIdEdit.getText().toString());
                            }
                            }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
        }
        if(method==Constants.METHOD.LOGIN) {

            final LoginResponse respone= (LoginResponse) Utility.getObjectFromJson(rr.JsonResponse, LoginResponse.class);
            Utility.saveId(respone, this);
            if(respone.login==true) {
                Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK, null);
            }
            finish();

        }

    }

    public void loginAndSaving(String inputPecfestId)
    {
        LoginRequest lr = new LoginRequest();
        lr.pecfestId = inputPecfestId;

        Request req = new Request();
        req.heading = "Logging in";
        req.method = Constants.METHOD.LOGIN;
        req.requestData = Utility.GetJsonObject(lr);
        req.showPleaseWaitAtStart = true;
        req.hidePleaseWaitAtEnd = true;

        Utility.SendRequestToServer(this, req);

    }
}
