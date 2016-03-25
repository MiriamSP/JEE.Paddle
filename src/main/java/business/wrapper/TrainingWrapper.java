package business.wrapper;

import java.util.Calendar;
import java.util.List;

import data.entities.Court;
import data.entities.Training;
import data.entities.User;

public class TrainingWrapper {

    private int courtId;

    private String username;

    private List<Integer> students;

    private Calendar date;

    public TrainingWrapper() {

    }
    
    

    public TrainingWrapper(int courtId, String username, List<Integer> students, Calendar date) {
        super();
        this.courtId = courtId;
        this.username = username;
        this.students = students;
        this.date = date;
    }



    public TrainingWrapper(Training training) {
        this.setCourtId(training.getCourt().getId());
        this.setUsername(training.getUser().getUsername());
        for (User user : training.getStudents()) {
            this.students.add(user.getId());
        }
        this.date = training.getDate();
    }

    public int getCourtId() {
        return courtId;
    }

    public void setCourtId(int courtId) {
        this.courtId = courtId;
    }

    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getStudents() {
        return students;
    }

    public void setStudents(List<Integer> students) {
        this.students = students;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

}
