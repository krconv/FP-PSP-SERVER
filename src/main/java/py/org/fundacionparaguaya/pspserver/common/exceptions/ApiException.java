package py.org.fundacionparaguaya.pspserver.common.exceptions;

/**
 * Created by rodrigovillalba on 10/4/17.
 */
public class ApiException extends Exception{

    private int code;

    public ApiException (int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}