package api;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.Test;

import business.api.Uris;
import business.wrapper.AvailableTime;
import business.wrapper.CourtState;

public class TrainingResourceFunctionalTesting {

    RestService restService = new RestService();

    @Test
    public void testshowAvailability() {
        restService.createCourt("1");
        restService.createCourt("2");
        String token = restService.registerAndLoginPlayer();
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DAY_OF_YEAR, 1);
        day.set(Calendar.HOUR_OF_DAY, 12);
        new RestBuilder<String>(RestService.URL).path(Uris.RESERVES).basicAuth(token, "").body(new AvailableTime(1, day)).post().build();
        day.set(Calendar.HOUR_OF_DAY, 14);
        new RestBuilder<String>(RestService.URL).path(Uris.RESERVES).basicAuth(token, "").body(new AvailableTime(2, day)).post().build();
        String day2 = "" + day.getTimeInMillis();
        String response = new RestBuilder<String>(RestService.URL).path(Uris.RESERVES).path(Uris.AVAILABILITY).basicAuth(token, "")
                .param("day", day2).clazz(String.class).get().build();
        LogManager.getLogger(this.getClass()).info("testshowAvailability (" + response + ")");
    }

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

    @After
    public void deleteAll() {
        new RestService().deleteAll();
    }

}
