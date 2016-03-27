package data.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.PersistenceConfig;
import config.TestsPersistenceConfig;
import data.entities.Court;
import data.entities.User;
import data.entities.Training;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class TrainingDaoITest {

    @Autowired
    private DaosService daosService;

    @Autowired
    private TrainingDao trainingDao;
    
    @Autowired
    private UserDao userDao;

    @Autowired
    private CourtDao courtDao;

    @Test
    public void testFindAnyTraining() {
        assertNotNull(trainingDao.findOne(1));
    }

    @Test
    public void testFindByUser() {
        User user = userDao.findByUsernameOrEmail("u0");
        assertEquals("u0", user.getUsername());
        assertNotNull(trainingDao.findByUser(user));
    }

    @Test
    public void testFindByUserAndDate() {
        User user = userDao.findByUsernameOrEmail("u0");
        assertEquals("u0", user.getUsername());
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, 1);
        date.set(Calendar.HOUR_OF_DAY, 9);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        assertNotNull(trainingDao.findByUserAndDate(user, date));
    }

    @Test
    public void testFindByCourtAndDate() {
        Court court = courtDao.findOne(1);
        assertEquals(1, court.getId());
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, 1);
        date.set(Calendar.HOUR_OF_DAY, 9);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        assertNotNull(trainingDao.findByCourtAndDate(court, date));
    }

    @Test
    public void testDeleteTraining() {
        assertEquals(2, trainingDao.count());
        Court court = courtDao.findOne(2);
        assertEquals(2, court.getId());
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, 1);
        date.set(Calendar.HOUR_OF_DAY, 9);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        trainingDao.deleteTraining(court.getId(), date);
        trainingDao.flush();
        assertEquals(1, trainingDao.count());
    }
    
    @Test
    public void testAddTrainingPlayer(){
        Court court = courtDao.findOne(1);
        assertEquals(1, court.getId());
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, 1);
        date.set(Calendar.HOUR_OF_DAY, 9);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        String student = "u0";
        int courtId = court.getId();
        trainingDao.addTrainingPlayer(courtId, date, student);
        Training training = trainingDao.findByCourtAndDate(court, date);
        assertEquals(1, training.numStudents());
    }
    
    @Test
    public void testDeleteTrainingPlayer(){
        Court court = courtDao.findOne(1);
        assertEquals(1, court.getId());
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, 1);
        date.set(Calendar.HOUR_OF_DAY, 9);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        Training training = trainingDao.findByCourtAndDate(court, date);
        String student = "u0";
        int courtId = court.getId();
        trainingDao.deleteTrainingPlayer(courtId, date, student);
        training = trainingDao.findByCourtAndDate(court, date);
        assertEquals(0, training.numStudents());
    }
}
