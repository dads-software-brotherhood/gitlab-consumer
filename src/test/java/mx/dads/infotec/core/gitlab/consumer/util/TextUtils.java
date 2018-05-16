package mx.dads.infotec.core.gitlab.consumer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * Clase de utilidades para las pruebas.
 *
 * @author erik.valdivieso
 */
public final class TextUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(TextUtils.class);

    public static String readText(Resource resource) {
        return readText(resource, null);
    }

    public static String readText(Resource resource, String defaultValue) {

        try (InputStream is = resource.getInputStream()) {
            StringBuilder sb = new StringBuilder();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }

            return sb.toString();
        } catch (IOException ex) {
            if (LOGGER.isErrorEnabled()) {
                StringBuilder sb = new StringBuilder("Error at read resource ");
                sb.append(resource.getFilename());
                LOGGER.error(sb.toString(), ex);
            }
        }

        return defaultValue;
    }
}
