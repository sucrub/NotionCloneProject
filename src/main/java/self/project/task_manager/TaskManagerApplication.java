package self.project.task_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication(exclude = { org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class })
@EnableMongoRepositories
public class TaskManagerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

}
