package in.pecfest.www.pecfest.Utilites;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import in.pecfest.www.pecfest.Communication.ExecuteRequest;
import in.pecfest.www.pecfest.Interfaces.CommunicationInterface;
import in.pecfest.www.pecfest.Model.Common.Request;
import in.pecfest.www.pecfest.R;


/**
 * Created by Pradeep on 19-01-2016.
 */
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

    public static String getUsername(Context context){
        if(userName == null || userName.isEmpty()){
            userName= context.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE).getString("pecfest_user_name", "");
        }
        return userName;
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
}
