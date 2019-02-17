package recipes.testData;

import recipes.model.Ingredient;
import recipes.model.Recipe;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestDataContainer {

    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate YESTERDAY = TODAY.minusDays(1);
    private static final LocalDate TOMORROW = TODAY.plusDays(1);
    private static final LocalDate DAY_AFTER_TOMORROW = TODAY.plusDays(2);

    private static final Ingredient HAM = Ingredient.builder().title("ham").bestBefore(YESTERDAY).useBy(TOMORROW)
        .build();
    private static final Ingredient EGGS = Ingredient.builder().title("eggs").bestBefore(YESTERDAY).useBy(TOMORROW)
        .build();
    private static final Ingredient FLOUR = Ingredient.builder().title("flour").bestBefore(TOMORROW)
        .useBy(DAY_AFTER_TOMORROW).build();
    private static final Ingredient MILK = Ingredient.builder().title("milk").bestBefore(TOMORROW)
        .useBy(DAY_AFTER_TOMORROW)
        .build();
    private static final Ingredient FRESH_OATS = Ingredient.builder().title("oats").bestBefore(TOMORROW)
        .useBy(DAY_AFTER_TOMORROW)
        .build();
    private static final Ingredient CHEESE = Ingredient.builder().title("cheese").bestBefore(TODAY.minusDays(7))
        .useBy(YESTERDAY)
        .build();

    public static final Recipe PORRIDGE = Recipe.builder().title("porridge")
        .ingredients(Arrays.asList(FRESH_OATS.getTitle(), MILK.getTitle())).build();
    public static final Recipe OMELET = Recipe.builder().title("omelet")
        .ingredients(Arrays.asList(HAM.getTitle(), EGGS.getTitle()))
        .build();
    public static final Recipe HAM_MUFFIN = Recipe.builder().title("Ham muffin")
        .ingredients(Arrays.asList(HAM.getTitle(), EGGS.getTitle(), FLOUR.getTitle()))
        .build();
    public static final Recipe PIZZA = Recipe.builder().title("pizza")
        .ingredients(Arrays.asList(FLOUR.getTitle(), EGGS.getTitle(), HAM.getTitle(), CHEESE.getTitle()))
        .build();

    public static final List<Recipe> RECIPES = Arrays.asList(PORRIDGE, OMELET, PIZZA, HAM_MUFFIN);
    public static final List<Ingredient> NOT_EXPIRED_INGREDIENTS = Arrays.asList(HAM, FLOUR, EGGS, MILK, FRESH_OATS);

    private static final List<Ingredient> ALL_INGREDIENTS = Arrays.asList(CHEESE, HAM, FLOUR, EGGS, MILK, FRESH_OATS);
    private static final Map<String, Ingredient> TITLE_INGREDIENT_MAP = ALL_INGREDIENTS.stream()
        .collect(Collectors.toMap(Ingredient::getTitle, Function.identity()));

    public static final Comparator<Recipe> recipeComparator = (recipe1, recipe2) -> {
        boolean firstRecipeOnlyHasFreshIngredients = recipe1.getIngredients()
            .stream()
            .allMatch(ingredient -> TITLE_INGREDIENT_MAP.get(ingredient).isFresh());
        boolean secondRecipeOnlyHasFreshIngredients = recipe2.getIngredients()
            .stream()
            .allMatch(ingredient -> TITLE_INGREDIENT_MAP.get(ingredient).isFresh());

        if (firstRecipeOnlyHasFreshIngredients && !secondRecipeOnlyHasFreshIngredients) {
            return -1;
        } else if (!firstRecipeOnlyHasFreshIngredients && secondRecipeOnlyHasFreshIngredients) {
            return 1;
        }
        return 0;
    };

}
