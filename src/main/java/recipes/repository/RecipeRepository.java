package recipes.repository;

import recipes.model.Recipe;

import java.util.Collection;

public interface RecipeRepository {

    Collection<Recipe> getAll();

}
