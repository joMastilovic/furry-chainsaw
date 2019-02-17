package recipes.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import recipes.TestUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static recipes.TestUtils.TEST_RESOURCES;

@TestConfiguration
@Profile("e2eTest")
public class E2eTestDataConfig {

    public static final String E2E_INGREDIENTS = TEST_RESOURCES + "/e2eIngredients.json";

    @Bean
    public InputStream ingredientsInputStream() throws FileNotFoundException {
        return new FileInputStream(E2E_INGREDIENTS);
    }

    @Bean
    public InputStream recipesInputStream() throws FileNotFoundException {
        return new FileInputStream(TestUtils.TEST_RESOURCES + "/e2eRecipes.json");
    }

}
