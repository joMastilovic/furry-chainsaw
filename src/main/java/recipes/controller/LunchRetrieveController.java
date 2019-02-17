package recipes.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import recipes.service.RecipeRetrieveService;

import java.util.List;

@RestController
@AllArgsConstructor
public class LunchRetrieveController {

    private final RecipeRetrieveService recipeRetrieveService;

    @GetMapping("/lunch")
    public List getLunch() {
        return recipeRetrieveService.getRecipes();
    }

}
