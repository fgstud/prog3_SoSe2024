package uhrzeit;
import java.time.Instant;
public class Zeit {
    private final int hours;
    private final int minutes;
    private final int seconds;

    public Zeit(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public Zeit() {
        String dateTime = Instant.now().toString();
        String limiterWithinTime = ":";
        String limiterForTime = "T";
        //2018-01-23T01:23:45.123456Z
        String[] timeSplit = dateTime.split(limiterForTime)[1].split(limiterWithinTime);
        this.hours = Integer.getInteger(timeSplit[0]);
        this.minutes = Integer.getInteger(timeSplit[1]);
        this.seconds = (int) Math.round(Double.parseDouble(timeSplit[2]));
    }

    public void ausgebendeutsch() {
        System.out.println(this.hours + ':' + this.minutes + ':' + this.seconds);
    }

    public void ausgebenEnglisch() {
        int tempHours = this.hours;
        String timeSuffix = " am";
        if (tempHours - 12 > 0) {
            tempHours -= 12;
            timeSuffix = " pm";
        }
        System.out.println(tempHours + ':' + this.minutes + ':' + this.seconds + timeSuffix);
    }

    public int differenz(Zeit t2) {
        int secondsOfT1 = this.getTotalSeconds();
        int secondsOfT2 = t2.getTotalSeconds();
        if (secondsOfT1 > secondsOfT2) {
            return secondsOfT1 - secondsOfT2;
        } else if (secondsOfT2 > secondsOfT1) {
            return secondsOfT2 - secondsOfT1;
        }
        return 0;
    }

    public int getTotalSeconds() {
        return (this.hours * 60 + this.minutes) * 60 + this.seconds;
    }
}
