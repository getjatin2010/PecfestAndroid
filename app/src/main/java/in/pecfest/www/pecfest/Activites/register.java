package in.pecfest.www.pecfest.Activites;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import in.pecfest.www.pecfest.R;

public class register extends AppCompatActivity {

    Button l;
    EditText e1,e2,e3,e4;
    EditText e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        l= (Button) findViewById(R.id.button3);
        e1= (EditText) findViewById(R.id.input_name);
        e2= (EditText) findViewById(R.id.input_College);
        e3= (EditText) findViewById(R.id.input_Email);
        e4= (EditText) findViewById(R.id.input_phone);
       e= (EditText) findViewById(R.id.t1);

    }
    public void r(View v)
    {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Your details are: \n" + "1.Name : \t "+ e1.getText()+ "\n"+ "2.College :\t"+ e2.getText()+"\n"+"3.Email :\t"
        +e3.getText()+"\n"+"4.Phone:\t"+e4.getText()+"\n"+"Are you Sure you want to continue?");

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(getApplicationContext(),"Registered",Toast.LENGTH_LONG).show();
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
}
