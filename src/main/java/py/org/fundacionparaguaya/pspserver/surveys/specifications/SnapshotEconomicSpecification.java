package py.org.fundacionparaguaya.pspserver.surveys.specifications;

import org.springframework.data.jpa.domain.Specification;

import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;

/**
 * Specifications used to find a snapshot economic entity from the repository.
 */
public class SnapshotEconomicSpecification {

    public static Specification<SnapshotEconomicEntity> forFamily(Long familyId) {
        return (root, query, cb) -> {
            if (familyId == null) {
                return null;
            }
            return cb.equal(root.join("family").get("familyId"), familyId);
        };
    }

    public static Specification<SnapshotEconomicEntity> forSurvey(Long surveyId) {
        return (root, query, cb) -> {
            if (surveyId == null) {
                return null;
            }
            return cb.equal(root.join("surveyDefinition").get("id"), surveyId);
        };
    }
}
