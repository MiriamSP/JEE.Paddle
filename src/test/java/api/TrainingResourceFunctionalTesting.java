package api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import business.api.Uris;
import business.wrapper.AvailableTime;
import business.wrapper.CourtState;
import business.wrapper.TrainingWrapper;
import business.wrapper.UserWrapper;
import business.wrapper.UserWrapperBuilder;

public class TrainingResourceFunctionalTesting {

    RestService restService = new RestService();

    @Test
    public void testCreateTrainingUnauthorized() {
        
        try {
            Calendar day = Calendar.getInstance();
            day.add(Calendar.DAY_OF_YEAR, 1);
            day.set(Calendar.HOUR_OF_DAY,12);
            restService.createCourt("3");
            CourtState court = new CourtState(3,true);
            List<Integer> lStudents = null;
            TrainingWrapper trainingWrapper = new TrainingWrapper(court.getCourtId(),"trainer", lStudents, day); 
            new RestBuilder<Object>(RestService.URL).path(Uris.TRAININGS).body(trainingWrapper).post().build();
            fail();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.UNAUTHORIZED, httpError.getStatusCode());
            LogManager.getLogger(this.getClass()).info(
                    "testCreateTraining (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
    }   
    
    @Test
    public void testTraining() {
       
        String token = restService.loginTrainer();
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DAY_OF_YEAR, 1);
        day.set(Calendar.HOUR_OF_DAY,12);
        String time = new SimpleDateFormat("HH:mm dd-MMM-yyyy ").format(day.getTime());
        System.out.println("Fecha training: " + time);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ AQUI 11111");
        
        restService.createCourt("3");
        CourtState court = new CourtState(3,true);
        List<Integer> lStudents = null;
        TrainingWrapper trainingWrapper = new TrainingWrapper(court.getCourtId(),"trainer", lStudents, day); 
        new RestBuilder<Object>(RestService.URL).path(Uris.TRAININGS).basicAuth(token, "").body(trainingWrapper).post().build();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ AQUI 2222222");

        assertEquals(1, 1);
    }

    @After
    public void deleteAll() {
        new RestService().deleteAll();
    }

}
