package business.wrapper;

import java.util.Calendar;
import java.util.List;

import data.entities.Court;
import data.entities.Training;
import data.entities.User;

public class TrainingWrapper {

    private Court court;

    private User user;

    private List<User> students;

    private Calendar date;

    public TrainingWrapper() {

    }

    public TrainingWrapper(Court court, User user, List<User> students, Calendar date) {
        super();
        this.setCourt(court);
        this.setUser(user);
        this.setStudents(students);
        this.setDate(date);
    }
    
    public TrainingWrapper(Training training) {
        this.setCourt(training.getCourt());
        this.setUser(training.getUser());
        this.setStudents(training.getStudents());
        this.setDate(training.getDate());
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TrainingWrapper [court=" + court + ", user=" + user + ", students=" + students + ", date=" + date + "]";
    }

}
