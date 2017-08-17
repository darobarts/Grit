package robarts.grit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by austinrobarts on 7/5/17.
 */

public class StartFragment extends Fragment {

    private OnStartClickListener startCallback;

    public interface OnStartClickListener {
        void startClicked();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        getActivity().setTitle("Grit");
        view.findViewById(R.id.start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCallback.startClicked();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            startCallback = (OnStartClickListener) context;
            getActivity().setTitle(getResources().getString(R.string.action_settings));
        } catch (ClassCastException err) {
            throw new ClassCastException(context.toString() + " must implement OnStartClickListener");
        }
    }

}
