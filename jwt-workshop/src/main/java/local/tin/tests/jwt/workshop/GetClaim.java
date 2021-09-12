package local.tin.tests.jwt.workshop;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.util.Properties;
import javax.xml.bind.DatatypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author benitodarder
 */
public class GetClaim extends App {

    public static final String USAGE = "Usage: java -cp <Path>/jwt-workshop-1.0-jar-with-dependencies.jar  local.tin.tests.jwt.workshop.GetClaim <Properties file path> <Token>:\n"
            + System.lineSeparator() + App.PROPERTY_SECRET + ": <Secret>"
            + System.lineSeparator() + App.PROPERTY_ID + ": <Identifier>"
            + System.lineSeparator() + App.PROPERTY_SUBJECT + ": <Subject>"
            + System.lineSeparator() + App.PROPERTY_ISSUER + ": <Issuer>"
            + System.lineSeparator() + App.PROPERTY_VALID_FOR + ": <In milliseconds>";
    private static final Logger LOGGER = LogManager.getLogger(GetClaim.class);

    public static void main(String[] args) throws IOException  {
        if (args.length != 2) {
            LOGGER.error(USAGE);
        } else {
            Properties properties = getPropertiesFile(args[0]);
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(DatatypeConverter.parseBase64Binary(properties.getProperty(App.PROPERTY_SECRET)))
                        .parseClaimsJws(args[1]).getBody();

                LOGGER.info("Claim audience: " + claims.getAudience());
                LOGGER.info("Claim id.: " + claims.getId());
                LOGGER.info("Claim issuer: " + claims.getIssuer());
                LOGGER.info("Claim subject: " + claims.getSubject());
            } catch (ExpiredJwtException | IllegalArgumentException | MalformedJwtException | SignatureException | UnsupportedJwtException e) {
                LOGGER.warn(e.getMessage());
            }
        }
    }
}
