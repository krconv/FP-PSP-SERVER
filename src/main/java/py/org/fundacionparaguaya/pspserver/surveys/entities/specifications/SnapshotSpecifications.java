package py.org.fundacionparaguaya.pspserver.surveys.entities.specifications;

import java.util.ArrayList;
import java.util.Arrays;
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
  public static Specification<SnapshotEconomicEntity> hasIndicator(String indicator, List<String> values) {
    return new Specification<SnapshotEconomicEntity>() {
      public Predicate toPredicate(Root<SnapshotEconomicEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Join<Object, Object> snapshots = root.join("snapshotIndicator");
        List<Predicate> predicates = new ArrayList<Predicate>();
        for (String color : values) {
          predicates.add(cb.equal(snapshots.get(indicator), color));
        }
        return cb.or(predicates.toArray(new Predicate[predicates.size()]));
      }
    };
  }

  public static Specification<SnapshotEconomicEntity> hasIndicators(Map<String, List<String>> indicators) {
    return new Specification<SnapshotEconomicEntity>() {
      public Predicate toPredicate(Root<SnapshotEconomicEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        for (String indicator : indicators.keySet()) {
          predicates.add(hasIndicator(indicator, indicators.get(indicator)).toPredicate(root, query, cb));
        }
        return cb.or(predicates.toArray(new Predicate[predicates.size()]));
      }
    };
  }

  public static Specification<SnapshotEconomicEntity> forFamilies(List<Long> familyIds) {
    return new Specification<SnapshotEconomicEntity>() {
      public Predicate toPredicate(Root<SnapshotEconomicEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        for (Long familyId : familyIds) {
          predicates.add(cb.equal(root.get("familyId"), familyId));
        }
        return cb.or(predicates.toArray(new Predicate[predicates.size()]));
      }
    };
  }
}