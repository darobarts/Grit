package robarts.grit;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by austinrobarts on 7/5/17.
 */

public class SettingsFragment extends Fragment {
    private TimeSetListener timeSetListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        getActivity().setTitle("Settings");

        final TimePickerLayout timePicker = (TimePickerLayout) view.findViewById(R.id.settings_time_picker);
        view.findViewById(R.id.settings_done_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = timePicker.getMilitaryHour();
                int minute = timePicker.getMinute();
                timeSetListener.onTimeSet(hour, minute);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            timeSetListener = (TimeSetListener) context;
        } catch (ClassCastException err) {
            throw new ClassCastException(context.toString() + " must implement OnDoneClickListener");
        }
    }
}
