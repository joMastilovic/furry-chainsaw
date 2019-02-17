package recipes.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class Ingredient {

    private final String title;
    private final LocalDate useBy;
    private final LocalDate bestBefore;

}
