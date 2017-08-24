package robarts.grit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

/**
 * Created by austinrobarts on 8/23/17.
 */

public class DailyNotification extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification_id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_menu_send)
                .setContentTitle("test")
                .setContentIntent(PendingIntent.getBroadcast(context, 0, new Intent(context, MainActivity.class), 0))
                .setContentText("Boom pow")
                .build();
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        notificationManager.notify(id, notification);
    }
}
