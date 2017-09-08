package robarts.grit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

/**
 * Created by austinrobarts on 9/7/17.
 */
public class TimePickerLayout extends LinearLayout {

    private NumberPicker timeFramePicker;
    private NumberPicker hourPicker;
    private NumberPicker minutePicker;
    private static final int MIN_HOUR = 1;
    private static final int MAX_HOUR = 12;
    private static final int MIN_MINUTE = 0;
    private static final int MAX_MINUTE = 59;
    private static final String[] TIME_FRAMES = new String[]{"AM","PM"};

    public TimePickerLayout(Context context) {
        super(context);
        intializeViews(context);
    }
    public TimePickerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        intializeViews(context);
    }

    public void setMinute(int minute) {
        minutePicker.setValue(minute);
    }

    public void setMilitaryHour(int hour) {
        hourPicker.setValue(hour);
    }

    public int getHour() {
        return hourPicker.getValue();
    }

    /**
     * Getter method for hour based on 24 hour time
     * @return returns the current hour
     */
    public int getMilitaryHour() {
        return hourPicker.getValue() * (timeFramePicker.getValue() + 1);
    }

    public int getMinute() {
        return minutePicker.getValue();
    }

    private void intializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.time_picker, this);

        timeFramePicker = (NumberPicker) this.findViewById(R.id.am_pm);
        timeFramePicker.setMinValue(0);
        timeFramePicker.setMaxValue(1);
        timeFramePicker.setDisplayedValues(TIME_FRAMES);

        hourPicker = (NumberPicker) this.findViewById(R.id.hour);
        hourPicker.setMinValue(MIN_HOUR);
        hourPicker.setMaxValue(MAX_HOUR);

        minutePicker = (NumberPicker) this.findViewById(R.id.minute);
        minutePicker.setMinValue(MIN_MINUTE);
        minutePicker.setMaxValue(MAX_MINUTE);
        minutePicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
    }
}
