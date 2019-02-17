package recipes.config;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Mapper {

    public static ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper;
    }

}
