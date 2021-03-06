package data.entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Token {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn
    private User user;

    @Column(unique = false, nullable = false)
    private Calendar dateCreated;

    public Token() {
    }

    public Token(User user) {
        assert user != null;
        this.user = user;
        this.value = new Encrypt()
                .encryptInBase64UrlSafe("" + user.getId() + user.getUsername() + Long.toString(new Date().getTime()) + user.getPassword());
        this.dateCreated = Calendar.getInstance();
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public User getUser() {
        return user;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return id == ((Token) obj).id;
    }

    @Override
    public String toString() {
        return "Token [id=" + id + ", value=" + value + ", userId=" + user.getId() + "]";
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String detailsTokenStatus() {
        String time = new SimpleDateFormat("HH:mm dd-MMM-yyyy ").format(dateCreated.getTime());
        long diffMinutes = 60 - ((Calendar.getInstance().getTimeInMillis() - this.dateCreated.getTimeInMillis()) / (60 * 1000));
        return "Fecha Creación Token: " + time + " - Quedan " + diffMinutes + " minutos de actividad";
    }

    public boolean isTokenExpired() {        
        return (Calendar.getInstance().getTimeInMillis() - this.dateCreated.getTimeInMillis()) > 3600000;       
    }

}
