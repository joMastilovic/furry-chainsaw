package recipes.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import recipes.helper.RecipeContainer;
import recipes.model.Recipe;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static recipes.testData.TestDataContainer.*;

class RecipeIngredientMatchingServiceImplTest {

    private static final Predicate<Recipe> RECIPE_ONLY_CONTAINS_FRESH_INGREDIENTS =
        recipe -> recipe.getIngredients().stream().allMatch(ingredient -> getIngredientByTitle(ingredient).isFresh());

    private static final Predicate<Recipe> RECIPE_CONTAINS_AT_LEAST_ONE_NOT_FRESH_INGREDIENT =
        recipe -> recipe.getIngredients().stream().anyMatch(ingredient -> !getIngredientByTitle(ingredient).isFresh());

    private static Predicate<Recipe> recipeContainsAtLeastOneExpiredIngredient() {
        return recipe -> recipe.getIngredients().stream()
            .anyMatch(ingredient -> !NOT_EXPIRED_INGREDIENTS.contains(getIngredientByTitle(ingredient)));
    }

    @Test
    void getAvailableRecipes() {
        RecipeIngredientMatchingServiceImpl recipeIngredientMatchingService = new RecipeIngredientMatchingServiceImpl();

        RecipeContainer availableRecipes = recipeIngredientMatchingService
            .getAvailableRecipes(NOT_EXPIRED_INGREDIENTS, RECIPES);

        List<Recipe> recipesWithFreshIngredients = availableRecipes.getRecipesWithAllFreshIngredients();
        List<Recipe> recipesWithSomeIngredientsPastBestBeforeDate =
            availableRecipes.getRecipesWithAtLeastOneNotFreshIngredient();

        Assertions.assertTrue(recipeContainsAtLeastOneExpiredIngredient().test(PIZZA));
        Assertions.assertFalse(recipesWithFreshIngredients.contains(PIZZA) ||
                recipesWithSomeIngredientsPastBestBeforeDate.contains(PIZZA),
            "recipe PIZZA should not be included in available recipes since it has expired ingredients");

        Assertions.assertEquals(recipesWithFreshIngredients, Collections.singletonList(PORRIDGE));
        Assertions.assertTrue(recipesWithFreshIngredients.stream().allMatch(RECIPE_ONLY_CONTAINS_FRESH_INGREDIENTS));

        Assertions.assertEquals(recipesWithSomeIngredientsPastBestBeforeDate, Arrays.asList(OMELET, HAM_MUFFIN));
        Assertions.assertTrue(recipesWithSomeIngredientsPastBestBeforeDate.stream()
            .allMatch(RECIPE_CONTAINS_AT_LEAST_ONE_NOT_FRESH_INGREDIENT));
    }

}
