package business.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import data.daos.CourtDao;
import data.daos.ReserveDao;
import data.daos.TrainingDao;
import data.daos.UserDao;
import data.entities.Court;
import data.entities.Reserve;
import data.entities.Training;
import data.entities.User;
import business.wrapper.Availability;
import business.wrapper.TimetableTraining;

@Controller
public class TrainingController {
    // TODO (COPY RESERVE)

    private static final int START_TIME = 9;

    private static final int END_TIME = 23;

    private TrainingDao trainingDao;

    private ReserveDao reserveDao;

    private CourtDao courtDao;

    private UserDao userDao;

    public TrainingDao getTrainingDao() {
        return trainingDao;
    }

    @Autowired
    public void setTrainingDao(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    @Autowired
    public void setReserveDao(ReserveDao reserveDao) {
        this.reserveDao = reserveDao;
    }

    @Autowired
    public void setCourtDao(CourtDao courtDao) {
        this.courtDao = courtDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean createTraining(int courtId, String trainer, Calendar startDate) {
        // TODO DAO
        return trainingDao.createTraining(courtId, trainer, startDate);
    }

    public boolean deleteTraining(int trainingId) {
        // TODO DAO
        return trainingDao.deleteTraining(trainingId);
    }

    public boolean addTrainingPlayer(int courtId, Calendar startDate, String student) {
        // TODO DAO
        return trainingDao.addTrainingPlayer(courtId, startDate, student);
    }

    public boolean deleteTrainingPlayer(int courtId, Calendar startDate, String student) {
        // TODO DAO
        return trainingDao.deleteTrainingPlayer(courtId, startDate, student);
    }

    public  void showTrainingsPrint() {
        // TODO DAO
        List<Training> lTraining = trainingDao.findAll();
        for (Training training : lTraining) {
            System.out.print("COURT: " + training.getCourt().getId());
            System.out.print("- TRAINER: " + training.getUser().getUsername());
            System.out.print("- DATE: " + training.getDate());
            System.out.println("- NUM STUDENTS: " + training.numStudents());
        }

        
    }
    
    public TimetableTraining showTrainings(Calendar calendarDay) {
        Calendar endDay = (Calendar) calendarDay.clone();
        endDay.add(Calendar.DAY_OF_MONTH, 1);
        List<Court> courtList = courtDao.findAll();
        Map<Integer, List<Integer>> allTimes = new HashMap<>();

        int initialHour = START_TIME;
        if (Calendar.getInstance().get(Calendar.YEAR) == calendarDay.get(Calendar.YEAR)
                && Calendar.getInstance().get(Calendar.DAY_OF_YEAR) == calendarDay.get(Calendar.DAY_OF_YEAR)) {
            initialHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        }
        for (Court court : courtList) {
            List<Integer> hourList = new ArrayList<>();
            for (int hour = initialHour; hour <= END_TIME; hour++) {
                hourList.add(hour);
            }
            allTimes.put(court.getId(), hourList);
        }
        List<Training> lTraining = trainingDao.findByDateBetween(calendarDay, endDay);
        for (Training training : lTraining) {
            allTimes.get(training.getCourt().getId()).remove(new Integer(training.getDate().get(Calendar.HOUR_OF_DAY)));
        }
        return new TimetableTraining(calendarDay, allTimes);
    }
    
    public Availability showCourtAvailability(Calendar calendarDay) {
        Calendar endDay = (Calendar) calendarDay.clone();
        endDay.add(Calendar.DAY_OF_MONTH, 1);
        List<Court> courtList = courtDao.findAll();
        Map<Integer, List<Integer>> allTimesAvailable = new HashMap<>();

        int initialHour = START_TIME;
        if (Calendar.getInstance().get(Calendar.YEAR) == calendarDay.get(Calendar.YEAR)
                && Calendar.getInstance().get(Calendar.DAY_OF_YEAR) == calendarDay.get(Calendar.DAY_OF_YEAR)) {
            initialHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        }
        for (Court court : courtList) {
            List<Integer> hourList = new ArrayList<>();
            for (int hour = initialHour; hour <= END_TIME; hour++) {
                hourList.add(hour);
            }
            allTimesAvailable.put(court.getId(), hourList);
        }
        List<Reserve> reserveList = reserveDao.findByDateBetween(calendarDay, endDay);
        for (Reserve reserve : reserveList) {
            allTimesAvailable.get(reserve.getCourt().getId()).remove(new Integer(reserve.getDate().get(Calendar.HOUR_OF_DAY)));
        }
        return new Availability(calendarDay, allTimesAvailable);
    }

    public boolean reserveCourt(int courtId, Calendar date, String username) {
        Reserve reserve = new Reserve(courtDao.findOne(courtId), date);
        if (reserveDao.findByCourtAndDate(reserve.getCourt(), reserve.getDate()) != null) {
            return false;
        }
        reserve.setUser(userDao.findByUsernameOrEmail(username));
        reserveDao.save(reserve);
        return true;
    }

    public boolean rightTime(int hour) {
        return hour >= START_TIME && hour <= END_TIME;
    }

}
