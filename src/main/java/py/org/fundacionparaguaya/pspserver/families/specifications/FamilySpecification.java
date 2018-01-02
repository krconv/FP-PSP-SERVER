/**
 * 
 */
package py.org.fundacionparaguaya.pspserver.families.specifications;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import py.org.fundacionparaguaya.pspserver.families.dtos.FamilyFilterDTO;
import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity;
import py.org.fundacionparaguaya.pspserver.families.entities.FamilyEntity_;
import py.org.fundacionparaguaya.pspserver.network.entities.ApplicationEntity;
import py.org.fundacionparaguaya.pspserver.network.entities.OrganizationEntity;
import py.org.fundacionparaguaya.pspserver.system.entities.CityEntity;
import py.org.fundacionparaguaya.pspserver.system.entities.CountryEntity;

/**
 * @author bsandoval
 *
 */
public class FamilySpecification {
    
    private static final String ID_ATTRIBUTE = "id";
    
    public static Specification<FamilyEntity> byFilter(FamilyFilterDTO filter) {
        return new Specification<FamilyEntity>() {
            @Override
            public Predicate toPredicate(Root<FamilyEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                
                if (filter.getApplicationId() != null) {
                    Join<FamilyEntity, ApplicationEntity> joinApplication = root.join(FamilyEntity_.application);
                    Expression<Long> byApplicationId = joinApplication.<Long> get(ID_ATTRIBUTE);
                    predicates.add(cb.equal(byApplicationId, filter.getApplicationId()));
                }
                
                if (filter.getOrganizationId() != null) {
                    Join<FamilyEntity, OrganizationEntity> joinOrganization = root.join(FamilyEntity_.organization);
                    Expression<Long> byOrganizationId = joinOrganization.<Long> get(ID_ATTRIBUTE);
                    predicates.add(cb.equal(byOrganizationId, filter.getOrganizationId()));
                }
                
                if (filter.getCountryId() != null) {
                    Join<FamilyEntity, CountryEntity> joinCountry = root.join(FamilyEntity_.country);
                    Expression<Long> byCountryId = joinCountry.<Long> get(ID_ATTRIBUTE);
                    predicates.add(cb.equal(byCountryId, filter.getCountryId()));
                }
                
                if (filter.getCityId() != null) {
                    Join<FamilyEntity, CityEntity> joinCity = root.join(FamilyEntity_.city);
                    Expression<Long> byCityId = joinCity.<Long> get(ID_ATTRIBUTE);
                    predicates.add(cb.equal(byCityId, filter.getCityId()));
                }
                
                if(StringUtils.isNotEmpty(filter.getName())) {
                    String nameParamQuery = "%" + filter.getName().toLowerCase().replaceAll("\\s", "%") + "%";
                    Expression<String> likeName = cb.lower(root.get(FamilyEntity_.name));
                    predicates.add(cb.like(likeName, nameParamQuery));
                }
                
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

}
