package in.pecfest.www.pecfest.Activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import in.pecfest.www.pecfest.Interfaces.CommunicationInterface;
import in.pecfest.www.pecfest.Model.Common.Constants;
import in.pecfest.www.pecfest.Model.Common.Request;
import in.pecfest.www.pecfest.Model.Common.Response;
import in.pecfest.www.pecfest.Model.Registration.RegistrationRequest;
import in.pecfest.www.pecfest.Model.Registration.RegistrationResponse;
import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.Utility;

public class register extends AppCompatActivity implements CommunicationInterface {
    Editable name;
    Editable college;
    Editable email;
    TextView t;
    Editable phone;
    Button l;
    EditText e1,e2,e3,e4;
    AutoCompleteTextView text;
    String[] colleges={"IIT Delhi ","PEC University Of Technology","IIT Bombay","UIET","Chitkara University","IIT Roorkee","IIT Mandi","IIT Ropar","Thapar University"};
    EditText e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    l= (Button) findViewById(R.id.btn_signup);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r();
            }
        });
        e1= (EditText) findViewById(R.id.input_name);
        text= (AutoCompleteTextView) findViewById(R.id.input_college);
String college=String.valueOf(text);

        e3= (EditText) findViewById(R.id.input_email);
        e4= (EditText) findViewById(R.id.input_phone);

        t= (TextView) findViewById(R.id.link_login);
//       e= (EditText) findViewById(R.id.t1);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,colleges);
        text.setAdapter(adapter);
        text.setThreshold(1);
    }

    public void a(View v)
    {
        Intent i= new Intent(getApplicationContext(),login.class);
        finish();
        startActivity(i);


    }
    public void r()
    {
       // AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Your details are:\n"+"Name:\t"+e1.getText()+"\n"+"College:\t"+college+"\n"
        +"Email\t"+e3.getText()+"\n"+"Phone:\t"+e4.getText()+"\n"+"Register?");

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                sendrequest();
            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();




    }


public void sendrequest()
{
    name=e1.getText();
    college=text.getText();
    email=e3.getText();
    phone=e4.getText();
    String name1,college1,email1,phone1;
    name1=name.toString();
    college1=college.toString();
    email1=email.toString();
    phone1=phone.toString();

    RegistrationRequest registrationRequest = new RegistrationRequest();
    registrationRequest.name = name1;
    registrationRequest.college = college1;
    registrationRequest.phone = phone1;
    registrationRequest.email = email1;
    registrationRequest.accomodation = 1;
    registrationRequest.gender = "male";


    Request rr= new Request();
    rr.method= Constants.METHOD.RESGISTRATION;
    rr.showPleaseWaitAtStart = true;
    rr.hidePleaseWaitAtEnd = true;
    rr.heading = null;
     rr.requestData= Utility.GetJsonObject(registrationRequest);
    Utility.SendRequestToServer(this,rr);

}

    @Override
    public void onRequestCompleted(String method, Response rr) {
        if(rr.isSuccess==false) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }

        if(method.equals(Constants.METHOD.RESGISTRATION))
        {



          RegistrationResponse respone= (RegistrationResponse) Utility.getObjectFromJson(rr.JsonResponse, RegistrationResponse.class);
            Toast t=Toast.makeText(getApplicationContext(),String.valueOf(rr.JsonResponse),Toast.LENGTH_SHORT);
            t.show();

            Intent i= new Intent(getApplicationContext(),verify.class);
            finish();
            startActivity(i);



        }
    }
}