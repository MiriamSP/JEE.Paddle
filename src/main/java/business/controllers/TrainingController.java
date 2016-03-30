package business.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import data.daos.TrainingDao;
import data.entities.Training;

import business.wrapper.TrainingWrapper;

@Controller
public class TrainingController {

    private TrainingDao trainingDao;

    public TrainingDao getTrainingDao() {
        return trainingDao;
    }

    @Autowired
    public void setTrainingDao(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    public boolean createTraining(int courtId, String trainer, Calendar startDate) {
        return trainingDao.createTraining(courtId, trainer, startDate);
    }
    
    public boolean deleteTraining(int trainingId) {
        return trainingDao.deleteTraining(trainingId);
    }

    public boolean deleteTraining(int courtId,  Calendar startDate) {
        return trainingDao.deleteTraining(courtId, startDate);
    }

    public boolean registerTraining(int courtId, Calendar startDate, String student) {
        return trainingDao.addTrainingPlayer(courtId, startDate, student);
    }

    public boolean deleteTrainingPlayer(int courtId, Calendar startDate, String student) {
        return trainingDao.deleteTrainingPlayer(courtId, startDate, student);
    }

    public boolean isVacancyInTraining(int courtId, Calendar startDate) {
        return trainingDao.isVacancyInTraining(courtId, startDate);
    }

    public List<TrainingWrapper> showTrainings() {
        List<TrainingWrapper> lTrainingWrapper = new ArrayList<>();
        for (Training training : trainingDao.findAll()) {
            lTrainingWrapper.add(new TrainingWrapper(training));
        }
        return lTrainingWrapper;
    }

    public boolean exist(int courtId, Calendar startDate) {
        return trainingDao.existTraining(courtId, startDate);
    }

}
