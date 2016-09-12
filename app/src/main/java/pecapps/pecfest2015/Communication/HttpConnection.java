package pecapps.pecfest2015.Communication;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import pecapps.pecfest2015.Utilites.Results;

/**
 * Created by Abhi on 04-08-2016.
 */
public class HttpConnection {
    private int method;
    private String murl;
    private Results rts;
    private String []keys;
    private String []values;
    private String bodyData=null;
    int keyCount=0;
    int defaultTimeout=10000;
    int connTimeout=4000;

	/*
	 method specifies the GET or POST
	 0- get
	 1- post
	 u- specifies the url to connect
	  */

    public HttpConnection(String u){
        this(u,0);
    }

    public HttpConnection(String u, int m){
        rts=new Results();
        method=m;
        murl=u;
    }

    public HttpConnection(String u, int m, int t){
        rts=new Results();
        method=m;
        murl=u;
        defaultTimeout=t;
    }

    public HttpConnection(String u, int m, int t, int c){
        rts=new Results();
        method=m;
        murl=u;
        defaultTimeout=t;
        connTimeout=c;
    }


    //set the parameters for the request.
    public void setValues(String []k, String []v,int c){
        keys=new String[c];
        values=new String[c];
        keys=k;
        values=v;
        keyCount=c;
    }

    public void setValues(String...s){
        keyCount=s.length/2;
        keys=new String[keyCount];
        values=new String[keyCount];

        for(int i=0, j=0;i<keyCount;i++){
            keys[i]=s[j++];
            values[i]=s[j++];
        }

    }

    public void putBody(String data){
        bodyData= data;
    }

    public Results getData(){

        try{

            URL url=new URL(murl);

            HttpURLConnection conn=(HttpURLConnection)url.openConnection();

            conn.setReadTimeout(defaultTimeout);
            conn.setConnectTimeout(connTimeout);
            String met="GET";

            if(method==0)
                met="GET";
            if(method==1){
                met="POST";
                conn.setDoOutput(true);
            }
            conn.setDoInput(true);

            conn.setRequestMethod(met);

            //if any set of parameters for the request are specified, then below code attaches them to the http request.

            if(keyCount>0){

                Uri.Builder builder = new Uri.Builder();

                for(int i=0;i<keyCount;i++) {
                    builder.appendQueryParameter(keys[i],values[i]);
                }

                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

            }

            if(bodyData!=null){
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(bodyData);
                writer.flush();
                writer.close();
                os.close();

            }

            //connection to the url is made

            conn.connect();

			/*checking for the response from the server.
			 if response code != 200, means request wasn't completed and in that case
			 a ioexception is thrown. */

            int res=0;
            try{

                res=conn.getResponseCode();
                Log.v("Http: res", ""+res);
                if(res!=200)
                    throw new IOException();

                InputStream in=new BufferedInputStream(conn.getInputStream());
                rts.data=readStream(in);
                rts.code=9;
                Log.v("Http: data", rts.data);
            }
            catch(IOException io){
                rts.code=5;
                rts.data=""+res;
                Log.v("Http: io-error", io.getMessage());
            }

        }
        catch(Exception e){
            rts.code=4;
            rts.data=e.getMessage();
            Log.v("Http: error", e.getMessage());
            //e.printStackTrace();
        }

        return rts;
    }

    //function to read the stream of data received as a response from the server and convert it into string.
    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}

