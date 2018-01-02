package py.org.fundacionparaguaya.pspserver.families.entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import py.org.fundacionparaguaya.pspserver.network.entities.ApplicationEntity;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.system.entities.CityEntity;
import py.org.fundacionparaguaya.pspserver.system.entities.CountryEntity;

/**
 * @author bsandoval
 *
 */
@StaticMetamodel(FamilyEntity.class)
public class FamilyEntity_ {
    public static volatile SingularAttribute<FamilyEntity, ApplicationEntity> application;
    public static volatile SingularAttribute<FamilyEntity, OrganizationEntity> organization;
    public static volatile SingularAttribute<FamilyEntity, CountryEntity> country;
    public static volatile SingularAttribute<FamilyEntity, CityEntity> city;
    public static volatile SingularAttribute<FamilyEntity, String> name;
}
