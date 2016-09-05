package in.pecfest.www.pecfest.Activites;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    TextView loginLink;
    Editable phone;
    RadioButton male,female,AccoYes,AccoNo;
    String gender = "";
    String acco = "";

    Button signUpButton;
    EditText fullName,e2, emailEditText,phoneNumber;
    AutoCompleteTextView collegeName;
    String[] colleges={"IIT Delhi ","PEC University Of Technology","IIT Bombay","UIET","Chitkara University","IIT Roorkee","IIT Mandi","IIT Ropar","Thapar University"};
    EditText e;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar=(Toolbar)findViewById(R.id.notification_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*TextView returnHome=(TextView)findViewById(R.id.registrationActionBar);
        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        // mask actionbar title with bitmap------------------------------------
        TextView actionBarTitle=(TextView)findViewById(R.id.registerText_Register);
        //TextView actionBarNotice=(TextView)findViewById(R.id.registrationActionBar);
        Bitmap overlay= BitmapFactory.decodeResource(getResources(),R.drawable.title_overlay);
        Shader shader=new BitmapShader(overlay,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        actionBarTitle.getPaint().setShader(shader);
        //shader=new BitmapShader(overlay,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        //actionBarNotice.getPaint().setShader(shader);
        //---------------------------------------------------------------------
        //----------------------------------------------------------------------------
        signUpButton = (Button) findViewById(R.id.btn_signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        fullName = (EditText) findViewById(R.id.input_name);
        collegeName = (AutoCompleteTextView) findViewById(R.id.input_college);


        male = (RadioButton)findViewById(R.id.Male);
        female = (RadioButton)findViewById(R.id.Female);

        AccoYes = (RadioButton)findViewById(R.id.YesAccomodation);
        AccoNo = (RadioButton)findViewById(R.id.NoAccomodation);



        emailEditText = (EditText) findViewById(R.id.input_email);
        phoneNumber= (EditText) findViewById(R.id.input_phone);

        loginLink = (TextView) findViewById(R.id.link_login);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,colleges);
        collegeName.setAdapter(adapter);
        collegeName.setThreshold(1);
    }

    public void a(View v)
    {
        Intent i= new Intent(getApplicationContext(),login.class);
        finish();
        startActivity(i);


    }
    public  boolean validateLetters(String name) {

        if(name==null)
            return false;

        if(name.length()<3)
            return false;

        for (int i = 0; i < name.length(); i++)
            if ( !( ((int) name.charAt(i) >= 65 && (int) name.charAt(i) <= 90) || ((int) name.charAt(i) >= 97 && (int) name.charAt(i) <= 122) || (name.charAt(i) == ' ' && i>=3)))
                return false;

        return true;
    }

    public boolean validateEmailID(String emailStr) {

        if(emailStr==null || emailStr.length()<3)
            return false;


        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        //public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
    public boolean validateCollege(String emailStr) {

        if(emailStr==null || emailStr.length()<3)
            return false;

        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[a-zA-Z](?=.{2,})[^;:+=_*^%$#@!]*$", Pattern.CASE_INSENSITIVE);
        //public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    public boolean validatePhone(String phone) {
        try{
            String value = "";
            if(phone.length()!=10)
                return false;
            for(int i = 0 ; i<10 ; i++) {
                value = phone.substring(i,i+1);
                Integer.parseInt(value);
            }
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public void showProblemDialog(String message)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private boolean setGender()
    {
        if(male.isChecked())
        {
            gender = "male";
            return true;
        }
        if(female.isChecked())
        {
            gender = "female";
            return true;
        }
        return false;
    }

    private boolean setAcco()
    {
        if(AccoYes.isChecked())
        {
            acco = "1";
            return true;
        }
        if(AccoNo.isChecked())
        {
            acco = "0";
            return true;
        }
        return false;
    }

    public void register()
    {
        String fullNameText = fullName.getText().toString();
        if(!validateLetters(fullNameText))
        {
            showProblemDialog("Please enter correct name");
            return;
        }
        String collegeText =  collegeName.getText().toString();
        if(!validateCollege(collegeText))
        {
            showProblemDialog("Please enter correct college name");
            return;
        }

        String phoneNumberText  =phoneNumber.getText().toString();
        if(!validatePhone(phoneNumberText))
        {
            showProblemDialog("Please enter correct phone number");
            return;
        }
        String emailTextString = emailEditText.getText().toString();
            if(!validateEmailID(emailTextString))
            {
                showProblemDialog("Please enter correct Email");
                return;
            }
        if(!setGender())
        {
            showProblemDialog("Please select gender");
            return;
        }
        if(!setAcco())
        {
            showProblemDialog("Please select Accomodation");
            return;
        }
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Your details are:\n"+"Name:\t"+ fullNameText+"\n"+"College:\t"+collegeText+"\n"
                +"Email\t"+ emailTextString+"\n"+"Phone:\t"+phoneNumberText+"\n"+"Register?");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                sendrequest();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }




    public void sendrequest()
    {
        name= fullName.getText();
        college= collegeName.getText();
        email= emailEditText.getText();
        phone=phoneNumber.getText();

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
        registrationRequest.accomodation = Integer.valueOf(acco);
        registrationRequest.gender = gender;

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

            final RegistrationResponse respone= (RegistrationResponse) Utility.getObjectFromJson(rr.JsonResponse, RegistrationResponse.class);
            if(respone.registered ==false)
            {
                showProblemDialog(respone.response);
            }
            else
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("You have been registered. Please Enter OTP on next page to verify your pecfestID send to your mobile number"+respone.response);
                alertDialogBuilder.setPositiveButton("yayy!!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent i = new Intent(getApplicationContext(), navverify.class);
                        i.putExtra("pecfestId", respone.response);
                        i.putExtra("mobile",phone.toString() );
                        i.putExtra("fromNav",false);
                        finish();
                        startActivity(i);
                    }
                });


                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}