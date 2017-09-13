package robarts.grit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by austinrobarts on 7/5/17.
 */

public class DayFragment extends Fragment {
    private OnDayFinishListener onDayFinishListener;
    private TimeSetListener timeSetListener;

    public interface OnDayFinishListener {
        void dayFinished();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);

        setHasOptionsMenu(true);
        final SharedPreferences prefs = getActivity().getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        final int dayNumber = prefs.getInt(DayRetriever.DAY_KEY, MainActivity.DAY_NOT_FOUND);
        Day day = DayRetriever.getDay(dayNumber, getResources());
        getActivity().setTitle("Day " + (dayNumber + 1));

        //if could not find the assumed day in database
        if (day == null) {
            //reset to beginning of day count
            prefs.edit().putInt(DayRetriever.DAY_KEY, 0).apply();
            day = DayRetriever.getDay(0, getResources());
        }

        if (day.hasText) {
            //set and display text on day screen
            TextView text = (TextView) view.findViewById(R.id.day_text);
            text.setText(day.text);
            view.findViewById(R.id.day_text).setVisibility(View.VISIBLE);
        }
        if (day.hasAudio) {
            //set and display text on day screen
            view.findViewById(R.id.day_audio_container).setVisibility(View.VISIBLE);
        }

        view.findViewById(R.id.day_finished_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDayFinishListener.dayFinished();
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.day_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //if user clicked change time
        if (item.getItemId() == R.id.time_change) {
            //create popup to choose new time
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final TimePickerLayout timePickerLayout = new TimePickerLayout(getContext());
            builder.setTitle("Choose a new time for alerts");
            builder.setView(timePickerLayout);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    timeSetListener.onTimeSet(timePickerLayout.getMilitaryHour(), timePickerLayout.getMinute());
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
            //Toast.makeText(getContext(), "Time dialog goes here", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onDayFinishListener = (DayFragment.OnDayFinishListener) context;
            timeSetListener = (TimeSetListener) context;
        } catch (ClassCastException err) {
            throw new ClassCastException(context.toString() + " must implement interfaces OnDayFinishListener and TimeSetListener");
        }
    }
}
