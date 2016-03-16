package data.daos;

import java.util.Calendar;

public interface TrainingDaoExtended {

    boolean createTraining(int courtId, String trainer, Calendar startDate);
    
    boolean deleteTraining(int trainingId);
    
    boolean addTrainingPlayer(int trainingId, String student);
    
    boolean deleteTrainingPlayer(int trainingId, String student);

}
