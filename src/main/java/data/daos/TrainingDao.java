package data.daos;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import data.entities.Court;
import data.entities.Training;

public interface TrainingDao extends JpaRepository<Training, Integer> {
    
    
}
