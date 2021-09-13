package cr.una.taskapp.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Properties;

/**
 * @author Maikol Guzman <mike@guzmanalan.com>
 * @create 7/25/21
 */
@SpringBootApplication
public class TaskAppBackendDataLayerApplication {
    public static void main(String[] args) {
        try {
            SpringApplication app = new SpringApplication(TaskAppBackendDataLayerApplication.class);
            Properties props = new Properties();
            props.setProperty("server.port", "8083");

            app.setDefaultProperties(props);
            app.run(args);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
