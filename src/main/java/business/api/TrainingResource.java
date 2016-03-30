package business.api;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import business.api.exceptions.AlreadyExistTrainingException;
import business.api.exceptions.InvalidTrainingException;
import business.api.exceptions.NotFoundTrainingIdException;
import business.controllers.TrainingController;
import business.wrapper.AvailableTime;
import business.wrapper.TrainingWrapper;

@RestController
@RequestMapping(Uris.SERVLET_MAP + Uris.TRAININGS)
public class TrainingResource {

    private TrainingController trainingController;

    @Autowired
    public void setTrainingController(TrainingController trainingController) {
        this.trainingController = trainingController;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createTraining(@AuthenticationPrincipal User activeUser, @RequestBody TrainingWrapper trainingWrapper)
            throws InvalidTrainingException, AlreadyExistTrainingException {

        if (this.trainingController.exist(trainingWrapper.getCourtId(), trainingWrapper.getDate())) {
            throw new AlreadyExistTrainingException();
        }

        if (!this.trainingController.createTraining(trainingWrapper.getCourtId(), trainingWrapper.getUsername(),
                trainingWrapper.getDate())) {
            throw new InvalidTrainingException();
        }
    }

    @RequestMapping(value = Uris.ID, method = RequestMethod.DELETE)
    public void deleteTraining(@PathVariable int id) throws NotFoundTrainingIdException {
        if (!trainingController.deleteTraining(id)) {
            throw new NotFoundTrainingIdException("id: " + id);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<TrainingWrapper> showTrainings() {
        return trainingController.showTrainings();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void registerTraining(@RequestParam(required = true) int courtId, Calendar date, String student)
            throws InvalidTrainingException {
        if (!this.trainingController.registerTraining(courtId, date, student)) {
            throw new InvalidTrainingException();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteTrainingPlayer(@RequestParam(required = true) int courtId, Calendar date, String student)
            throws InvalidTrainingException {
        if (!this.trainingController.deleteTrainingPlayer(courtId, date, student)) {
            throw new InvalidTrainingException();
        }
    }

}
