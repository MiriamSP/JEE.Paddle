package business.api.exceptions;

public class InvalidTokenException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Expired token";

    public static final int CODE = 1;

    public InvalidTokenException() {
        this("");
    }

    public InvalidTokenException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
