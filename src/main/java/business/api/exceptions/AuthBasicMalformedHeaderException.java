package business.api.exceptions;

public class AuthBasicMalformedHeaderException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Error de Authorization en cabecera por formato erroneo, debe estar en Auth Basic";

    public static final int CODE = 666;

    public AuthBasicMalformedHeaderException() {
        this("");
    }

    public AuthBasicMalformedHeaderException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
