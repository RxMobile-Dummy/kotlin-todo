package com.remindme.firebase;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.remindme.R;

public class NotificationBuilder extends AsyncTask<String, Void, Bitmap> {
    private static final String TAG = "MyNotificationBuilder";
    private Context mContext;
    private PendingIntent mPendingIntent;
    private int mType;

    public static final String NOTIFICATION_KEY = "NOTIFICATION_KEY";
    NotificationCompat.InboxStyle inboxStyle;

    public NotificationBuilder(Context context, int notificationType, PendingIntent pendingIntent) {
        super();
        this.mContext = context;
        this.mType=notificationType;
        this.mPendingIntent=pendingIntent;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        return null;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        String channelId = mContext.getString(R.string.notification_channel);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = null;
        NotificationManager notificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder =
                new NotificationCompat.Builder(mContext, channelId)
                        // .setGroup(extrasObj.getGroupId())  // Uncomment this when you want to display bundled notification.
                        .setSmallIcon(R.drawable.ic_notif)
                        .setContentTitle("Notification")
                        .setContentText("Task Title")
                        //.setGroupSummary(false) // Uncomment this when you want to display bundled notification.
                        .setAutoCancel(true)

                        .setContentIntent(mPendingIntent)
                        .setSound(defaultSoundUri)
                        .setStyle(inboxStyle)

                        .setColor(mContext.getResources().getColor(R.color.teal_200));




        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        assert notificationBuilder != null;
        notificationManager.notify(NotificationID.getID() /* ID of notification */, notificationBuilder.build());

        /*
        * Uncomment this when you want to display bundled notification.
        * */
//        notificationManager.notify(mObj.getId(), notificationBuilder.build());
//        notificationManager.notify(extrasObj.getBundleNotificationId(), summaryNotificationBuilder.build());

    }
}
