package py.org.fundacionparaguaya.pspserver.network.entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author bsandoval
 *
 */
@StaticMetamodel(OrganizationEntity.class)
public class OrganizationEntity_ {
    public static volatile SingularAttribute<OrganizationEntity, ApplicationEntity> application;
    public static volatile SingularAttribute<OrganizationEntity, Long> id;
    public static volatile SingularAttribute<OrganizationEntity, Boolean> isActive;
}
