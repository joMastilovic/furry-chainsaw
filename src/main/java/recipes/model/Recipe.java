package recipes.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;

@Builder
@Getter
public class Recipe {

    private final String title;
    private final Collection<String> ingredients;

}
