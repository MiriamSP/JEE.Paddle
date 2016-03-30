package business.wrapper;

import java.util.Calendar;
import java.util.List;

import data.entities.Training;
import data.entities.User;

public class TrainingWrapper {

    private int courtId;

    private String username;

    private List<String> students;

    private Calendar date;

    public TrainingWrapper() {

    }

    public TrainingWrapper(int courtId, String username, List<String> students, Calendar date) {
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
            this.students.add(user.getUsername());
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

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public boolean setOneStudent(String student) {
        if (this.students.size() < 4) {
            students.add(student);
            return true;
        } else {
            return false;
        }
    }      

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

}
