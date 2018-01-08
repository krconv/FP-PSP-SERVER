package py.org.fundacionparaguaya.pspserver.surveys.entities.specifications;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;

public final class SnapshotSpecifications {
  private SnapshotSpecifications() {}

  public static Specification<SnapshotEconomicEntity> hasIndicator(
      final String indicator, 
      final List<String> values
    ) {
    return new Specification<SnapshotEconomicEntity>() {
      public Predicate toPredicate(
          final Root<SnapshotEconomicEntity> root,
          final CriteriaQuery<?> query,
          final CriteriaBuilder cb
        ) {
        if (indicator == null || indicator.length() == 0) {
          return cb.isNotNull(root.get("id")); // any
        }

        Join<Object, Object> snapshots = root.join("snapshotIndicator");
        if (values == null || values.isEmpty()) {
          return cb.isNotNull(snapshots.get(indicator)); // any value
        }

        List<Predicate> predicates = new ArrayList<Predicate>();
        for (String color : values) {
          predicates.add(cb.equal(snapshots.get(indicator), color));
        }
        return cb.or(predicates.toArray(new Predicate[predicates.size()]));
      }
    };
  }

  public static Specification<SnapshotEconomicEntity> hasIndicators(
      final Map<String, 
      final List<String>> indicators
    ) {
    return new Specification<SnapshotEconomicEntity>() {
      public Predicate toPredicate(
          final Root<SnapshotEconomicEntity> root,
          final CriteriaQuery<?> query,
          final CriteriaBuilder cb
        ) {
        if (indicators == null || indicators.isEmpty()) {
          return cb.isNotNull(root.get("id")); // any
        }

        List<Predicate> predicates = new ArrayList<Predicate>();
        for (String indicator : indicators.keySet()) {
          predicates.add(hasIndicator(
            indicator,
            indicators.get(indicator)).toPredicate(root, query, cb)
          );
        }
        return cb.or(predicates.toArray(new Predicate[predicates.size()]));
      }
    };
  }

  public static Specification<SnapshotEconomicEntity> forFamilies(
      final List<Long> familyIds
    ) {
    return new Specification<SnapshotEconomicEntity>() {
      public Predicate toPredicate(
          final Root<SnapshotEconomicEntity> root,
          final CriteriaQuery<?> query,
          final CriteriaBuilder cb
        ) {
        if (familyIds == null) {
          return cb.isNotNull(root.get("id")); // any
        }

        List<Predicate> predicates = new ArrayList<Predicate>();
        for (Long familyId : familyIds) {
          predicates.add(cb.equal(
            root.join("family").get("familyId"),
            familyId
          ));
        }
        return cb.or(predicates.toArray(new Predicate[predicates.size()]));
      }
    };
  }
}
