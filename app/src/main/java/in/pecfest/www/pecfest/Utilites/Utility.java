package in.pecfest.www.pecfest.Utilites;

import android.content.Context;
import android.support.v7.app.ActionBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import in.pecfest.www.pecfest.Communication.ExecuteRequest;
import in.pecfest.www.pecfest.Interfaces.CommunicationInterface;
import in.pecfest.www.pecfest.Model.Common.Request;


/**
 * Created by Pradeep on 19-01-2016.
 */
public class Utility {

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
}
