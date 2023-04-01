package io.github.jokerhasnopersonality;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Loader for PizzeriaProperties class.
 */
public class PizzeriaPropertiesManager {
    public static PizzeriaProperties loadProperties(InputStream inputStream) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(inputStream, PizzeriaProperties.class);
    }

    public static void saveProperties(String fileName, PizzeriaProperties properties)
            throws IOException {
        ObjectMapper mapper = JsonMapper.builder().build();
        mapper.writeValue(new FileOutputStream(fileName), properties);
    }
}
