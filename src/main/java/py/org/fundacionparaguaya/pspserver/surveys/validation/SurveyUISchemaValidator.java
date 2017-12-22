package py.org.fundacionparaguaya.pspserver.surveys.validation;

import py.org.fundacionparaguaya.pspserver.surveys.entities.PropertyAttributeEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.StopLightGroup;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SurveyUISchema;

/**
 * Created by rodrigovillalba on 10/26/17.
 */
public interface SurveyUISchemaValidator extends SurveyUISchemaValidatorFunction<SurveyUISchema, PropertyAttributeEntity, ValidationResult> {


    static SurveyUISchemaValidator presentInGroup() {
        return (SurveyUISchema schema, PropertyAttributeEntity attr) -> {
            if (attr.getStopLightGroup() == StopLightGroup.ECONOMIC) {
                return validateInEconomics(schema, attr);
            }
            if (attr.getStopLightGroup() == StopLightGroup.INDICATOR) {
                return validateInIndicators(schema, attr);
            }
            return ValidationResult.valid();
        };
    }

    static ValidationResult validateInEconomics(SurveyUISchema schema, PropertyAttributeEntity attr) {
        
        if (schema.getGroupEconomics()==null) {
            return ValidationResult.invalid("Group Economic don't exists");
        } else if (!schema.getGroupEconomics().contains(attr.getPropertySchemaName())) {
            return ValidationResult.invalid(attr.getPropertySchemaName(), "Property '" + attr.getPropertySystemName() + "' should be in '" + SurveyUISchema.UI_GROUP_ECONOMICS + "' in 'survey_ui_schema'");
        } 
        
        return ValidationResult.valid();
    }

    static ValidationResult validateInIndicators(SurveyUISchema schema, PropertyAttributeEntity attr) {
        
        if (schema.getGroupIndicators()==null) {
            return ValidationResult.invalid("Group Indicators don't exists");
        } else if (!schema.getGroupIndicators().contains(attr.getPropertySchemaName())) {
            return ValidationResult.invalid(attr.getPropertySchemaName(), "Property '" + attr.getPropertySystemName() + "' should be in '" + SurveyUISchema.UI_GROUP_INDICTATORS + "' in 'survey_ui_schema'");

        } 
        return ValidationResult.valid();
    }
}
