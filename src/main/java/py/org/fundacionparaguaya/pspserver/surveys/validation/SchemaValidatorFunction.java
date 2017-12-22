package py.org.fundacionparaguaya.pspserver.surveys.validation;

/**
 * Created by rodrigovillalba on 10/26/17.
 */
public interface SchemaValidatorFunction<SurveyDefinition, String, Object, ValidationResult> {
    ValidationResult apply(SurveyDefinition schema, String propertyName, Object propertyValue);
}
