package py.org.fundacionparaguaya.pspserver.common.exceptions;

/**
 * Created by rodrigovillalba on 10/4/17.
 */
public class NotFoundException extends ApiException {
    private int code;
    public NotFoundException (int code, String msg) {
        super(code, msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}