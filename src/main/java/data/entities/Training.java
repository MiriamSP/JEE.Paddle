package data.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
    private User trainer;

    @ManyToMany(fetch = FetchType.EAGER)
    private ArrayList<User> students;

    private Calendar date;

    public Training(Court court, User user, Calendar date) {
        this.court = court;
        this.trainer = user;
        this.date = date;
        this.students = new ArrayList<User>(4);
    }

    public Training() {
    }

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
        return trainer;
    }

    public void setUser(User user) {
        this.trainer = user;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<User> students) {
        this.students = students;
    }

    public int numStudents() {
        return this.students.size();
    }

    public boolean setStudent(User student) {
        // TODO dejar en entity?
        if (!students.contains(student) && numStudents() < 4) {
            this.students.add(student);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteStudent(User student) {
        // TODO dejar en entity?
        if (!students.contains(student)) {
            return false;
        } else {
            this.students.remove(student);
            return true;
        }
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Training [id=" + id + ", court=" + court + ", user=" + trainer + ", date=" + date + "]";
    }

}
