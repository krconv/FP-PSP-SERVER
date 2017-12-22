/**
 * 
 */
package py.org.fundacionparaguaya.pspserver.surveys.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author bsandoval
 *
 */
public enum SurveyStoplightEnum {
	RED, YELLOW, GREEN;
	
	public static SurveyStoplightEnum fromValue(String value) {
		SurveyStoplightEnum[] values = SurveyStoplightEnum.values();
		Optional<SurveyStoplightEnum> findAny = Arrays.asList(values).stream()
		.filter(v -> v.name().equalsIgnoreCase(value))
		.findAny();
		return findAny.orElse(null);
	}
}
