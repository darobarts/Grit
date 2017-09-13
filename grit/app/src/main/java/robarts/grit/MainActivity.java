package robarts.grit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements StartFragment.OnStartClickListener,
TimeSetListener, DayFragment.OnDayFinishListener {

    private static final String HOUR_KEY = "hour";
    private static final String MINUTE_KEY = "minute";
    public static final int DAY_NOT_FOUND = -2;
    public static final String PREF_NAME = "prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //get the current day the user is on or add day 1 (index 0) if user's first time
        int day = 0;
        SharedPreferences sharedPref = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        day = sharedPref.getInt(DayRetriever.DAY_KEY, DAY_NOT_FOUND);
        //if day has not been set previously dont display a day
        if (day == DAY_NOT_FOUND) {
            deployFragment(new StartFragment());
        } else {
            deployFragment(new DayFragment());
        }
    }

    @Override
    public void onTimeSet(int hour, int minute) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        //set time of notifications
        sharedPreferences.edit().putInt(HOUR_KEY, hour).apply();
        sharedPreferences.edit().putInt(MINUTE_KEY, hour).apply();

        deployFragment(new DayFragment());
    }

    @Override
    public void startClicked() {
        deployFragment(new SettingsFragment());
    }

    @Override
    public void dayFinished() {
        queueNotification();
        Toast.makeText(this, getResources().getString(R.string.end_day_notification), Toast.LENGTH_SHORT).show();
    }

    private void queueNotification() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        SharedPreferences sharedPreferences = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int hour = sharedPreferences.getInt(HOUR_KEY, -1);
        int minute = sharedPreferences.getInt(MINUTE_KEY, -1);

        Intent intent = new Intent(this, DailyNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        //create calendar to get milliseconds for user set notification time
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

    private void deployFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
