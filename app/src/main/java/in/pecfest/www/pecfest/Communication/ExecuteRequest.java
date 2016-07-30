package in.pecfest.www.pecfest.Communication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import in.pecfest.www.pecfest.Interfaces.CommunicationInterface;
import in.pecfest.www.pecfest.Model.Common.Request;
import in.pecfest.www.pecfest.Model.Common.Response;


/**
 * Created by jatin on 20/5/15.
 */
public class ExecuteRequest extends AsyncTask<Void,Void,Response> {

    Context context;
    Request request;

    static ProgressDialog pbDialog;
    String baseURL = "http://www.pecfest.in/";
    CommunicationInterface listener;


    public ExecuteRequest(Context context, Request request, CommunicationInterface listener)
    {
        this.context = context;
        this.request = request;
        this.listener = listener;
    }

    //String responseString = "";

    @Override
    protected void onPreExecute()
    {
        if (request.showPleaseWaitAtStart) {
            pbDialog = pbDialog.show(context, request.heading, "Please wait.. ", true, false);
        }
        super.onPreExecute();
    }

    private HttpURLConnection getConnection() throws Exception
    {
        String urlString = baseURL + request.method;
        URL url = new URL(urlString);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();


        conn.setReadTimeout(10000 /*milliseconds*/);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);


        // Set Headers Here
        conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        //conn.setRequestProperty("sessionId", Utility.loggedInUser.sessionId);

        return  conn;
    }

    @Override
    protected Response doInBackground(Void... params) {

        Response rr = new Response();

        try {
            HttpURLConnection conn = getConnection();
            conn.connect();
            OutputStream os = new BufferedOutputStream(conn.getOutputStream());
            os.write(request.requestData.getBytes());
            os.flush();

            InputStream is = conn.getInputStream();

            rr.isSuccess = true;
            rr.JsonResponse = ReadInputStream(is);
        }
        catch (Exception e) {
            rr.isSuccess = false;
            rr.errorMessage = e.toString();
        }
        return rr;
    }

    private String ReadInputStream(InputStream is) throws Exception{
        String line = "";
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        return total.toString();
    }

    protected void onPostExecute(Response rr) {
        if(rr == null)
        {
            Toast.makeText(context, "Server Problem", Toast.LENGTH_SHORT).show();
            return;
        }
        if (request.hidePleaseWaitAtEnd) {
            if(pbDialog != null)
            {
                pbDialog.dismiss();
            }
        }
        listener.onRequestCompleted(request.method, rr);
    }

}

