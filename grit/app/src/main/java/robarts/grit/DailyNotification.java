package robarts.grit;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by austinrobarts on 8/23/17.
 */

public class DailyNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        SharedPreferences prefs = context.getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        int currentDay = prefs.getInt(DayRetriever.DAY_KEY, MainActivity.DAY_NOT_FOUND) + 1;
        prefs.edit().putInt(DayRetriever.DAY_KEY, currentDay).apply();
        Intent notificatonIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificatonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_timer_black_24dp)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentIntent(contentIntent)
                .setContentText(String.format(context.getString(R.string.day_notification), currentDay + 1))
                .build();
        notificationManager.notify(0, notification);
    }
}
