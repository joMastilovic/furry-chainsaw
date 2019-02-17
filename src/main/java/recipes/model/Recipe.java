package recipes.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.util.stream.Collectors;

@Builder
@Getter
public class Recipe {

    private final String title;
    private final Collection<String> ingredients;

    public boolean canBeMadeFromIngredients(Collection<Ingredient> ingredients) {
        return ingredients.stream()
            .map(Ingredient::getTitle)
            .collect(Collectors.toList())
            .containsAll(this.ingredients);
    }

}
