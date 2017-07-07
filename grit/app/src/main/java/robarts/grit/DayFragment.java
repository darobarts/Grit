package robarts.grit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        view.findViewById(R.id.day_finished_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDayFinishListener.dayFinished();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onDayFinishListener = (DayFragment.OnDayFinishListener) context;
            getActivity().setTitle("Day One");
        } catch (ClassCastException err) {
            throw new ClassCastException(context.toString() + " must implement OnDayFinishListener");
        }
    }
}
