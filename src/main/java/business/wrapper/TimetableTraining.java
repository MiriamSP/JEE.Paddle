package business.wrapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class TimetableTraining {

    private Calendar day;

    private List<TimetableTime> times;

    public TimetableTraining() {
    }

    public TimetableTraining(Calendar day, List<TimetableTime> times) {
        this.day = day;
        this.times = times;
    }

    public TimetableTraining(Calendar day, Map<Integer, List<Integer>> allTimes) {
        this.day = day;
        List<TimetableTime> times = new ArrayList<>();
        for (Integer court : allTimes.keySet()) {
            for (Integer hour : allTimes.get(court)) {
                Calendar hourCalendar = (Calendar) day.clone();
                hourCalendar.set(Calendar.HOUR_OF_DAY, hour);
                times.add(new TimetableTime(court, hourCalendar));
            }
        }
        this.times = times;
    }

    public Calendar getDay() {
        return day;
    }

    public void setDay(Calendar day) {
        this.day = day;
    }

    public List<TimetableTime> getTimes() {
        return times;
    }

    public void setTimes(List<TimetableTime> times) {
        this.times = times;
    }

    @Override
    public String toString() {
        String dayString = new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(day.getTime());
        return "Timetable Training [day=" + dayString + ", times=" + times + "]";
    }

}
