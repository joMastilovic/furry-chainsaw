package recipes.service.impl;

import recipes.helper.RecipeContainer;
import recipes.model.Ingredient;
import recipes.model.Recipe;
import recipes.service.RecipeIngredientMatchingService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeIngredientMatchingServiceImpl implements RecipeIngredientMatchingService {

    @Override
    public RecipeContainer getAvailableRecipes(Collection<Ingredient> ingredients, Collection<Recipe> recipes) {

        List<Ingredient> freshIngredients = ingredients.stream()
            .filter(Ingredient::isFresh)
            .collect(Collectors.toList());

        List<Recipe> recipesWithFreshIngredients = recipes.stream()
            .filter(recipe -> recipe.canBeMadeFromIngredients(freshIngredients))
            .collect(Collectors.toList());

        List<Recipe> remainingRecipes = new ArrayList<>(recipes);
        remainingRecipes.removeAll(recipesWithFreshIngredients);

        List<Recipe> recipesWithAtLeastOneNotFreshIngredient = remainingRecipes.stream()
            .filter(recipe -> recipe.canBeMadeFromIngredients(ingredients))
            .collect(Collectors.toList());

        return RecipeContainer.builder()
            .recipesWithAllFreshIngredients(recipesWithFreshIngredients)
            .recipesWithAtLeastOneNotFreshIngredient(recipesWithAtLeastOneNotFreshIngredient)
            .build();
    }

}
