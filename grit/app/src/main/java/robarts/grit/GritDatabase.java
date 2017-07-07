package robarts.grit;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by austinrobarts on 7/6/17.
 */

public class GritDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "grit";
    public static final String TABLE_NAME = "day";
    public static final String DAY_COLUMN_NUMBER = "number";
    public static final String DAY_COLUMN_TEXT = "text";
    public static final String DAY_COLUMN_HASTEXT = "hasText";
    public static final String DAY_COLUMN_HASAUDIO = "hasAudio";
    public static final String DAY_COLUMN_AUDIO_FILENAME = "audioFilename";

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     */
    public GritDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +  TABLE_NAME + " (" +
            DAY_COLUMN_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DAY_COLUMN_HASTEXT + " BOOLEAN, " +
            DAY_COLUMN_HASAUDIO + " BOOLEAN, " +
            DAY_COLUMN_TEXT + " TEXT, " +
            DAY_COLUMN_AUDIO_FILENAME + " TEXT)");
        //add 2 testing table entries
        db.execSQL(makeInsertStatement(0, true, false, DatabaseUtils.sqlEscapeString("List 17 pretty ladies"), DatabaseUtils.sqlEscapeString("")));
        db.execSQL(makeInsertStatement(1, true, false, DatabaseUtils.sqlEscapeString("List 20 pretty ladies"), DatabaseUtils.sqlEscapeString("")));
    }

    private String makeInsertStatement(int number, boolean hasText, boolean hasAudio, String text, String audioFilename) {
        return "INSERT INTO " + TABLE_NAME + " (" +
                DAY_COLUMN_NUMBER + "," + DAY_COLUMN_HASTEXT + "," + DAY_COLUMN_HASAUDIO + "," + DAY_COLUMN_TEXT + "," + DAY_COLUMN_AUDIO_FILENAME +") " +
                "VALUES (" + number +"," + 1+","+0+","+text+","+audioFilename+")";
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Day getDay(int dayNumber) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{DAY_COLUMN_NUMBER, DAY_COLUMN_HASTEXT, DAY_COLUMN_HASAUDIO, DAY_COLUMN_TEXT, DAY_COLUMN_AUDIO_FILENAME}, DAY_COLUMN_NUMBER + " = " + dayNumber, null, null, null, null, null);
        Day day = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int number = cursor.getInt(cursor.getColumnIndex(DAY_COLUMN_NUMBER));
            String text = cursor.getString(cursor.getColumnIndex(DAY_COLUMN_TEXT));
            String audioFilename = cursor.getString(cursor.getColumnIndex(DAY_COLUMN_AUDIO_FILENAME));
            boolean hasText = cursor.getInt(cursor.getColumnIndex(DAY_COLUMN_HASTEXT)) == 1;
            boolean hasAudio = cursor.getInt(cursor.getColumnIndex(DAY_COLUMN_HASAUDIO)) == 1;
            day = new Day(number, hasText, hasAudio, text, audioFilename);
        }
        return day;
    }
}
