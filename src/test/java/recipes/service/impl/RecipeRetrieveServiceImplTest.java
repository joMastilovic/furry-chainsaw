package recipes.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import recipes.helper.RecipeContainer;
import recipes.model.Recipe;
import recipes.repository.IngredientRepository;
import recipes.repository.RecipeRepository;
import recipes.service.RecipeIngredientMatchingService;
import recipes.service.RecipeRetrieveService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static recipes.testData.TestDataContainer.*;

class RecipeRetrieveServiceImplTest {

    private RecipeIngredientMatchingService recipeIngredientMatchingService;
    private IngredientRepository ingredientRepository;
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        recipeIngredientMatchingService = mock(RecipeIngredientMatchingService.class);
        ingredientRepository = mock(IngredientRepository.class);
        recipeRepository = mock(RecipeRepository.class);

        when(ingredientRepository.getAllNotExpired()).thenReturn(NOT_EXPIRED_INGREDIENTS);
        when(recipeRepository.getAll()).thenReturn(RECIPES);
        when(recipeIngredientMatchingService.getAvailableRecipes(NOT_EXPIRED_INGREDIENTS, RECIPES)).thenReturn(
            RecipeContainer.builder()
                .recipesWithAtLeastOneNotFreshIngredient(Arrays.asList(HAM_MUFFIN, OMELET))
                .recipesWithAllFreshIngredients(Collections.singletonList(PORRIDGE))
                .build());
    }

    @Test
    void getRecipes() {
        RecipeRetrieveService recipeRetrieveService = new RecipeRetrieveServiceImpl(
            ingredientRepository, recipeRepository, recipeIngredientMatchingService);

        List<Recipe> recipeList = recipeRetrieveService.getRecipes();

        Assertions.assertTrue(recipeList.containsAll(Arrays.asList(PORRIDGE, OMELET, HAM_MUFFIN)));
        Assertions.assertFalse(recipeList.contains(PIZZA));

        assertListSortedAccordingToRequirements(recipeList);

        verify(recipeRepository).getAll();
        verify(ingredientRepository).getAllNotExpired();
        verify(recipeIngredientMatchingService).getAvailableRecipes(NOT_EXPIRED_INGREDIENTS, RECIPES);
    }

    private void assertListSortedAccordingToRequirements(List<Recipe> recipeList) {
        ArrayList<Recipe> manuallySortedRecipeList = new ArrayList<>(recipeList);
        manuallySortedRecipeList.sort(recipeComparator);
        Assertions.assertEquals(recipeList, manuallySortedRecipeList,
            "recipeList should be sorted so it should not differ from its sorted copy.");
    }
}
