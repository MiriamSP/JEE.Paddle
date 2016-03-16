package data.entities;

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
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> students;

    private Calendar date;

    public Training(Court court, User user, Calendar date) {
        this.court = court;
        this.user = user;
        this.date = date;
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
        return "Training [id=" + id + ", court=" + court + ", user=" + user + ", date=" + date + "]";
    }

      
}
