package recipes.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import recipes.helper.RecipeContainer;
import recipes.model.Ingredient;
import recipes.model.Recipe;
import recipes.repository.IngredientRepository;
import recipes.repository.RecipeRepository;
import recipes.service.RecipeIngredientMatchingService;
import recipes.service.RecipeRetrieveService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Component
public class RecipeRetrieveServiceImpl implements RecipeRetrieveService {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeIngredientMatchingService recipeIngredientMatchingService;

    @Override
    public List<Recipe> getRecipes() {
        Collection<Ingredient> ingredients = ingredientRepository.getAllNotExpired();
        Collection<Recipe> recipes = recipeRepository.getAll();

        RecipeContainer availableRecipes = recipeIngredientMatchingService.getAvailableRecipes(ingredients, recipes);

        List<Recipe> sortedRecipes = new ArrayList<>();
        sortedRecipes.addAll(availableRecipes.getRecipesWithAllFreshIngredients());
        sortedRecipes.addAll(availableRecipes.getRecipesWithAtLeastOneNotFreshIngredient());
        return sortedRecipes;
    }

}
