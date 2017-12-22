package py.org.fundacionparaguaya.pspserver.surveys.validation;

/**
 * Created by rodrigovillalba on 10/26/17.
 */
public interface PropertyValidatorFunction<Property, String, Object, ValidationResult> {
    ValidationResult apply(Property property, String propertyName, Object propertyValue);
}
