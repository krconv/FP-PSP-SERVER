package py.org.fundacionparaguaya.pspserver.web.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import py.org.fundacionparaguaya.pspserver.common.constants.ErrorCodes;
import py.org.fundacionparaguaya.pspserver.common.dtos.ErrorDTO;
import py.org.fundacionparaguaya.pspserver.common.dtos.FieldErrorDTO;
import py.org.fundacionparaguaya.pspserver.common.exceptions.CustomParameterizedException;

import java.util.List;
/**
 * This controller advice is inspired by JHipster Example app:
 * https://github.com/jhipster/jhipster-sample-app
 * And by:
 * https://github.com/eugenp/tutorials/blob/035ea531b6c755955f7c2d2d2d191d400baca4fd/spring-security-rest/src/main/java/org/baeldung/web/CustomRestExceptionHandler.java
 * <p>
 *     Controller advice to translate the server side exceptions to client-friendly json structures.
 * </p>
 */
@ControllerAdvice
public class ExceptionTranslatorAdvice  extends AbstractBaseExceptionHandler {

    private final Logger LOG = LoggerFactory.getLogger(ExceptionTranslatorAdvice.class);


    /**
     * <p>
     *   Errors thrown in Controllers methods with arguments annotated with JAX-RS @Valid.
     * </p>
     *
     * @param ex
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        ErrorDTO dto = ErrorDTO.of(fieldErrors, ErrorCodes.VALIDATION_FAILURE.getMessage(), ex.getLocalizedMessage());
        return generateResponseEntity(dto, HttpStatus.BAD_REQUEST);
    }

    /**
     * <p>
     *   Errors thrown in service layers validations, using standard exceptions.
     * </p>
     * <p>
     *   We favor the use of standard exceptions as recommended in:
     *   https://github.com/tatsuyaoiw/effective-java/blob/master/chapter-9.md#item-60-favor-the-use-of-standard-exceptions
     * </p>
     * @param ex
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleBusinessValidationError(IllegalArgumentException ex) {
        LOG.debug(ex.getMessage(), ex);
        ErrorDTO dto = ErrorDTO.of(ex.getLocalizedMessage());
        return generateResponseEntity(dto, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CustomParameterizedException.class)
    public ResponseEntity<Object> handleCustomParameterizedValidationError(CustomParameterizedException ex) {
        List<FieldErrorDTO> fieldErrors = ex.getFieldErrors();
        ErrorDTO dto = ErrorDTO.of(ex.getLocalizedMessage());
        dto.addFieldErrors(fieldErrors);
        return generateResponseEntity(dto, HttpStatus.BAD_REQUEST);
    }


    /**
     * <p>
     *     Any other unhandled exceptions are processed here.
     * </p>
     * <p>
     *     We also process user defined (non standard) exceptions annotated with @ResponseStatus.
     *     See {@link py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException}
     * </p>
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleGeneralUncaughtExcption(Exception ex) {
        HttpStatus status;
        ErrorDTO errorDto;
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            status = responseStatus.value();
            errorDto = ErrorDTO.of(responseStatus.reason(), ex.getLocalizedMessage()).formatWithCode();
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            errorDto = ErrorDTO.of(ErrorCodes.INTERNAL_SERVER_ERROR.getMessage(), ex.getLocalizedMessage()).formatWithCode();
        }

        LOG.error("Error id: {}", errorDto.getErrorId());
        LOG.error(ex.getMessage(), ex);

        return generateResponseEntity(errorDto, status);
    }
}
