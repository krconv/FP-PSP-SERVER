package py.org.fundacionparaguaya.pspserver.surveys.validation;

import java.util.*;

/**
 * Created by rodrigovillalba on 10/24/17.
 */

public class ValidationSupport {

    private static final ValidationResult valid = new ValidationResult(){

        public boolean isValid(){ return true; }

        public Optional<String> getReason(){ return Optional.empty(); }

        @Override
        public String getPropertyName() {
            return null;
        }
    };


    static ValidationResult validResult(){
        return valid;
    }


    public static ValidationResults validResults() {return new ValidationList();}
}