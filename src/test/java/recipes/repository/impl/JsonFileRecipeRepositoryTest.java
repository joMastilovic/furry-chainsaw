package recipes.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import recipes.model.Recipe;
import recipes.repository.RecipeRepository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.function.Predicate;

import static recipes.TestUtils.TEST_RESOURCES;

class JsonFileRecipeRepositoryTest {

    private static final String RECIPES_JSON = "/testRecipes.json";

    @Test
    void getRecipes() throws FileNotFoundException {
        RecipeRepository jsonFileRecipeRepository =
            new JsonFileRecipeRepository(new ObjectMapper(),
                new FileInputStream(TEST_RESOURCES + RECIPES_JSON));

        Collection<Recipe> recipes = jsonFileRecipeRepository.getAll();

        assertRecipesDeserializedProperly(recipes);
    }

    private void assertRecipesDeserializedProperly(Collection<Recipe> recipes) {
        Assertions.assertEquals(5, recipes.size());
        Assertions.assertTrue(recipes.stream().allMatch(recipeIngredientsAreNotEmpty()));
        Assertions.assertTrue(recipes.stream().allMatch(recipesTitlesAreNotEmpty()));
        Assertions.assertTrue(recipes.stream().allMatch(recipeIngredientsTitlesAreNotEmpty()));
    }

    private static Predicate<Recipe> recipesTitlesAreNotEmpty() {
        return recipe -> StringUtils.isNotBlank(recipe.getTitle());
    }

    private static Predicate<Recipe> recipeIngredientsAreNotEmpty() {
        return recipe -> recipe.getIngredients().size() > 0;
    }

    private static Predicate<Recipe> recipeIngredientsTitlesAreNotEmpty() {
        return recipe -> recipe.getIngredients().stream().allMatch(StringUtils::isNotBlank);
    }

}
