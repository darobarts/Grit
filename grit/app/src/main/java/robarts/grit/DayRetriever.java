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
    private static final String CSV_FILENAME = "dayData.csv";
    private static final int NUM_DAYS = 30;
    private static final int DAY_COL = 0;
    private static final int HAS_TEXT_COL = 1;
    private static final int HAS_AUDIO_COL = 2;
    private static final int TEXT_COL = 3;
    private static final int AUDIO_FILENAME_COL = 4;
    /**
     * Gets the day corresponding to the day number
     * @param dayNumber number of day to get. Starting at 1
     * @return a Day object with all the fields associated with Day filled
     */
    public static Day getDay(int dayNumber, Resources resources) {
        try {
            Scanner scanner = new Scanner(resources.getAssets().open(CSV_FILENAME));
            String[] entries = new String[NUM_DAYS];
            int curDay = 0;
            //clear line with columns
            scanner.nextLine();
            while (scanner.hasNext() && curDay <= dayNumber) {
                entries[curDay] =  scanner.nextLine();
                curDay++;
            }
            //line is correct day that needs to be parsed
            List<String> vals = CSVUtils.parseLine(entries[dayNumber]);
            //pull relevant data from csv
            int day = Integer.parseInt(vals.get(DAY_COL));
            boolean hasText = Boolean.parseBoolean(vals.get(HAS_TEXT_COL));
            boolean hasAudio = Boolean.parseBoolean(vals.get(HAS_AUDIO_COL));
            String text = vals.get(TEXT_COL);
            String audioFileaname = vals.get(AUDIO_FILENAME_COL);

            return new Day(day, hasText, hasAudio, text, audioFileaname);
        } catch (FileNotFoundException err) {
            Log.e("DayRetriever", "CSV file was not found when trying to be read, " + err.getMessage());
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
