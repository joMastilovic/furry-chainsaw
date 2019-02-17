package recipes.repository.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import recipes.exception.JsonRepositoryException;
import recipes.model.Ingredient;
import recipes.repository.IngredientRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@AllArgsConstructor
@Component
public class JsonFileIngredientRepository implements IngredientRepository {
    private final ObjectMapper objectMapper;
    private final String ingredientsResource;

    @Override
    public Collection<Ingredient> getAllNotExpired() {
        try {
            return objectMapper.readValue(
                getClass().getResourceAsStream(ingredientsResource), JsonIngredientContainer.class)
                .ingredients.stream().filter(not(Ingredient::isExpired))
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new JsonRepositoryException(ingredientsResource, e);
        }
    }

    private static class JsonIngredientContainer {
        private final List<Ingredient> ingredients;

        @JsonCreator
        public JsonIngredientContainer(@JsonProperty("ingredients") List<Ingredient> ingredients) {
            this.ingredients = ingredients;
        }
    }
}
