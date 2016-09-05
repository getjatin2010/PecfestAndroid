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
import android.widget.TextView;
import android.widget.Toast;

import in.pecfest.www.pecfest.Interfaces.CommunicationInterface;
import in.pecfest.www.pecfest.Model.Common.Constants;
import in.pecfest.www.pecfest.Model.Common.Request;
import in.pecfest.www.pecfest.Model.Common.Response;
import in.pecfest.www.pecfest.Model.Registration.RegistrationResponse;
import in.pecfest.www.pecfest.Model.login.LoginRequest;
import in.pecfest.www.pecfest.Model.login.LoginResponse;
import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.Utility;

public class login extends AppCompatActivity  implements CommunicationInterface
{
    TextView input;
    EditText inputPecfestId;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        input= (TextView) findViewById(R.id.link_signup);
        inputPecfestId = (EditText)findViewById(R.id.inputPecfestId);
        login = (Button)findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAndSaving();
            }
        });
    }
    public  void i(View v)
    {
        Intent i= new Intent(getApplicationContext(),register.class);
        finish();
        startActivity(i);
    }

    public void loginAndSaving()
    {
        LoginRequest lr = new LoginRequest();
        lr.pecfestId = inputPecfestId.getText().toString();

        Request req = new Request();
        req.heading = "Logging in";
        req.method = Constants.METHOD.LOGIN;
        req.requestData = Utility.GetJsonObject(lr);
        req.showPleaseWaitAtStart = true;
        req.hidePleaseWaitAtEnd = true;

    Utility.SendRequestToServer(this, req);



    }

    @Override
    public void onRequestCompleted(String method, Response rr) {


        if(rr.isSuccess==false) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }

        if(method.equals(Constants.METHOD.LOGIN))
        {

            final LoginResponse respone= (LoginResponse) Utility.getObjectFromJson(rr.JsonResponse, LoginResponse.class);
            if(respone.login ==false)
            {
                Utility.showProblemDialog(this, "Please enter Correct PecfestId");
            }
            else
            {
                Utility.saveId(inputPecfestId.getText().toString(),this);
                Toast.makeText(this,"Logged In",Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK, null);
                finish();
            }
        }


    }
}
