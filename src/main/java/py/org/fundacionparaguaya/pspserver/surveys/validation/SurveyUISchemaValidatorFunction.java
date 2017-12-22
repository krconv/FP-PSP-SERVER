package py.org.fundacionparaguaya.pspserver.surveys.validation;

/**
 * Created by rodrigovillalba on 10/26/17.
 */
public interface SurveyUISchemaValidatorFunction<SurveyUISchema, SnapshotPropertyAttributeEntity, ValidationResult> {
    ValidationResult apply(SurveyUISchema schema, SnapshotPropertyAttributeEntity attribute);
}
