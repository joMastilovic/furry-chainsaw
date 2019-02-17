package recipes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!e2eTest")
public class DataSource {

    @Bean
    public String ingredientsResource() {
        return "/ingredients.json";
    }

    @Bean
    public String recipesResource() {
        return "/recipes.json";
    }

}
