package recipes.service;

import recipes.model.Ingredient;
import recipes.model.Recipe;

import java.util.Collection;

public interface RecipeIngredientMatchingService {

    Collection<Recipe> getAvailableRecipes(Collection<Ingredient> ingredients, Collection<Recipe> recipes);

}
