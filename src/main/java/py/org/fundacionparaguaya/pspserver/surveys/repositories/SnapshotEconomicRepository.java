package py.org.fundacionparaguaya.pspserver.surveys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import py.org.fundacionparaguaya.pspserver.surveys.entities.SnapshotEconomicEntity;
import py.org.fundacionparaguaya.pspserver.surveys.entities.StoreableSnapshot;

import java.util.Collection;

/**
 * Created by rodrigovillalba on 10/19/17.
 */
public interface SnapshotEconomicRepository extends JpaRepository<SnapshotEconomicEntity, Long>, JpaSpecificationExecutor<SnapshotEconomicEntity> {
    Collection<SnapshotEconomicEntity> findBySurveyDefinitionId(Long surveyId);
}
