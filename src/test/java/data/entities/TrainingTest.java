package data.entities;

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
}
