package business.api.exceptions;

public class MaxStudentsInTrainingException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "El training ya está completo";

    public static final int CODE = 1;

    public MaxStudentsInTrainingException() {
        this("");
    }

    public MaxStudentsInTrainingException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
