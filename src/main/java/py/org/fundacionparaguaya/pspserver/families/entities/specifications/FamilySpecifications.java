package py.org.fundacionparaguaya.pspserver.families.entities.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;

public final class FamilySpecifications {
  private FamilySpecifications() { }

  public static Specification<FamilyEntity> belongsToOrganization(
      final Long organizationId
    ) {
    return attributeHasId("organization", organizationId);
  }

  public static Specification<FamilyEntity> belongsToApplication(
      final Long applicationId
    ) {
    return attributeHasId("application", applicationId);
  }

  public static Specification<FamilyEntity> inCountry(final Long countryId) {
    return attributeHasId("country", countryId);
  }

  public static Specification<FamilyEntity> inCity(final Long cityId) {
    return attributeHasId("city", cityId);
  }

  private static Specification<FamilyEntity> attributeHasId(
      final String attribute,
      final Long id
    ) {
    return new Specification<FamilyEntity>() {
      public Predicate toPredicate(
          final Root<FamilyEntity> root,
          final CriteriaQuery<?> query,
          final CriteriaBuilder cb
        ) {
        if (id == null) { 
          return cb.isNotNull(root.get("familyId")); 
        }
        Join<Object, Object> secondary = root.join(attribute);
        return cb.equal(secondary.get("id"), id);
      }
    };
  }
}
