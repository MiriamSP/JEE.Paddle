package data.entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Training {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn
    private Court court;

    // Trainer
    @ManyToOne
    @JoinColumn
    private User user;

    private List<User> students;

    private Calendar startDate;

    private Calendar finishDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Calendar finishDate) {
        this.finishDate = finishDate;
    }

    @Override
    public String toString() {
        return "Training [id=" + id + ", court=" + court + ", user=" + user + ", students=" + students + ", startDate=" + startDate
                + ", finishDate=" + finishDate + "]";
    }
}
