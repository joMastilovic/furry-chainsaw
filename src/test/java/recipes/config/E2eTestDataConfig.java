package recipes.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@TestConfiguration
@Profile("e2eTest")
public class E2eTestDataConfig {

    @Bean
    public String ingredientsResource() {
        return "/e2eIngredients.json";
    }

    @Bean
    public String recipesResource() {
        return "/e2eRecipes.json";
    }

}
