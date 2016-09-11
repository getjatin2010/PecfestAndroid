package in.pecfest.www.pecfest.Notifications;

import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import in.pecfest.www.pecfest.R;
import in.pecfest.www.pecfest.Utilites.Utility;
import in.pecfest.www.pecfest.Utilites.getBitmap;

/**
 * Created by Abhi on 06-08-2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    final String TAG= "Notifications message";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
            if(remoteMessage.getData()!=null) {
                if(remoteMessage.getData().containsKey("type") && remoteMessage.getData().get("type").equals("notification")) {
                    Utility.storeNotifs(this, Utility.GetJsonObject(remoteMessage.getData()));
                    generateNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("text"),remoteMessage.getData().get("photo"), remoteMessage.getData().containsKey("collapse"));
                }
            }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            generateNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), null, false);
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());

            Utility.storeNotifs(this, Utility.GetJsonObject(remoteMessage.getData()));
        }

    }

    public void generateNotification(String title, String body, String photo, boolean collapse){
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_event)
                        .setContentTitle(title)
                        .setDefaults(android.support.v4.app.NotificationCompat.DEFAULT_SOUND | android.support.v4.app.NotificationCompat.DEFAULT_VIBRATE);

        if(body.length()>100){
            mBuilder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(body).setSummaryText(body));
        }
        else if(photo!=null){
            Bitmap b= getBitmap.fetchFromWeb(photo);
            getBitmap.saveToLocal(Utility.getIdForPhotos(photo), b);
            mBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture(b).setSummaryText(body));
        }

        // Sets an ID for the notification

        int mNotificationId= 1;
        if(!collapse)
            mNotificationId = (int)(Math.random() * 99);
        else{
            int c=0;
            if((c=Utility.getNewNotifs(this)-1)>1)
                mBuilder.setContentText("  "+c+" new notifications");
            else
                mBuilder.setContentText(body);
        }
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }

}
