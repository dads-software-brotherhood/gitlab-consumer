package mx.dads.infotec.core.gitlab.consumer;

import mx.dads.infotec.core.gitlab.consumer.config.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 *
 * @author erik.valdivieso
 */
@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class App implements CommandLineRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(App.class);
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

    //access command line arguments
    @Override
    public void run(String... args) throws Exception {
        LOGGER.debug("Params num: {}", args.length);
    }
}
