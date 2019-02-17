package recipes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import recipes.config.E2eTestDataConfig;
import recipes.controller.LunchRetrieveController;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static recipes.TestUtils.TEST_RESOURCES;

@SpringBootTest(classes = {Application.class, E2eTestDataConfig.class})
@ActiveProfiles("e2eTest")
public class EndToEndTest {

    @Autowired
    private LunchRetrieveController lunchRetrieveController;
    @Autowired
    private String ingredientsResource;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(lunchRetrieveController).build();
    }

    @Test
    void endToEndTest() throws Exception {
        createTestData();

        MvcResult mvcResult = mockMvc.perform(get("/lunch")).andReturn();

        Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
        String e2eTestResponseBody = "[{\"title\":\"Ham and Cheese Toastie\",\"ingredients\":[\"Ham\",\"Cheese\",\"" +
            "Bread\",\"Butter\"]},{\"title\":\"Hotdog\",\"ingredients\":[\"Hotdog Bun\",\"Sausage\",\"Ketchup\",\"" +
            "Mustard\"]}]";
        Assertions.assertEquals(e2eTestResponseBody, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void contextLoads() {
        Assertions.assertNotNull(lunchRetrieveController);
    }

    private void createTestData() throws IOException {
        Integer[] offsetsDays = {
            6, 11, 6, 11, 6, 11, 6, 11, 1, 6, 1, 6, -9, -6, 1, 6, -9, 6, 6, 11, 6, 11, 1, 6, 1, 6, 1, 6, 1, 6, -9, -6
        };
        LocalDate[] datesToFillTemplateWith = Arrays.stream(offsetsDays)
            .map(integer -> LocalDate.now().plusDays(integer))
            .toArray(LocalDate[]::new);

        String templateFileContent = TestUtils.readFileAsString(TEST_RESOURCES + "/e2eIngredientsTemplate.json");
        String fileContentWithDatesFilledIn = String.format(templateFileContent, datesToFillTemplateWith);

        try (PrintWriter out = new PrintWriter(TEST_RESOURCES + ingredientsResource)) {
            out.println(fileContentWithDatesFilledIn);
        }
    }
}
