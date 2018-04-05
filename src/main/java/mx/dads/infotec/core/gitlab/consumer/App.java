package mx.dads.infotec.core.gitlab.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import mx.dads.infotec.core.gitlab.consumer.config.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author erik.valdivieso
 */
@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class App implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Bean
    public ObjectMapper jacksonObjectMapper() {
        return new ObjectMapper().setPropertyNamingStrategy(
                PropertyNamingStrategy.SNAKE_CASE);

    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }

    //access command line arguments
    @Override
    public void run(String... args) throws Exception {
        LOGGER.debug("Params num: {}", args.length);
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
