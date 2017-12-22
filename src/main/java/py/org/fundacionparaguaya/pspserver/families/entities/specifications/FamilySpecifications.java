package py.org.fundacionparaguaya.pspserver.families.entities.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;

public final class FamilySpecifications {
  public static Specification<FamilyEntity> belongsToOrganization(Long organizationId) {
    return attributeHasId("organization", organizationId);
  }
  
  public static Specification<FamilyEntity> belongsToApplication(Long applicationId) {
    return attributeHasId("application", applicationId);
  }

  public static Specification<FamilyEntity> inCountry(Long countryId) {
    return attributeHasId("country", countryId);
  }

  public static Specification<FamilyEntity> inCity(Long cityId) {
    return attributeHasId("city", cityId);
  }

  private static Specification<FamilyEntity> attributeHasId(String attribute, Long id) {
    return new Specification<FamilyEntity>() {
      public Predicate toPredicate(Root<FamilyEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (id == null) return cb.isNotNull(root.get("familyId"));
        Join<Object, Object> countries = root.join(attribute);
        return cb.equal(countries.get("id"), id);
      }
    };
  }
}