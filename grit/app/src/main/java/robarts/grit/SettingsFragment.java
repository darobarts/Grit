package robarts.grit;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
        final EditText hourText = (EditText) view.findViewById(R.id.hour_input);
        final EditText minuteText = (EditText) view.findViewById(R.id.minute_input);

        view.findViewById(R.id.settings_done_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDoneClickListener.onDoneClicked(Integer.parseInt(hourText.getText().toString()), Integer.parseInt(minuteText.getText().toString()));
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
