package data.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, TestsPersistenceConfig.class})
public class TrainingDaoITest {

    @Autowired
    private DaosService daosService;

    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private ReserveDao reserveDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CourtDao courtDao;

    @Test
    public void testFindAnyTraining() {
        // trainingDao.findOne(1).getCourt()
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
        //Court court = courtDao.findOne(1);
        //assertEquals(1, court.getId());
        /*
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, 1);
        date.set(Calendar.HOUR_OF_DAY, 9);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        assertNotNull(trainingDao.findByUserAndDate(user, date));*/
    }
}
