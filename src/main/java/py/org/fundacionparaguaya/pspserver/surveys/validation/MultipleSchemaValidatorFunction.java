package py.org.fundacionparaguaya.pspserver.surveys.validation;

/**
 * Created by rodrigovillalba on 10/26/17.
 */
public interface MultipleSchemaValidatorFunction<SurveySchema, String, Object, ValidationResults> {
    ValidationResults apply(SurveySchema schema, String propertyName, Object propertyValue);
}
