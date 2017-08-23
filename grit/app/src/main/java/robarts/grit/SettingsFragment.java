package robarts.grit;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;

/**
 * Created by austinrobarts on 7/5/17.
 */

public class SettingsFragment extends Fragment {
    private OnDoneClickListener onDoneClickListener;

    public interface OnDoneClickListener {
        void onDoneClicked(int hour, int minute);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        getActivity().setTitle("Settings");
        //set up display for AM/PM
        NumberPicker timeFramePicker = (NumberPicker) view.findViewById(R.id.am_pm);
        final NumberPicker hourPicker = (NumberPicker) view.findViewById(R.id.hour);
        hourPicker.setMinValue(1);
        hourPicker.setMaxValue(12);
        final NumberPicker minutePicker = (NumberPicker) view.findViewById(R.id.minute);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
        timeFramePicker.setMinValue(0);
        timeFramePicker.setMaxValue(1);
        timeFramePicker.setDisplayedValues(new String[]{"AM", "PM"});

        view.findViewById(R.id.settings_done_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDoneClickListener.onDoneClicked(hourPicker.getValue(), minutePicker.getValue());
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onDoneClickListener = (SettingsFragment.OnDoneClickListener) context;
        } catch (ClassCastException err) {
            throw new ClassCastException(context.toString() + " must implement OnDoneClickListener");
        }
    }
}
