package recipes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class Ingredient {

    private final String title;
    private final LocalDate useBy;
    private final LocalDate bestBefore;

    @JsonCreator
    public Ingredient(@JsonProperty("title") String title,
                      @JsonProperty("use-by") LocalDate useBy,
                      @JsonProperty("best-before") LocalDate bestBefore) {
        this.title = title;
        this.useBy = useBy;
        this.bestBefore = bestBefore;
    }

    public boolean isFresh() {
        return LocalDate.now().isBefore(bestBefore);
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(useBy);
    }
}
