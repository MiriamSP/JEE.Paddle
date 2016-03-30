package api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import business.api.Uris;
import business.wrapper.CourtState;
import business.wrapper.TrainingWrapper;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TrainingResourceFunctionalTesting {

    RestService restService = new RestService();

    @Test
    public void testCreateTrainingUnauthorized() {
        try {
            Calendar day = Calendar.getInstance();
            day.add(Calendar.DAY_OF_YEAR, 1);
            day.set(Calendar.HOUR_OF_DAY, 12);
            restService.createCourt("3");
            CourtState court = new CourtState(3, true);
            List<String> lStudents = null;
            TrainingWrapper trainingWrapper = new TrainingWrapper(court.getCourtId(), "trainer", lStudents, day);
            new RestBuilder<Object>(RestService.URL).path(Uris.TRAININGS).body(trainingWrapper).post().build();
            fail();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.UNAUTHORIZED, httpError.getStatusCode());
            LogManager.getLogger(this.getClass())
                    .info("testCreateTraining (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
    }

    @Test
    public void testCreateTraining() {
        String token = restService.loginTrainer();
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DAY_OF_YEAR, 1);
        day.set(Calendar.HOUR_OF_DAY, 12);
        restService.createCourt("3");
        CourtState court = new CourtState(3, true);
        List<String> lStudents = null;
        TrainingWrapper trainingWrapper = new TrainingWrapper(court.getCourtId(), "trainer", lStudents, day);
        new RestBuilder<Object>(RestService.URL).path(Uris.TRAININGS).basicAuth(token, "").body(trainingWrapper).post().build();
    }

    @Test
    public void testShowTraining() {
        String token = restService.loginTrainer();
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DAY_OF_YEAR, 1);
        day.set(Calendar.HOUR_OF_DAY, 12);
        restService.createCourt("3");
        CourtState court = new CourtState(3, true);
        List<String> lStudents = null;
        TrainingWrapper trainingWrapper = new TrainingWrapper(court.getCourtId(), "trainer", lStudents, day);
        new RestBuilder<Object>(RestService.URL).path(Uris.TRAININGS).basicAuth(token, "").body(trainingWrapper).post().build();
        token = restService.registerAndLoginPlayer();
        String response = new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).path(Uris.SHOW).basicAuth(token, "")
                .body(trainingWrapper).clazz(String.class).get().build();
        LogManager.getLogger(this.getClass()).info("testshowTraining (" + response + ")");
    }

    @Test
    public void testRegisterTraining() {
        String token = restService.loginTrainer();
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DAY_OF_YEAR, 1);
        day.set(Calendar.HOUR_OF_DAY, 12);
        restService.createCourt("3");
        CourtState court = new CourtState(3, true);
        List<String> lStudents = new ArrayList<String>(4);
        String student = "u0";
        lStudents.add(student);
        TrainingWrapper trainingWrapper = new TrainingWrapper(court.getCourtId(), "trainer", lStudents, day);
        new RestBuilder<Object>(RestService.URL).path(Uris.TRAININGS).basicAuth(token, "").body(trainingWrapper).post().build();
        token = restService.registerAndLoginPlayer();
        new RestBuilder<Object>(RestService.URL).path(Uris.TRAININGS).path(Uris.REGISTER).basicAuth(token, "").body(trainingWrapper).put()
                .build();
    }

    @After
    public void deleteAll() {
        new RestService().deleteAll();
    }

}
