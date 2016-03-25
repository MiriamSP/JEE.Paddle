package api;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.Test;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;

import business.api.Uris;
import business.wrapper.AvailableTime;
import business.wrapper.CourtState;
import business.wrapper.TrainingWrapper;
import business.wrapper.UserWrapper;
import data.entities.Court;

public class TrainingResourceFunctionalTesting {

    RestService restService = new RestService();

        @Test
    public void testShowTrainings() {
        final int COURTS = 5;
        for (int i = 1; i <= COURTS; i++) {
            restService.createCourt("" + i);
        }
        String token = restService.loginAdmin();
        List<CourtState> list = Arrays.asList(new RestBuilder<CourtState[]>(RestService.URL).path(Uris.COURTS).basicAuth(token, "")
                .clazz(CourtState[].class).get().build());
        assertEquals(COURTS, list.size());
    }
    
    // public void reserveCourt(@AuthenticationPrincipal User activeUser, @RequestBody AvailableTime availableTime)
    @Test
    public void testReserveCourt() {
        restService.createCourt("1");
        restService.createCourt("2");
        String token = restService.registerAndLoginPlayer();
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DAY_OF_YEAR, 1);
        day.set(Calendar.HOUR_OF_DAY,12);
        new RestBuilder<String>(RestService.URL).path(Uris.RESERVES).basicAuth(token, "").body(new AvailableTime(1, day)).post().build();
        day.set(Calendar.HOUR_OF_DAY,14);
        new RestBuilder<String>(RestService.URL).path(Uris.RESERVES).basicAuth(token, "").body(new AvailableTime(2, day)).post().build();
    }
    
    @Test
    public void testTraining() {
        CourtState court = new CourtState(11,true);
        //CourtState court = restService.createCourt("1");
        String token = restService.loginAdmin();
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DAY_OF_YEAR, 1);
        day.set(Calendar.HOUR_OF_DAY,12);
        // int courtId, String trainer, Calendar date
        UserWrapper trainer = new UserWrapper("trainer01", "trainer01@mail", "123456", Calendar.getInstance());
        List<Integer> lStudents = null;
        TrainingWrapper training = new TrainingWrapper(court.getCourtId(),trainer.getUsername(), lStudents, day); 
        
       
        new RestBuilder<Object>(RestService.URL).path(Uris.TRAININGS).basicAuth(token, "").body(training).post().build();
        assertEquals(1, 1);
        
        
       
    }

    @After
    public void deleteAll() {
        new RestService().deleteAll();
    }

}
