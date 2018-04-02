package mx.dads.infotec.core.gitlab.rest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author erik.valdivieso
 */
@SpringBootApplication
public class App implements CommandLineRunner {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

    //access command line arguments
    @Override
    public void run(String... args) throws Exception {

        //do something
    }
}
