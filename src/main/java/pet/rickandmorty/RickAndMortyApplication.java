package pet.rickandmorty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableScheduling
@SpringBootApplication
@EnableWebMvc()
public class RickAndMortyApplication {
    public static void main(String[] args) {
        SpringApplication.run(RickAndMortyApplication.class, args);
    }

    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }
}
