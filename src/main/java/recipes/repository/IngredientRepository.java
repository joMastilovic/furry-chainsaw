package recipes.repository;

import recipes.model.Ingredient;

import java.util.Collection;

public interface IngredientRepository {

    Collection<Ingredient> getAllNotExpired();

}
