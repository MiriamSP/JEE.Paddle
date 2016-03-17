package data.daos;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import data.entities.Court;
import data.entities.Reserve;
import data.entities.Training;
import data.entities.User;

public class TrainingDaoImpl implements TrainingDaoExtended {

    @Autowired
    private TrainingDao trainingDao;

    @Autowired
    private ReserveDao reserveDao;

    @Autowired
    private CourtDao courtDao;

    @Autowired
    private UserDao userDao;

    @Override
    public boolean createTraining(int courtId, String trainer, Calendar startDate) {
        // TODO Auto-generated method stub
        Court court = courtDao.findOne(courtId);
        User userTrainer = userDao.findByUsernameOrEmail(trainer);
        if ((court != null) && (userTrainer != null)) {
            Training training = new Training(court, userTrainer, startDate);
            trainingDao.save(training);
            Reserve reserve = reserveDao.findByCourtAndDate(court, startDate);
            // anulación de la reserva si existe
            if (reserve != null) {
                // TODO MIRAR save DELETE
                reserveDao.delete(reserve);
                reserveDao.flush();
            }
            return true;
        } else
            return false;
    }

    @Override
    public boolean deleteTraining(int trainingId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteTraining(int courtId, Calendar startDate) {
        // TODO Auto-generated method stub
        Court court = courtDao.findOne(courtId);
        Training training = trainingDao.findByCourtAndDate(court, startDate);
        if (training != null) {
            trainingDao.delete(training);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addTrainingPlayer(int courtId, Calendar startDate, String student) {
        // TODO Auto-generated method stub
        Court court = courtDao.findOne(courtId);
        Training training = trainingDao.findByCourtAndDate(court, startDate);
        if (training != null) {
            User user = userDao.findByUsernameOrEmail(student);
            if (user != null) {
                training.setStudent(user);
                System.out.println("aqui: "+ student + " - num student: " + training.numStudents() + " - students: "+ user.toString());
                trainingDao.saveAndFlush(training);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteTrainingPlayer(int courtId, Calendar startDate, String student) {
        // TODO Auto-generated method stub
        Court court = courtDao.findOne(courtId);
        Training training = trainingDao.findByCourtAndDate(court, startDate);
        if (training != null) {
            User user = userDao.findByUsernameOrEmail(student);
            if (user != null) {
                training.deleteStudent(user);
                System.out.println("aqui: "+ student + " - num student: " + training.numStudents() + " - students: "+ user.toString());
                trainingDao.saveAndFlush(training);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
