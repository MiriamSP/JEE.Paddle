package business.wrapper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimetableTime {

    private int courtId;
    
    private String trainerId;

    private Calendar time;

    public TimetableTime() {
    }

    public TimetableTime(int courtId, Calendar time) {
        this.courtId = courtId;
        this.time = time;
    }
    
    public TimetableTime(int courtId, String trainerId, Calendar time) {
        this.courtId = courtId;
        this.trainerId = trainerId;
        this.time = time;
    }

    public int getCourtId() {
        return courtId;
    }

    public void setCourtId(int courtId) {
        this.courtId = courtId;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

        
    @Override
    public String toString() {
        String timeString = new SimpleDateFormat("HH:00").format(time.getTime());
        return "Lesson Time [courtId=" + courtId + ", trainerId=" + trainerId + ", time=" + timeString + "]";
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

}
