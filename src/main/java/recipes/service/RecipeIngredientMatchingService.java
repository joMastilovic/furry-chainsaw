package recipes.service;

import recipes.helper.RecipeContainer;
import recipes.model.Ingredient;
import recipes.model.Recipe;

import java.util.Collection;

public interface RecipeIngredientMatchingService {

    RecipeContainer getAvailableRecipes(Collection<Ingredient> ingredients, Collection<Recipe> recipes);

}
