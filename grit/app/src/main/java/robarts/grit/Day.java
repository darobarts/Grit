package robarts.grit;

/**
 * Created by austinrobarts on 7/6/17.
 */

public class Day {
    public int number;
    public boolean hasText;
    public boolean hasAudio;
    public String text;
    public String audioFilename;

    public Day(int number, boolean hasText, boolean hasAudio, String text, String audioFilename) {
        this.number = number;
        this.hasText = hasText;
        this.hasAudio = hasAudio;
        this.text = text;
        this.audioFilename = audioFilename;
    }
}
