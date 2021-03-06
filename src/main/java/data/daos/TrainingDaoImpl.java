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

    private void checkReserve(Court court, Calendar date) {
        Reserve reserve = reserveDao.findByCourtAndDate(court, date);
        // si coincide reserva ==> anulación
        if (reserve != null) {
            reserveDao.delete(reserve);
            reserveDao.flush();
        }
    }

    @Override
    public boolean createTraining(int courtId, String trainer, Calendar startDate) {
        Court court = courtDao.findOne(courtId);
        User userTrainer = userDao.findByUsernameOrEmail(trainer);
        if ((court != null) && (userTrainer != null)) {
            if (trainingDao.findByCourtAndDate(court, startDate) == null) {
                Training training = new Training(court, userTrainer, startDate);
                trainingDao.saveAndFlush(training);
                checkReserve(court, startDate);
                return true;
            } else {
                return false;
            }
        } else
            return false;
    }

    @Override
    public boolean deleteTraining(int courtId, Calendar startDate) {
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
    public boolean deleteTraining(int trainingId) {
        Training training = trainingDao.findOne(trainingId);
        if (training != null) {
            trainingDao.delete(training);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addTrainingPlayer(int courtId, Calendar startDate, String student) {
        Court court = courtDao.findOne(courtId);
        Training training = trainingDao.findByCourtAndDate(court, startDate);
        if (training != null) {
            User user = userDao.findByUsernameOrEmail(student);
            if (user != null) {
                training.setStudent(user);
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
        Court court = courtDao.findOne(courtId);
        Training training = trainingDao.findByCourtAndDate(court, startDate);
        if (training != null) {
            User user = userDao.findByUsernameOrEmail(student);
            if (user != null) {
                training.deleteStudent(user);
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
    public boolean existTraining(int courtId, Calendar startDate) {
        Court court = courtDao.findOne(courtId);
        return trainingDao.findByCourtAndDate(court, startDate) != null;
    }

    @Override
    public boolean isVacancyInTraining(int courtId, Calendar startDate) {
        Court court = courtDao.findOne(courtId);
        return trainingDao.findByCourtAndDate(court, startDate).numStudents() < 4;
    }

}
