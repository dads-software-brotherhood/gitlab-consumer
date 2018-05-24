package mx.dads.infotec.core.gitlab.consumer.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

public final class DateUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
    private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    private DateUtils() {
    }

    @Nullable
    public static Date toDate(String source) {
        try {
			return DATE_FORMAT.parse(source);
		} catch (ParseException ex) {
            LOGGER.error("Error at parse date", ex);
            return null;
		}
    }

}