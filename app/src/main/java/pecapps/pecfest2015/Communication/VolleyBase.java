package pecapps.pecfest2015.Communication;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import android.content.Context;

/**
 * Created by Abhi on 12-08-2016.
 */
public class VolleyBase {
    private static VolleyBase mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private VolleyBase(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleyBase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyBase(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}