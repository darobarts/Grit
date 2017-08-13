package robarts.grit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by austinrobarts on 7/5/17.
 */

public class DayFragment extends Fragment {
    private OnDayFinishListener onDayFinishListener;

    public interface OnDayFinishListener {
        void dayFinished();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);


        final SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        final int dayNumber = prefs.getInt(DayRetriever.DAY_KEY, -1);
        Day day = DayRetriever.getDay(dayNumber, getResources());
        getActivity().setTitle("Day " + dayNumber);

        //if could not find the assumed day in database
        if (day == null) {
            //reset to beginning of day count
            prefs.edit().putInt(DayRetriever.DAY_KEY, 0).commit();
        } else {
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
                    prefs.edit().putInt(DayRetriever.DAY_KEY, dayNumber + 1).commit();
                    onDayFinishListener.dayFinished();
                }
            });
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onDayFinishListener = (DayFragment.OnDayFinishListener) context;
        } catch (ClassCastException err) {
            throw new ClassCastException(context.toString() + " must implement OnDayFinishListener");
        }
    }
}
