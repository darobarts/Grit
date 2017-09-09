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

public class MainActivity extends AppCompatActivity implements StartFragment.OnStartClickListener,
TimeSetListener, DayFragment.OnDayFinishListener {

    private static final String HOUR_KEY = "hour";
    private static final String MINUTE_KEY = "minute";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //get the current day the user is on or add day 1 (index 0) if user's first time
        int day = 0;
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        day = sharedPref.getInt(DayRetriever.DAY_KEY, -1);
        //if day has not been set previously dont display a day
        if (day == -1) {
            deployFragment(new StartFragment());
        } else {
            deployFragment(new DayFragment());
        }
    }

    @Override
    public void onTimeSet(int hour, int minute) {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        //set first day ready
        sharedPreferences.edit().putInt(DayRetriever.DAY_KEY, 0).commit();
        //set time of notifications
        sharedPreferences.edit().putInt(HOUR_KEY, hour).apply();
        sharedPreferences.edit().putInt(MINUTE_KEY, hour).apply();
        //create notification
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, DailyNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 15000, pendingIntent);

        deployFragment(new DayFragment());
    }

    @Override
    public void startClicked() {
        deployFragment(new SettingsFragment());
    }

    @Override
    public void dayFinished() {
        Toast.makeText(this, getResources().getString(R.string.end_day_notification), Toast.LENGTH_SHORT).show();
    }

    private void deployFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
