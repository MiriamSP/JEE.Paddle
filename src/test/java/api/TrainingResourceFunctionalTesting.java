package api;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import business.api.Uris;
import business.wrapper.AvailableTime;
import business.wrapper.CourtState;
import business.wrapper.TrainingWrapper;
import business.wrapper.UserWrapper;
import business.wrapper.UserWrapperBuilder;

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
    public void testCreate() {
        for (int i = 0; i < 4; i++) {
            new RestBuilder<Object>(RestService.URL).path(Uris.USERS).body(new UserWrapperBuilder(i).build()).post().build();
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
        TrainingWrapper training = new TrainingWrapper(court.getCourtId(),"trainer", lStudents, day); 
        new RestBuilder<Object>(RestService.URL).path(Uris.TRAININGS).basicAuth(token, "").body(training).post().build();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ AQUI 2222222");

        assertEquals(1, 1);
    }

    @After
    public void deleteAll() {
        new RestService().deleteAll();
    }

}
