package business.api.exceptions;

public class InvalidTrainingException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Reserva de entrenamiento inválida";

    public static final int CODE = 1;

    public InvalidTrainingException() {
        this("");
    }

    public InvalidTrainingException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
