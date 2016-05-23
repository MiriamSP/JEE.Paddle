package business.api.exceptions;

public class AlreadyExistTrainingException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Ya existe el entrenamiento";

    public static final int CODE = 1;

    public AlreadyExistTrainingException() {
        this("");
    }

    public AlreadyExistTrainingException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
