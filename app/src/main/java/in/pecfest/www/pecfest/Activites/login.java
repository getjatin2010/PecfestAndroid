package in.pecfest.www.pecfest.Activites;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.Utility;

public class login extends AppCompatActivity {
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
        Utility.saveId(inputPecfestId.getText().toString(),this);
        Toast.makeText(this,"Logged In",Toast.LENGTH_SHORT).show();
        setResult(Activity.RESULT_OK, null);
        finish();
    }

}
