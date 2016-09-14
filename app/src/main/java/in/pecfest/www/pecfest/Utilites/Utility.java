package in.pecfest.www.pecfest.Utilites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Calendar;

import in.pecfest.www.pecfest.Communication.ExecuteRequest;
import in.pecfest.www.pecfest.Interfaces.CommunicationInterface;
import in.pecfest.www.pecfest.Model.Common.Constants;
import in.pecfest.www.pecfest.Model.Common.Request;
import in.pecfest.www.pecfest.Model.login.LoginResponse;
import in.pecfest.www.pecfest.R;


public class Utility {

    private static String baseUrl = "";
    final public static String sharedPreferences = "in.pecfest.preferences";
    public static String userName = "";

    public static String GetJsonObject(Object obj) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(obj);
    }


    public static Object getObjectFromJson(String jsonString, Class toClass) {
        Gson gson = new Gson();
        Object obj = gson.fromJson(jsonString, toClass);
        return obj;
    }

    public static void SendRequestToServer(Context context, Request request) {
        CommunicationInterface tt = (CommunicationInterface) context;
        ExecuteRequest er = new ExecuteRequest(context, request, tt);
        er.execute();
    }

    public static void setNewNotificationIncrement(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        int num = sharedPreferences.getInt(Constants.newNotifs, 0);
        num = num+1;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.newNotifs,num);
        editor.commit();
    }

    public static void saveDateHour(String data,Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("dateHour", data);
        editor.commit();
    }

    public static String getDateHour(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getString("dateHour", null);

    }

    public static void setNewNotificationZero(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constants.newNotifs,0);
        editor.commit();
    }

    public static int getNewNotifs(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        int num = sharedPreferences.getInt(Constants.newNotifs, 0);
        return num;
    }



    public static void setActionBarTitle(String title, ActionBar actionBar) {
        actionBar.setTitle(title);
    }

    public static String getBaseUrl(Context context) {
        if (!baseUrl.contains("http")) {
            baseUrl = context.getString(R.string.base_url);
        }
        return baseUrl;
    }

    public static String getUsername(Context context) {
        if (userName == null || userName.isEmpty()) {
            userName = context.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE).getString("pecfest_user_name", "");
        }
        return userName;
    }

    public static void saveId(LoginResponse lr, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pecId", lr.pecfestId);
        editor.putString("name", lr.name);
        editor.putString("phone", lr.phone);
        editor.commit();
    }

    public static LoginResponse getsaveId(Context context) {
        LoginResponse lr = new LoginResponse();
        SharedPreferences sharedPreferences = context.getSharedPreferences("appPreferences", Context.MODE_PRIVATE);
        lr.pecfestId = sharedPreferences.getString("pecId", null);
        lr.name = sharedPreferences.getString("name", null);
        lr.phone = sharedPreferences.getString("phone", null);
        return lr;
    }

    public static void setUsername(Context context, String pun) {
        context.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE).edit().putString("pecfest_user_name", pun).commit();
        userName = pun;
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getSharedPreferencesEditor(Context context) {
        return context.getSharedPreferences(sharedPreferences, Context.MODE_PRIVATE).edit();
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showProblemDialog(Context context, String message) {
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

    public static ArrayList<String> getNotifs(Context context) {
        String key = getSharedPreferences(context).getString("notifKeys", null);
        ArrayList<String> al = new ArrayList<String>();

        try {

            if (key == null)
                return al;
            String keys[] = key.split(",");
            for (int i = 0; i < keys.length; i++) {
                if (keys[i] == null || keys[i].isEmpty())
                    continue;
                String temp = getSharedPreferences(context).getString("n" + keys[i], null);
                if (temp != null)
                    al.add(temp);
            }
        }
        catch(Exception e){

        }
        return al;
    }

    public static void storeNotifs(Context context, String data){
        double rand= Math.random();
        Utility.getSharedPreferencesEditor(context).putString("notifKeys",Utility.getSharedPreferences(context).getString("notifKeys","")+","+rand).putString("n"+rand,data).commit();
        setNewNotificationIncrement(context);
    }

    public static void clearNotifs(Context context) {

        try {
            String key = getSharedPreferences(context).getString("notifKeys", null);

            String keys[] = key.split(",");
            for (int i = 0; i < keys.length; i++) {
                if (keys[i] == null || keys[i].isEmpty())
                    continue;
                getSharedPreferencesEditor(context).remove("n" + keys[i]);
            }
            getSharedPreferencesEditor(context).remove("notifKeys").commit();
        }
        catch(Exception e){

        }
    }


    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static getBitmap GetBitmap(String url, ImageView iv, boolean resize, int width, boolean fetchFromLocal){
        getBitmap gbp=(getBitmap)new getBitmap(url, getIdForPhotos(url) , iv, null, resize, width, fetchFromLocal,false).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        return gbp;
    }

    public static getBitmap GetBitmap(String url, ImageView iv, boolean resize, int width, boolean fetchFromLocal,boolean round){
        getBitmap gbp=(getBitmap)new getBitmap(url, getIdForPhotos(url) , iv, null, resize, width, fetchFromLocal,round).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        return gbp;
    }

    public static String getIdForPhotos(String url){
        return (url.substring(url.lastIndexOf(".")-9, url.length())).replace("/","-");
    }
}

