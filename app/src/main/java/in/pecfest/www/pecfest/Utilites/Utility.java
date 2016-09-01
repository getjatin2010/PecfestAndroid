package in.pecfest.www.pecfest.Utilites;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import in.pecfest.www.pecfest.Communication.ExecuteRequest;
import in.pecfest.www.pecfest.Interfaces.CommunicationInterface;
import in.pecfest.www.pecfest.Model.Common.Request;
import in.pecfest.www.pecfest.R;


public class Utility {

    private static String baseUrl= "";
    final public static String sharedPreferences= "in.pecfest.preferences";
    public static String userName= "";

    public static String GetJsonObject(Object obj)
    {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(obj);
    }


    public  static Object getObjectFromJson(String jsonString,Class toClass)
    {
        Gson gson = new Gson();
        Object obj = gson.fromJson(jsonString, toClass);
        return  obj;
    }

   public  static void SendRequestToServer ( Context context, Request request )
   {
       CommunicationInterface tt = (CommunicationInterface)context;
       ExecuteRequest er = new ExecuteRequest(context, request,tt);
       er.execute();
   }


    public static void setActionBarTitle(String title, ActionBar actionBar)
    {
        actionBar.setTitle(title);
    }

    public static String getBaseUrl(Context context){
        if(!baseUrl.contains("http")){
            baseUrl= context.getString(R.string.base_url);
        }
        return baseUrl;
    }

    public static String getUsername(Context context) {
        if (userName == null || userName.isEmpty()) {
            userName = context.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE).getString("pecfest_user_name", "");
        }
        return userName;
    }

    public static void saveId(String id,Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor  =sharedPreferences.edit();
        editor.putString("pecId",id);
        editor.commit();
    }

    public static String getsaveId(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appPreferences",Context.MODE_PRIVATE);
        return sharedPreferences.getString("pecId","");
    }

    public static void setUsername(Context context, String pun){
        context.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE).edit().putString("pecfest_user_name", pun).commit();
        userName= pun;
    }

    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE);
    }

    public SharedPreferences.Editor getSharedPreferencesEditor(Context context){
        return context.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE).edit();
    } 

    public static  void showDialogForPecfestID(final Context context)
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        final EditText input = new EditText(context);
        alertDialog.setTitle("Enter Your PecfestId");
        alertDialog.setIcon(R.mipmap.ic_launcher);
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        String pecfestOld = Utility.getsaveId(context);
        input.setHint("PecfestID");
        if(pecfestOld!="")
            input.setText(pecfestOld);
        alertDialog.setView(input);
        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "DONE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String pecfestId = input.getText().toString();
                if (pecfestId != null) {
                    Utility.saveId(pecfestId,context);
                }
            }
        });
        alertDialog.show();

    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
