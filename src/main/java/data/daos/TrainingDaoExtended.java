package data.daos;

import java.util.Calendar;

public interface TrainingDaoExtended {

    boolean createTraining(int courtId, String trainer, Calendar startDate);
    
    boolean deleteTraining(int courtId,  Calendar startDate);
    
    boolean deleteTraining(int trainingId);
 
    boolean addTrainingPlayer(int courtId, Calendar startDate, String student);
    
    boolean deleteTrainingPlayer(int courtId, Calendar startDate, String student);

    boolean existTraining(int courtId, Calendar startDate);
    
    boolean isVacancyInTraining(int courtId, Calendar startDate);
}
