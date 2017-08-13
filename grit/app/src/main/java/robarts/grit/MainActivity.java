package robarts.grit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements StartFragment.OnStartClickListener,
SettingsFragment.OnDoneClickListener, DayFragment.OnDayFinishListener {

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
        if (day == -1) {
            deployFragment(new StartFragment());
        } else {
            deployFragment(new DayFragment());
        }


    }

    @Override
    public void onDoneClicked(int hour, int minute) {
        this.getPreferences(Context.MODE_PRIVATE).edit().putInt(DayRetriever.DAY_KEY, 0).commit();
        deployFragment(new DayFragment());
    }

    @Override
    public void startClicked() {
        deployFragment(new SettingsFragment());
    }

    @Override
    public void dayFinished() {
        Toast.makeText(this, "CONGRATS ON COMPLETING DAY", Toast.LENGTH_SHORT).show();
    }

    private void deployFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
