package recipes.repository.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import recipes.exception.JsonRepositoryException;
import recipes.model.Recipe;
import recipes.repository.RecipeRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class JsonFileRecipeRepository implements RecipeRepository {

    private final ObjectMapper objectMapper;
    private final String recipesResource;

    @Override
    public Collection<Recipe> getAll() {
        try {
            return objectMapper.readValue(
                getClass().getResourceAsStream(recipesResource), JsonRecipesContainer.class
            ).recipes;
        } catch (IOException e) {
            throw new JsonRepositoryException(recipesResource, e);
        }
    }

    private static class JsonRecipesContainer {
        private final List<Recipe> recipes;

        @JsonCreator
        public JsonRecipesContainer(@JsonProperty("recipes") List<Recipe> recipes) {
            this.recipes = recipes;
        }
    }

}
