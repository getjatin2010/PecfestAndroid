package pecapps.pecfest2015.Notifications;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Abhi on 06-08-2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    final String TAG= "Notifications Service";
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.v(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    public void sendRegistrationToServer(String rt){

    }
}
