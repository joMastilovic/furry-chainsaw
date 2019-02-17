package recipes.config;

import org.springframework.context.annotation.*;

import java.io.InputStream;

@Configuration
@Profile("!e2eTest")
public class DataSource {

    @Bean
    @Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public InputStream ingredientsInputStream() {
        return getClass().getResourceAsStream("/ingredients.json");
    }

    @Bean
    @Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public InputStream recipesInputStream() {
        return getClass().getResourceAsStream("/recipes.json");
    }

}
