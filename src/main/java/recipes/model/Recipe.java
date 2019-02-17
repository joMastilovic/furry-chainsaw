package recipes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.util.stream.Collectors;

@Builder
@Getter
public class Recipe {

    private final String title;
    private final Collection<String> ingredients;

    @JsonCreator
    public Recipe(@JsonProperty("title") String title, @JsonProperty("ingredients") Collection<String> ingredients) {
        this.title = title;
        this.ingredients = ingredients;
    }

    public boolean canBeMadeFromIngredients(Collection<Ingredient> ingredients) {
        return ingredients.stream()
            .map(Ingredient::getTitle)
            .collect(Collectors.toList())
            .containsAll(this.ingredients);
    }

}
