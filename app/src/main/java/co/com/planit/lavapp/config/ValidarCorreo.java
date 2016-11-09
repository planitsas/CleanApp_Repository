package co.com.planit.lavapp.config;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by VaioDevelopment on 12/04/2016.
 */
public class ValidarCorreo {


        private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        /**
         * Validate given email with regular expression.
         *
         * @param email
         *            email for validation
         * @return true valid email, otherwise false
         */
        public static boolean validarEmail(String email) {

            // Compiles the given regular expression into a pattern.
            Pattern pattern = Pattern.compile(PATTERN_EMAIL);

            // Match the given input against this pattern
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();

        }

    }