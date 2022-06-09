package jejunu.portal.calenboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CalenBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalenBoardApplication.class, args);
    }

}
