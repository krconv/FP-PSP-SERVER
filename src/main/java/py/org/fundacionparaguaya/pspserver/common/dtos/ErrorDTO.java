package py.org.fundacionparaguaya.pspserver.common.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.*;



/**
 * View Model for transferring developerMessage message with a list of field errors.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long errorId;

    private String developerMessage;

    private String message;

    private List<FieldErrorDTO> fieldErrors;


    public static ErrorDTO of(String message, String developerMessage) {
        return new ErrorDTO(ErrorIdGenerator.getNewId(), message, developerMessage);
    }

    public static ErrorDTO of(String message) {
        return ErrorDTO.of(message, null);
    }

    public static ErrorDTO of(List<FieldError> fieldErrors, String message, String developerMessage) {
        ErrorDTO dto = ErrorDTO.of(message, developerMessage);
        for (FieldError fieldError : fieldErrors) {
            dto.add(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode());
        }
        return dto;
    }

    private ErrorDTO(Long errorId, String message, String developerMessage) {
        this.errorId = errorId;
        this.developerMessage = developerMessage;
        this.message = message;
    }

    public void add(String objectName, String field, Collection<String> messages) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorDTO(objectName, field, messages));
    }

    public void add(String objectName, String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorDTO(objectName, field, Arrays.asList(message)));
    }

    public String getMessage() {
        return message;
    }


    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public Long getErrorId() {
        return errorId;
    }

    public void addFieldErrors(List<FieldErrorDTO> fieldErrors) {
        if (this.fieldErrors == null) {
            this.fieldErrors = new ArrayList<>();
        }
        this.fieldErrors.addAll(fieldErrors);
    }


    public ErrorDTO formatWithCode() {
        String formattedMessge = ErrorFormatter.formatErrorMessage(this.getMessage(), this.getErrorId());
        return new ErrorDTO(this.getErrorId(), formattedMessge, this.getDeveloperMessage());
    }



    /**
     * Created by rodrigovillalba on 12/12/17.
     */
    public static class ErrorIdGenerator {
        /**
         * Private constructor in order to prevent to instantiate this class.
         */
        private ErrorIdGenerator() {
        }

        public static long getNewId() {
            return new Date().getTime();
        }

    }
}
