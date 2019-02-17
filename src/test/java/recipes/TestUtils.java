package recipes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class TestUtils {

    public static final String TEST_RESOURCES = "src/test/resources";

    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

}
