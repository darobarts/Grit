package robarts.grit;

import android.content.res.Resources;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Simple class with static method to get a specific day from the dayData csv file
 *
 * Created by austinrobarts on 8/11/17.
 */

public class DayRetriever {

    public static final String DAY_KEY = "day";
    public static final int NUM_DAYS = 30;
    /**
     * Gets the day corresponding to the day number
     * @param dayNumber number of day to get. Starting at 1
     * @return a Day object with all the fields associated with Day filled
     */
    public static Day getDay(int dayNumber, Resources resources) {
        String dayText = resources.getStringArray(R.array.day_texts)[dayNumber];
        return new Day(dayNumber, true, false, dayText, null);
    }
}
