/**
 * 
 */
package py.org.fundacionparaguaya.pspserver.families.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import py.org.fundacionparaguaya.pspserver.surveys.dtos.SnapshotIndicators;

/**
 * @author bsandoval
 *
 */
public class FamilyMapDTO extends FamilyDTO {
	
	@JsonProperty("snapshot_indicators")
	private SnapshotIndicators snapshotIndicators;

	@ApiModelProperty(value = "Last snapshot indicators")
	public SnapshotIndicators getSnapshotIndicators() {
		return snapshotIndicators;
	}

	public void setSnapshotIndicators(SnapshotIndicators snapshotIndicators) {
		this.snapshotIndicators = snapshotIndicators;
	}
	
}
