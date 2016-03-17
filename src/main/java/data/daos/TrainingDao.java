package data.daos;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import data.entities.Court;
import data.entities.User;
import data.entities.Training;

public interface TrainingDao extends JpaRepository<Training, Integer> , TrainingDaoExtended {

    Training findByCourtAndDate(Court court, Calendar date);

    Training findByUser(User user);

    Training findByUserAndDate(User user, Calendar date);

    Training findByCourtAndUser(Court court, User user);
    
    List<Training> findByDateBetween(Calendar date1, Calendar date2);

}
