package py.org.fundacionparaguaya.pspserver.surveys.mapper;

import org.springframework.stereotype.Component;
import py.org.fundacionparaguaya.pspserver.surveys.entities.PropertyAttributeEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.StopLightGroup;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * Created by rodrigovillalba on 10/20/17.
 */
@Component
public class PropertyAttributeSupport {

    private final List<PropertyAttributeEntity> attributeList;

    public PropertyAttributeSupport(List<PropertyAttributeEntity> attributeEntityList) {
        this.attributeList = attributeEntityList;
    }

    public Predicate<String> additional() {
        return (systemName) -> !attributeList.stream()
                .filter(attr -> attr.getPropertySystemName().equals(systemName))
                .findAny()
                .isPresent();
    }

    public Predicate<String> staticEconomic() {
        return (systemName) -> attributeList.stream()
                .filter(attr -> attr.getPropertySystemName().equals(systemName))
                .filter(attr -> attr.getStopLightGroup() == StopLightGroup.ECONOMIC)
                .findFirst()
                .isPresent();
    }

    public Predicate<String> staticIndicator() {
        return (systemName) -> attributeList.stream()
                .filter(attr -> attr.getPropertySystemName().equals(systemName))
                .filter(attr -> attr.getStopLightGroup() == StopLightGroup.INDICATOR)
                .findFirst()
                .isPresent();
    }

    public String propertySchemaToSystemName(String propertySchemaName) {
        return attributeList.stream()
                .filter(attr -> attr.getPropertySchemaName().equals(propertySchemaName))
                .findFirst()
                .orElseGet(PropertyAttributeEntity::new)
                .getPropertySystemName();
    }

    public List<PropertyAttributeEntity> getPropertyAttributesByGroup(StopLightGroup group) {
        return attributeList.stream()
                .filter(attr -> attr.getStopLightGroup() == group)
                .collect(Collectors.toList());
    }

    public List<PropertyAttributeEntity> getPropertyAttributes() {
        return attributeList;
    }
}
