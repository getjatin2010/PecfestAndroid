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
import in.pecfest.www.pecfest.Model.login.LoginResponse;
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

    public static void saveId(LoginResponse lr,Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor  =sharedPreferences.edit();
        editor.putString("pecId",lr.pecfestId);
        editor.putString("name",lr.name);
        editor.putString("phone",lr.phone);
        editor.commit();
    }

    public static LoginResponse getsaveId(Context context)
    {
        LoginResponse lr = new LoginResponse();
        SharedPreferences sharedPreferences = context.getSharedPreferences("appPreferences",Context.MODE_PRIVATE);
        lr.pecfestId = sharedPreferences.getString("pecId",null);
        lr.name = sharedPreferences.getString("name",null);
        lr.phone = sharedPreferences.getString("phone",null);
        return lr;
    }

    public static void setUsername(Context context, String pun){
        context.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE).edit().putString("pecfest_user_name", pun).commit();
        userName= pun;
    }

    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getSharedPreferencesEditor(Context context){
        return context.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE).edit();
    } 



    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showProblemDialog(Context context,String message)
    {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });


        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
