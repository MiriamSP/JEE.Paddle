package data.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

import data.entities.Token;
import data.entities.User;

public class TrainingTest {

    @Test
    public void testTraining() {
        User user = new User("trainer", "trainer@gmail.com", "p", Calendar.getInstance());
        Court court = new Court();
        Training training = new Training(court, user, Calendar.getInstance());
        assertTrue(training.getUser().getId() == user.getId());
        assertTrue(training.getCourt().getId() == court.getId());
    }

    @Test
    public void testSetStudent() {
        User user = new User("trainer", "trainer@gmail.com", "p", Calendar.getInstance());
        Court court = new Court();
        Training training = new Training(court, user, Calendar.getInstance());
        training.toString();
        User student = new User("student", "student@gmail.com", "p", Calendar.getInstance());
        assertTrue(training.getUser().getId() == user.getId());
        training.setStudent(student);
        assertEquals(1, training.numStudents());
    }

    @Test
    public void testDeleteStudent() {
        User user = new User("trainer", "trainer@gmail.com", "p", Calendar.getInstance());
        Court court = new Court();
        Training training = new Training(court, user, Calendar.getInstance());
        User student = new User("student", "student@gmail.com", "p", Calendar.getInstance());
        assertTrue(training.getUser().getId() == user.getId());
        training.setStudent(student);
        assertEquals(1, training.numStudents());
        training.deleteStudent(student);
        assertEquals(0, training.numStudents());
    }

}
