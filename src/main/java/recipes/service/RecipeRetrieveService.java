package recipes.service;


import org.springframework.stereotype.Component;
import recipes.model.Recipe;

import java.util.List;

@Component
public interface RecipeRetrieveService {

    List<Recipe> getRecipes();

}
