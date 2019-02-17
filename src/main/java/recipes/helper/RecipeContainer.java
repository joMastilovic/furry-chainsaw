package recipes.helper;

import lombok.Builder;
import lombok.Getter;
import recipes.model.Recipe;

import java.util.List;

@Getter
@Builder
public class RecipeContainer {

    private final List<Recipe> recipesWithAtLeastOneNotFreshIngredient;
    private final List<Recipe> recipesWithAllFreshIngredients;

}
