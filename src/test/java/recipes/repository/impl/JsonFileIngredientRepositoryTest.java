package recipes.repository.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import recipes.model.Ingredient;
import recipes.repository.IngredientRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import static recipes.TestUtils.TEST_RESOURCES;
import static recipes.TestUtils.readFileAsString;
import static recipes.config.Mapper.objectMapper;
import static recipes.testData.TestDataContainer.TODAY;
import static recipes.testData.TestDataContainer.YESTERDAY;

class JsonFileIngredientRepositoryTest {

    private static final String INGREDIENTS_JSON = "/ingredients.json";
    private static final String INGREDIENT_TEMPLATE_JSON = TEST_RESOURCES + "/ingredientsTemplate.json";
    private static final String INGREDIENTS_WITH_DATES_FILLED_IN_JSON = TEST_RESOURCES + INGREDIENTS_JSON;

    @BeforeEach
    void setUp() throws IOException {
        String templateFileContent = readFileAsString(INGREDIENT_TEMPLATE_JSON);
        String fileContentWithDatesFilledIn = String.format(templateFileContent,
            TODAY, TODAY, TODAY, TODAY, TODAY, TODAY, YESTERDAY, YESTERDAY
        );
        try (PrintWriter out = new PrintWriter(INGREDIENTS_WITH_DATES_FILLED_IN_JSON)) {
            out.println(fileContentWithDatesFilledIn);
        }
    }

    @Test
    void getAllNotExpired() {
        IngredientRepository jsonFileIngredientRepository =
            new JsonFileIngredientRepository(objectMapper(), INGREDIENTS_JSON);

        Collection<Ingredient> allNotExpired = jsonFileIngredientRepository.getAllNotExpired();

        assertIngredientsDeserializedProperly(allNotExpired);
    }

    private void assertIngredientsDeserializedProperly(Collection<Ingredient> allNotExpired) {
        Assertions.assertEquals(3, allNotExpired.size());
        Assertions.assertTrue(allNotExpired.stream().noneMatch(Ingredient::isExpired));
        Assertions.assertTrue(
            allNotExpired.stream().allMatch(ingredient -> StringUtils.isNotBlank(ingredient.getTitle()))
        );
    }

}
