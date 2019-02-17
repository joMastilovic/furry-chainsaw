package recipes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import recipes.service.RecipeRetrieveService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static recipes.testData.TestDataContainer.RECIPES;

@ExtendWith(SpringExtension.class)
class LunchRetrieveControllerTest {

    private MockMvc mockMvc;

    @Test
    void getRecipes() throws Exception {
        RecipeRetrieveService recipeRetrieveService = mock(RecipeRetrieveService.class);
        when(recipeRetrieveService.getRecipes()).thenReturn(RECIPES);
        LunchRetrieveController lunchRetrieveController = new LunchRetrieveController(recipeRetrieveService);
        this.mockMvc = standaloneSetup(lunchRetrieveController).build();

        MvcResult result = mockMvc.perform(get("/lunch")).andReturn();

        verify(recipeRetrieveService).getRecipes();
        Assertions.assertEquals(200, result.getResponse().getStatus());
        String contentAsString = result.getResponse().getContentAsString();
        Assertions.assertEquals(new ObjectMapper().writeValueAsString(RECIPES), contentAsString);
    }
}
