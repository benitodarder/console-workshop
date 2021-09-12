package local.tin.tests.jwt.workshop;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.Properties;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author benitodarder
 */
public class GetToken extends App {

    public static final String USAGE = "Usage: java -cp <Path>/jwt-workshop-1.0-jar-with-dependencies.jar  local.tin.tests.jwt.workshop.GetToken <Properties file path>:\n"
            + System.lineSeparator() + App.PROPERTY_SECRET + ": <Secret>"
            + System.lineSeparator() + App.PROPERTY_ID + ": <Identifier>"
            + System.lineSeparator() + App.PROPERTY_SUBJECT + ": <Subject>"
            + System.lineSeparator() + App.PROPERTY_ISSUER + ": <Issuer>"
            + System.lineSeparator() + App.PROPERTY_VALID_FOR + ": <In milliseconds>";
    private static final Logger LOGGER = LogManager.getLogger(GetToken.class);

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            LOGGER.error(USAGE);
        } else {
            Properties properties = getPropertiesFile(args[0]);
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            Date now = new Date();

            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(properties.getProperty(App.PROPERTY_SECRET));
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            JwtBuilder builder = Jwts.builder().setId(properties.getProperty(App.PROPERTY_ID))
                    .setIssuedAt(now)
                    .setSubject(properties.getProperty(App.PROPERTY_SUBJECT))
                    .setIssuer(properties.getProperty(App.PROPERTY_ISSUER))
                    .setExpiration(new Date(now.getTime() + Long.parseLong(properties.getProperty(App.PROPERTY_VALID_FOR))))
                    .signWith(signatureAlgorithm, signingKey);

            LOGGER.info(builder.compact());
        }
    }
}
