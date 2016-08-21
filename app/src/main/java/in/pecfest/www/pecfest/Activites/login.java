package in.pecfest.www.pecfest.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import in.pecfest.www.pecfest.R;

public class login extends AppCompatActivity {
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        t= (TextView) findViewById(R.id.link_signup);
    }
    public  void i(View v)
    {
        Intent i= new Intent(getApplicationContext(),register.class)
                ;
        startActivity(i);
    }
}
