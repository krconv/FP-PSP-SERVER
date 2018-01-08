package py.org.fundacionparaguaya.pspserver.surveys.services;

import py.org.fundacionparaguaya.pspserver.surveys.dtos.NewSnapshot;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.Snapshot;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotFilterDTO;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotIndicators;

import java.util.List;
import java.util.Map;

/**
 * Created by rodrigovillalba on 9/14/17.
 */
public interface SnapshotService {
	
    Snapshot addSurveySnapshot(NewSnapshot snapshot);

    List<Snapshot> find(Long surveyId, Long familiyId);
    
    List<Snapshot> filter(SnapshotFilterDTO filter);

    SnapshotIndicators getSnapshotIndicators(Long snapshotId);

	List<SnapshotIndicators> getSnapshotIndicatorsByFamily(Long familyId);
	
	SnapshotIndicators getLastSnapshotIndicatorsByFamily(Long snapshotId);
}
